package viz.static

import viz.ChartLibrary
import viz.Extension
import viz.PlotTarget
import viz.PlotTargets
import viz.Png
import viz.Svg
import viz.TempFileTarget
import viz.VizReturn

/** @param width
  *   width of the image, for ECharts plots and for Vega specs sized to their container. Vega specs with their own size
  *   keep it.
  * @param height
  *   height of the image, as for `width`
  * @param scale
  *   PNG only: pixels per SVG unit. The default, 2, stays sharp on high density screens.
  */
final case class StaticOptions(
    width: Int = 640,
    height: Int = 400,
    scale: Double = 2.0
)

/** Plot targets that write the plot to an image file and return its path. Rendering happens inside the JVM (see
  * [[StaticRenderer]]); nothing needs installing.
  *
  * {{{
  * import viz.static.StaticTargets.png
  * val path = (1 to 10).plotBarChart()
  * }}}
  *
  * Files go to a temp file, or to the `dedavOutPath` directory when that is configured.
  */
object StaticTargets:

  given svg: PlotTarget = svgWith(StaticOptions())
  given png: PlotTarget = pngWith(StaticOptions())

  def svgWith(options: StaticOptions): PlotTarget =
    imageTarget(Svg)(StaticRenderer.svg(_, _, options.width, options.height).map(_.getBytes("UTF-8")))

  def pngWith(options: StaticOptions): PlotTarget =
    imageTarget(Png)(StaticRenderer.png(_, _, options.width, options.height, options.scale))

  // Unlike the notebook, where the image is a fallback, here it is the whole point: a failed render throws.
  private def imageTarget(format: Extension)(render: (ujson.Value, ChartLibrary) => Either[String, Array[Byte]]) =
    new TempFileTarget(format):
      def show(spec: ujson.Value, lib: ChartLibrary): VizReturn =
        val path = PlotTargets.outPath match
          case Some(dir) => os.temp(dir = os.Path(dir), suffix = format.ext, prefix = "plot-")
          case None      => os.temp(suffix = format.ext, prefix = "plot-")
        showWithTempFile(spec.toString, path, lib)
        path
      end show

      override def showWithTempFile(spec: String, path: os.Path, lib: ChartLibrary): Unit =
        render(ujson.read(spec), lib) match
          case Right(bytes) => os.write.over(path, bytes)
          case Left(reason) => throw new Exception(s"Could not render the plot to ${format.ext}: $reason")
end StaticTargets
