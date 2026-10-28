package viz.notebook

import almond.api.JupyterApi
import almond.api.JupyterAPIHolder.value
import almond.interpreter.api.DisplayData
import viz.ChartLibrary
import viz.PlotTarget
import viz.UnitTarget

/** @param width
  *   width of the static SVG, for ECharts plots and for Vega specs sized to their container
  * @param height
  *   height of the static SVG, as for `width`
  * @param staticSvg
  *   publish an SVG alongside the interactive plot, which static viewers (GitHub, nbviewer) show
  * @param editorLink
  *   publish a link that opens Vega and Vega-Lite plots in the online Vega editor
  */
final case class NotebookOptions(
    width: Int = 640,
    height: Int = 400,
    staticSvg: Boolean = true,
    editorLink: Boolean = true
)

object NotebookTargets:

  val VegaMime = "application/vnd.vega.v5+json"
  val VegaLiteMime = "application/vnd.vegalite.v5+json"

  /** Links longer than this (big inline datasets) are left out rather than bloat the notebook. */
  val MaxEditorUrlLength = 32_000

  /** Shows plots in a Jupyter notebook running the almond kernel.
    *
    * Each plot is published as one output carrying several representations, and each viewer picks the best one it can
    * show:
    *   - Vega and Vega-Lite as their own mimetype, which JupyterLab and VS Code render interactively
    *   - a static SVG, rendered in the JVM, for viewers that don't run code, e.g. GitHub. ECharts plots have no
    *     notebook mimetype, so they are shown as this SVG everywhere
    *   - plain text, as a last resort
    *
    * Vega and Vega-Lite plots are followed by a link to the online Vega editor.
    *
    * For the custom interactive (tooltips, dark mode) rendering, see `viz.PlotTargets.almond_js`.
    */
  given almond: PlotTarget = almondWith(NotebookOptions())

  def almondWith(options: NotebookOptions): PlotTarget = new UnitTarget:
    override def show(spec: ujson.Value, lib: ChartLibrary): Unit =
      val kernel = summon[JupyterApi]
      for output <- outputs(spec, lib, options) do kernel.publish.display(output)
      end for
    end show

  /** The outputs [[almond]] publishes for one plot, in order. */
  def outputs(spec: ujson.Value, lib: ChartLibrary, options: NotebookOptions): Seq[DisplayData] =
    lib match
      case ChartLibrary.Vega =>
        val lite = StaticRenderer.isVegaLite(spec)
        val svg = staticSvg(options)(StaticRenderer.vegaSvg(spec, options.width, options.height))
        val plot = DisplayData.empty.withDetailedData(
          Seq(
            (if lite then VegaLiteMime else VegaMime) -> DisplayData.Value.Json(spec.render())
          ) ++ svg.map(s => "image/svg+xml" -> DisplayData.Value.String(s)) ++ Seq(
            "text/plain" -> DisplayData.Value.String(describe(spec, if lite then "Vega-Lite" else "Vega"))
          )
        )
        val link = if options.editorLink then editorLink(spec, lite).toSeq else Seq.empty
        plot +: link

      case ChartLibrary.Echarts =>
        val svg = staticSvg(options)(StaticRenderer.echartsSvg(spec, options.width, options.height))
        Seq(
          DisplayData.empty.withDetailedData(
            svg.map(s => "image/svg+xml" -> DisplayData.Value.String(s)).toSeq ++ Seq(
              "text/plain" -> DisplayData.Value.String(describe(spec, "ECharts"))
            )
          )
        )

  private def staticSvg(options: NotebookOptions)(render: => Either[String, String]): Option[String] =
    if !options.staticSvg then None
    else
      render match
        case Right(svg)   => Some(svg)
        case Left(reason) =>
          System.err.println(s"dedav4s: no static image for this plot, it won't show in static viewers ($reason)")
          None

  private def describe(spec: ujson.Value, library: String): String =
    spec.objOpt
      .flatMap(o => o.get("description").orElse(o.get("title")))
      .flatMap(t => t.strOpt.orElse(t.objOpt.flatMap(_.get("text")).flatMap(_.strOpt)))
      .fold(s"$library plot")(d => s"$library plot: $d")

  /** A link opening the spec in the Vega editor, as markdown and html (whichever the viewer shows). */
  def editorLink(spec: ujson.Value, lite: Boolean): Option[DisplayData] =
    val mode = if lite then "vega-lite" else "vega"
    StaticRenderer
      .compressForUrl(spec.render())
      .toOption
      .map(compressed => s"https://vega.github.io/editor/#/url/$mode/$compressed")
      .filter(_.length <= MaxEditorUrlLength)
      .map { url =>
        val label = "Open in the Vega editor"
        DisplayData.empty.withDetailedData(
          Seq(
            "text/markdown" -> DisplayData.Value.String(s"[$label]($url)"),
            "text/html" -> DisplayData.Value.String(s"""<a href="$url" target="_blank">$label</a>"""),
            "text/plain" -> DisplayData.Value.String(s"$label: $url")
          )
        )
      }
  end editorLink
end NotebookTargets
