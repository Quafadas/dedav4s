package integrationapp

import viz.vega.plots.SpecUrl
import viz.LaminarViz
import com.raquo.laminar.api.L.*
import viz.applyMods
import viz.vega.facades.EmbedOptions
import viz.vega.facades.Helpers.safeAddSignalListener
import scala.scalajs.js.JSON
import scala.scalajs.js

def vegaLiteBar: Div =
  val (aSignalBus, signalCallback) = LaminarViz.dataClickBus
  val bar2 = SpecUrl.SimpleBarChartwithLabelsandEmojisLite.jsonSpec.applyMods(
    List(
      viz.Utils.fillDiv,
      (spec: ujson.Value) =>
        spec("layer")(0)("mark") = ujson.Obj(
          "type" -> "bar",
          "tooltip" -> true
        )
    )
  )
  def textIfObject(in: js.UndefOr[js.Dynamic]): String =
    if in == js.undefined then "undefined"
    else JSON.stringify(in.get)

  val dchart2 = div(
    height := "85vh",
    width := "100vw"
  )

  val parentDiv = div(
    cls := "parent-div",
    child <-- aSignalBus.map(o => p(textIfObject(o))),
    dchart2
  )

  val (d, v) = LaminarViz.viewEmbed(
    upickle.default.write(bar2),
    Some(dchart2),
    Some(EmbedOptions()),
    false
  )

  d.amend(
    v.map(_.map(vv => vv.addEventListener("click", signalCallback))) --> Observer(_ => ())
  )
  parentDiv
end vegaLiteBar
