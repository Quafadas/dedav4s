package viz.graph

import upickle.default.*

/** Exports layout result to Sprotty-compatible JSON format */
object SprottyExporter:

  /** Converts a layout result to Sprotty JSON */
  def toSprottyJson(layout: LayoutResult): ujson.Value =
    val nodeChildren = layout.nodes.map(nodeToJson)
    val edgeChildren = layout.edges.map(edgeToJson)
    ujson.Obj(
      "type" -> "graph",
      "id" -> "root",
      "children" -> ujson.Arr.from(nodeChildren ++ edgeChildren)
    )
  end toSprottyJson

  private def nodeToJson(node: PositionedNode): ujson.Value =
    val baseObj = ujson.Obj(
      "id" -> node.id,
      "type" -> nodeTypeString(node.nodeType),
      "position" -> ujson.Obj(
        "x" -> node.x,
        "y" -> node.y
      ),
      "size" -> ujson.Obj(
        "width" -> node.width,
        "height" -> node.height
      ),
      "children" -> ujson.Arr(
        ujson.Obj(
          "id" -> s"${node.id}_label",
          "type" -> "label",
          "text" -> node.label
        )
      )
    )

    // Add table-specific data if applicable
    node.nodeType match
      case NodeType.Table(rows, cols) =>
        baseObj("tableData") = ujson.Obj(
          "rows" -> rows,
          "columns" -> cols
        )
      case _ => ()
    end match

    baseObj
  end nodeToJson

  private def edgeToJson(edge: PositionedEdge): ujson.Value =
    val obj = ujson.Obj(
      "id" -> edge.id,
      "type" -> "edge",
      "sourceId" -> edge.source,
      "targetId" -> edge.target
    )

    // Add routing points if available
    if edge.routingPoints.nonEmpty then
      obj("routingPoints") = ujson.Arr(
        edge.routingPoints.map { case (x, y) =>
          ujson.Obj("x" -> x, "y" -> y)
        }*
      )
    end if

    obj
  end edgeToJson

  private def nodeTypeString(nodeType: NodeType): String =
    nodeType match
      case NodeType.Simple      => "node:simple"
      case NodeType.Table(_, _) => "node:table"

  /** Generates a complete HTML page with embedded graph */
  def toHtml(
      layout: LayoutResult,
      title: String = "Graph Visualization",
      includeStyles: Boolean = true
  ): String =
    val jsonData = toSprottyJson(layout)
    val jsonString = ujson.write(jsonData, indent = 2)

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
