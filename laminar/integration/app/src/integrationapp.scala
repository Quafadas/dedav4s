package integrationapp

import viz.vega.plots.SpecUrl
import viz.LaminarViz
import com.raquo.laminar.api.L.*
import org.scalajs.dom
import viz.vega.facades.EmbedOptions
import viz.applyMods
import viz.vega.facades.Helpers.safeAddSignalListener
import scala.scalajs.js.JSON

@main
def main(): Unit =

  val dchart2 = div(
    height := "85vh",
    width := "100vw"
  )
  val (bus, callback) = LaminarViz.dataClickBus

  val tabs = Seq(
    "Vega Bar" -> (() => vegaBar),
    "Vega Lite Bar" -> (() => vegaLiteBar)
  )
  val selectedTab = Var(0)

  renderOnDomContentLoaded(
    dom.document.body,
    div(
      h1(
        "Viz Play"
      ),
      width := "100vw",
      height := "100vh",
      div(
        div(
          tabs.zipWithIndex.map { case ((label, _), idx) =>
            button(
              label,
              onClick.mapTo(idx) --> selectedTab.writer,
              cls := (if selectedTab.now() == idx then "selected-tab" else "tab")
            )
          }
        )
      ),
      child <-- selectedTab.signal.map { idx =>
        tabs(idx)._2.apply()
      }
    )
  )
end main
