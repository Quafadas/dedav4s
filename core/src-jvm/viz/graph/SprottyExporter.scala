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
       |  ${if includeStyles then defaultStyles else ""}
       |</head>
       |<body>
       |  <div id="sprotty"></div>
       |  <script type="module">
       |    // Import Sprotty as ESM module from CDN
       |    import { TYPES, ConsoleLogger, LogLevel, loadDefaultModules, LocalModelSource, 
       |             SGraphView, SLabelView, PolylineEdgeView, configureModelElement, 
       |             SGraph, SNode, SEdge, SLabel, SGraphFactory } from 'https://unpkg.com/sprotty@1.1.0/lib/index.js';
       |    import { Container } from 'https://unpkg.com/inversify@6.0.2/es/index.js';
       |    
       |    // Graph data embedded from backend
       |    const graphData = $jsonString;
       |    
       |    // Create Sprotty container with default modules
       |    const container = new Container();
       |    loadDefaultModules(container);
       |    
       |    // Configure view for node types
       |    configureModelElement(container, 'graph', SGraph, SGraphView);
       |    configureModelElement(container, 'node:simple', SNode, SGraphView);
       |    configureModelElement(container, 'node:table', SNode, SGraphView);
       |    configureModelElement(container, 'label', SLabel, SLabelView);
       |    configureModelElement(container, 'edge', SEdge, PolylineEdgeView);
       |    
       |    // Set up logger
       |    container.bind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope();
       |    container.bind(TYPES.LogLevel).toConstantValue(LogLevel.warn);
       |    
       |    // Create model source
       |    const modelSource = container.get(LocalModelSource);
       |    
       |    // Set the model and render
       |    modelSource.setModel(graphData);
       |    
       |    // Initialize Sprotty on the container div
       |    modelSource.updateModel();
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
      |  #sprotty {
      |    background: white;
      |    border: 1px solid #ddd;
      |    border-radius: 8px;
      |    padding: 20px;
      |    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
      |    width: 100%;
      |    height: 600px;
      |  }
      |  /* Sprotty SVG styling */
      |  .sprotty-graph > .sprotty-node {
      |    fill: #e8f4f8;
      |    stroke: #333;
      |    stroke-width: 2px;
      |  }
      |  .sprotty-edge {
      |    stroke: #666;
      |    stroke-width: 2px;
      |    fill: none;
      |  }
      |  .sprotty-edge > .arrow {
      |    fill: #666;
      |  }
      |  .sprotty-label {
      |    font-family: Arial, sans-serif;
      |    font-size: 14px;
      |    text-anchor: middle;
      |    dominant-baseline: middle;
      |  }
      |</style>""".stripMargin

end SprottyExporter
