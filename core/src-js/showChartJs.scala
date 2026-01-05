package viz.js
import org.scalajs.dom
import org.scalajs.dom.html.Div
import scala.util.Random
import org.scalajs.dom.Element
import scala.scalajs.js.JSON
import viz.ChartLibrary

/** This is a "show" implementation for JS. Importantly, it forces the div to have a well defined width and height on
  * mount Otherwise vega chokes. This can be used as a starting pont for however you'd wish to integrate your own chart.
  *
  * It ought to get you started quickly.
  */
object showChartJs:
  def apply(spec: ujson.Value, node: Element, width: Int = 50)(using chartLib: ChartLibrary) =
    assert(chartLib == ChartLibrary.Vega, "Only Vega is supported for now")
    val child = dom.document.createElement("div")
    val anId = "vega" + Random.alphanumeric.take(8).mkString("")
    child.id = anId
    child.setAttribute("style", s"width:${width}vmin;height:${width}vmin")
    node.appendChild(child)
    child.asInstanceOf[Div]
    val opts = viz.vega.facades.EmbedOptions()
    val parsed = JSON.parse(spec.toString())
    viz.vega.facades.embed(s"#${child.id}", parsed, opts)
    ()
  end apply
end showChartJs

object makePlotTarget:
  def apply(node: Element, width: Int = 50): Div =
    val child = dom.document.createElement("div")
    val anId = "vega" + Random.alphanumeric.take(8).mkString("")
    child.id = anId
    child.setAttribute("style", s"width:${width}vmin;height:${width}vmin")
    node.appendChild(child)
    child.asInstanceOf[Div]
  end apply
end makePlotTarget
