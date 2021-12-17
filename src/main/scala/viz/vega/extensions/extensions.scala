package viz.vega.extensions

import viz.vega.plots.BarChart
import viz.PlotTarget
import scala.util.Random
import scala.annotation.targetName
import viz.vega.extensions
import viz.vega.plots.WordCloud

extension [T: Numeric](l: Iterable[T])(using plotTarget: PlotTarget)
  def plotBarChart(): BarChart =
    val labelToNotShow =
      for (number <- l)
        yield ujson.Obj(
          "category" -> Random.alphanumeric.take(8).mkString(""),
          "amount" -> number.toString
        )
    new BarChart(
      List(
        viz.Utils.fillDiv,
        spec => spec("data")(0)("values") = labelToNotShow,
        spec =>
          spec("axes").arr.dropWhileInPlace(ax => ax("scale").str == "xscale")
      )
    )

extension [T: Numeric](l: Iterable[(String, T)])(using plotTarget: PlotTarget)
  @targetName("plotBarChartWithLabels")
  def plotBarChart(): BarChart =
    val labelled =
      for ((label, number) <- l)
        yield ujson.Obj(
          "category" -> label,
          "amount" -> number.toString
        )

    new BarChart(
      List(
      spec => spec("data")(0)("values") = labelled,
      viz.Utils.fillDiv,
      )
    )
    
extension (s: String)(using plotTarget: PlotTarget)
  def plotWordcloud(): WordCloud = List(s).plotWordcloud()

extension (s: Seq[String])(using plotTarget: PlotTarget)
  def plotWordcloud(): WordCloud = 
    new WordCloud(
      List(
        spec => spec("data")(0)("values").arr.clear(),
        spec => spec("data")(0)("values") = s,
        viz.Utils.fillDiv
      )
    )
