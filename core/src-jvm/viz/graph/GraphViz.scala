package viz.graph

/** High-level API for graph visualization */
object GraphViz:

  /** Creates a graph visualization from a DAG */
  def visualize(
      dag: DAG,
      direction: LayoutDirection = LayoutDirection.Down,
      nodeSpacing: Double = 20.0,
      layerSpacing: Double = 50.0
  ): GraphVisualization =
    // Validate DAG
    dag.validate() match
      case Left(error) =>
        throw new IllegalArgumentException(s"Invalid DAG: $error")
      case Right(_) => ()
    end match

    // Compute layout
    val layoutEngine = new ELKLayoutEngine()
    val layout =
      layoutEngine.computeLayout(dag, direction, nodeSpacing, layerSpacing)

    new GraphVisualization(layout)
  end visualize

  /** Quick helper to create and visualize a simple graph */
  def simple(edges: (String, String)*): GraphVisualization =
    val dag = DAG.simple(edges*)
    visualize(dag)
  end simple

end GraphViz

/** A graph visualization that can be exported to various formats */
class GraphVisualization(val layout: LayoutResult):

  /** Exports to Sprotty-compatible JSON */
  def toJson: ujson.Value =
    SprottyExporter.toSprottyJson(layout)

  /** Exports to a standalone HTML file */
  def toHtml(title: String = "Graph Visualization"): String =
    SprottyExporter.toHtml(layout, title)

  /** Saves the visualization as an HTML file */
  def saveHtml(path: os.Path, title: String = "Graph Visualization"): Unit =
    val html = toHtml(title)
    os.write.over(path, html)
  end saveHtml

  /** Returns the JSON as a formatted string */
  def toJsonString: String =
    ujson.write(toJson, indent = 2)

end GraphVisualization
