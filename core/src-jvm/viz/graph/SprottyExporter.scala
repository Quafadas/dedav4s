package viz.graph

import io.circe.*
import io.circe.generic.semiauto.*
import io.circe.syntax.*

/** Sprotty model classes for JSON serialization */
object SprottyModel:
  /** Position in 2D space */
  case class Position(x: Double, y: Double)

  /** Size dimensions */
  case class Size(width: Double, height: Double)

  /** Routing point for edges */
  case class RoutingPoint(x: Double, y: Double)

  /** Table data for table nodes */
  case class TableData(rows: Int, columns: Int)

  /** Label element */
  case class Label(id: String, `type`: String, text: String)

  /** Node element in the graph */
  case class Node(
      id: String,
      `type`: String,
      position: Position,
      size: Size,
      children: List[Label],
      tableData: Option[TableData] = None
  )

  /** Edge element in the graph */
  case class Edge(
      id: String,
      `type`: String,
      sourceId: String,
      targetId: String,
      routingPoints: Option[List[RoutingPoint]] = None
  )

  /** Graph element (can be Node or Edge) */
  sealed trait GraphElement

  object GraphElement:
    case class NodeElement(node: Node) extends GraphElement
    case class EdgeElement(edge: Edge) extends GraphElement
  end GraphElement

  /** Root graph container */
  case class Graph(
      `type`: String,
      id: String,
      children: List[Json]
  )

  // Circe encoders
  given Encoder[Position] = deriveEncoder[Position]
  given Encoder[Size] = deriveEncoder[Size]
  given Encoder[RoutingPoint] = deriveEncoder[RoutingPoint]
  given Encoder[TableData] = deriveEncoder[TableData]
  given Encoder[Label] = deriveEncoder[Label]
  given Encoder[Node] = deriveEncoder[Node]
  given Encoder[Edge] = deriveEncoder[Edge]
  given Encoder[Graph] = deriveEncoder[Graph]

end SprottyModel

/** Exports layout result to Sprotty-compatible JSON format */
object SprottyExporter:
  import SprottyModel.*

  /** Converts a layout result to Sprotty JSON */
  def toSprottyJson(layout: LayoutResult): Json =
    val nodes = layout.nodes.map(nodeToSprottyNode)
    val edges = layout.edges.map(edgeToSprottyEdge)

    // Encode nodes and edges as Json separately, then combine
    val nodeJsons = nodes.map(_.asJson)
    val edgeJsons = edges.map(_.asJson)

    Graph(
      `type` = "graph",
      id = "root",
      children = (nodeJsons ++ edgeJsons).toList
    ).asJson
  end toSprottyJson

  private def nodeToSprottyNode(node: PositionedNode): Node =
    val nodeType = node.nodeType match
      case NodeType.Simple      => "node:simple"
      case NodeType.Table(_, _) => "node:table"

    val tableData = node.nodeType match
      case NodeType.Table(rows, cols) => Some(TableData(rows, cols))
      case _                          => None

    Node(
      id = node.id,
      `type` = nodeType,
      position = Position(node.x, node.y),
      size = Size(node.width, node.height),
      children = List(
        Label(
          id = s"${node.id}_label",
          `type` = "label",
          text = node.label
        )
      ),
      tableData = tableData
    )
  end nodeToSprottyNode

  private def edgeToSprottyEdge(edge: PositionedEdge): Edge =
    val routingPoints =
      if edge.routingPoints.nonEmpty then Some(edge.routingPoints.map { case (x, y) => RoutingPoint(x, y) }.toList)
      else None

    Edge(
      id = edge.id,
      `type` = "edge",
      sourceId = edge.source,
      targetId = edge.target,
      routingPoints = routingPoints
    )
  end edgeToSprottyEdge

  /** Generates a complete HTML page with embedded graph */
  def toHtml(
      layout: LayoutResult,
      title: String = "Graph Visualization",
      includeStyles: Boolean = true
  ): String =
    val jsonData = toSprottyJson(layout)
    val jsonString = jsonData.spaces2

    s"""<!DOCTYPE html>
       |<html>
       |<head>
       |  <meta charset="UTF-8">
       |  <title>$title</title>
       |  ${
        if includeStyles then defaultStyles else ""
      }
       |</head>
       |<body>
       |  <div id="graph-container"></div>
       |  <script type="module">
       |    // Graph data embedded from backend
       |    const graphData = $jsonString;
       |
       |    // Initialize viewer
       |    const container = document.getElementById('graph-container');
       |
       |    // Simple SVG rendering
       |    container.innerHTML = renderGraphSVG(graphData);
       |
       |    function renderGraphSVG(data) {
       |      const width = Math.max(...data.children.filter(c => c.position).map(c => c.position.x + c.size.width)) + 50;
       |      const height = Math.max(...data.children.filter(c => c.position).map(c => c.position.y + c.size.height)) + 50;
       |
       |      let svg = `<svg width="$${width}" height="$${height}" xmlns="http://www.w3.org/2000/svg">`;
       |
       |      // Arrow marker definition
       |      svg += `<defs>
       |        <marker id="arrowhead" markerWidth="10" markerHeight="10"
       |                refX="9" refY="3" orient="auto">
       |          <polygon points="0 0, 10 3, 0 6" fill="#666"/>
       |        </marker>
       |      </defs>`;
       |
       |      // Render edges first (so they appear behind nodes)
       |      data.children.filter(c => c.type === 'edge').forEach(edge => {
       |        const source = data.children.find(n => n.id === edge.sourceId);
       |        const target = data.children.find(n => n.id === edge.targetId);
       |        if (source && target) {
       |          const x1 = source.position.x + source.size.width / 2;
       |          const y1 = source.position.y + source.size.height;
       |          const x2 = target.position.x + target.size.width / 2;
       |          const y2 = target.position.y;
       |          svg += `<line x1="$${x1}" y1="$${y1}" x2="$${x2}" y2="$${y2}"
       |                  stroke="#666" stroke-width="2" marker-end="url(#arrowhead)"/>`;
       |        }
       |      });
       |
       |      // Render nodes
       |      data.children.filter(c => c.position).forEach(node => {
       |        const x = node.position.x;
       |        const y = node.position.y;
       |        const w = node.size.width;
       |        const h = node.size.height;
       |
       |        // Node rectangle
       |        svg += `<rect x="$${x}" y="$${y}" width="$${w}" height="$${h}"
       |                fill="#e8f4f8" stroke="#333" stroke-width="2" rx="5"/>`;
       |
       |        // Node label
       |        if (node.children && node.children.length > 0) {
       |          const label = node.children[0].text;
       |          svg += `<text x="$${x + w/2}" y="$${y + h/2}"
       |                  text-anchor="middle" dominant-baseline="middle"
       |                  font-family="Arial, sans-serif" font-size="14">$${label}</text>`;
       |        }
       |      });
       |
       |      svg += '</svg>';
       |      return svg;
       |    }
       |  </script>
       |</body>
       |</html>""".stripMargin
  end toHtml

  private def defaultStyles: String =
    """<style>
      |  body {
      |    margin: 0;
      |    padding: 20px;
      |    font-family: Arial, sans-serif;
      |    background: #f5f5f5;
      |  }
      |  #graph-container {
      |    background: white;
      |    border: 1px solid #ddd;
      |    border-radius: 8px;
      |    padding: 20px;
      |    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
      |  }
      |</style>""".stripMargin

end SprottyExporter
