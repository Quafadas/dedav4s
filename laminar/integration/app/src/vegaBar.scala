package integrationapp

import viz.vega.plots.SpecUrl
import viz.LaminarViz
import com.raquo.laminar.api.L.*
import viz.applyMods
import viz.vega.facades.EmbedOptions
import viz.vega.facades.Helpers.safeAddSignalListener
import viz.vega.facades.VegaView
import scala.scalajs.js.JSON

def vegaBar: Div =
  val (aSignalBus, signalCallback: ((x: String, y: scala.scalajs.js.Dynamic) => Unit)) = LaminarViz.signalBus
  val bar1 = SpecUrl.BarChart.jsonSpec.applyMods(
    List(
      // (spec: ujson.Value) => spec("autosize") = ujson.Obj("type" -> "fit", "contains" -> "padding")
      viz.Utils.fillDiv
    )
  )

  val dchart2 = div(
    height := "85vh",
    width := "100vw"
  )

  val parentDiv = div(
    cls := "parent-div",
    child.text <-- aSignalBus.map { x =>
      JSON.stringify(x).toString
    },
    dchart2
  )

  val (d, v) = LaminarViz.viewEmbed(
    upickle.default.write(bar1),
    Some(dchart2),
    Some(EmbedOptions()),
    false
  )

  d.amend(
    (v: Signal[Option[VegaView]]).map((vvOpt: Option[VegaView]) =>
      vvOpt.map((vv: VegaView) => vv.safeAddSignalListener("tooltip", signalCallback))
    ) --> Observer(_ => ())
  )
  parentDiv
end vegaBar
