package viz.graph

import io.circe.Json

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
  def toJson: Json =
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
    toJson.spaces2

  /** Displays the graph using the configured plot target
    *
    * This method integrates with dedav4s plot target system. The HTML is stored in a JSON wrapper to maintain
    * compatibility with the ujson.Value interface used by plot targets.
    *
    * @param plotTarget
    *   The target for rendering (e.g., desktopBrowser, tempHtmlFile)
    * @param title
    *   Optional title for the graph visualization
    * @return
    *   The result from the plot target (typically a file path)
    */
  def plot(title: String = "Graph Visualization")(using
      plotTarget: viz.LowPriorityPlotTarget
  ): viz.VizReturn =
    // Wrap the HTML in a ujson string for the plot target
    val html = toHtml(title)
    val spec = ujson.Str(html)
    plotTarget.show(spec, viz.ChartLibrary.Graph)
  end plot

end GraphVisualization
