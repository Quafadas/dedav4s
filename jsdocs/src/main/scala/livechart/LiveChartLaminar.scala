package livechart

import scala.scalajs.js
import com.raquo.laminar.api.L.*
import scala.util.Random

import scalajs.js.JSON

import viz.LaminarViz
import js.JSConverters.*

import viz.vega.facades.VegaView
import viz.vega.facades.Helpers.*
import viz.vega.plots.SpecUrl
import viz.js.extensions.*

// @main
// def LiveChart(): Unit =
//   renderOnDomContentLoaded(
//     dom.document.getElementById("app"),
//     // div()
//     chartExample.vegaView()
//   )
// end LiveChart

def textIfObject(in: js.UndefOr[js.Dynamic]): String =
  if in == js.undefined then "undefined"
  else JSON.stringify(in.get)

object chartExample:

  val (chartDataClickedBus, chartClickCallback) = LaminarViz.dataClickBus
  val (aSignalBus, signalCallback) = LaminarViz.signalBus
  val data = Var(List(2.4, 3.4, 5.1, -2.3))
  val baseChart = SpecUrl.BarChart.jsonSpec

  def vegaView(): Div =
    val (chartDiv: Div, viewOpt: Signal[Option[VegaView]]) = LaminarViz.viewEmbed(baseChart.toString())

    div(
      viewOpt.map(_.map(vv =>
        vv.safeAddSignalListener("tooltip", signalCallback)
        vv.addEventListener("click", chartClickCallback)
        // vv.addEventListener("click", dataPrintOnlyClickHandler)
        // vv.printState()
      )) --> Observer(_ => ()),
      p("We also want to find a way, to interact with the chart"),
      span("Here's a random data set: "),
      child.text <-- data.signal.map { data =>
        data.mkString("[", ",", "]")
      },
      p(
        button(
          "Add a random number",
          onClick --> { _ =>
            data.update { data =>
              data :+ scala.util.Random.nextDouble() * 5
            }
          }
        )
      ),
      data.signal.combineWith(viewOpt) --> Observer { (in: (List[Double], Option[VegaView])) =>
        val data = in._1
        val theView = in._2
        theView.foreach { view =>
          val dataJs: scala.scalajs.js.Array[js.Object] = data
            .map(d =>
              js.Dynamic.literal(
                category = Random.alphanumeric.take(8).mkString(""),
                amount = d
              )
            )
            .toJSArray
          view.data("table", dataJs)
          view.runAsync() // Don't forget this or nothing happens :-)
        }

      },
      chartDiv,
      p("You last clicked on : ", child.text <-- chartDataClickedBus.map(textIfObject)),
      p("You last hovered on : ", child.text <-- aSignalBus.map(textIfObject))
    )

  end vegaView

  def apply(): Div =
    div(
      p("We want to make it as easy as possible, to build a chart"),
      span("Here's a random data set: "),
      child.text <-- data.signal.map { data =>
        data.mkString("[", ",", "]")
      },
      button(
        "Add a random number",
        onClick --> { _ =>
          data.update { data =>
            data :+ scala.util.Random.nextDouble() * 5
          }
        }
      ),
      p(),
      child <-- data.signal.map { data =>
        val data2 = data.map(d => (d, d.toString()))
        val barSpec = data2.barSpec(List(viz.Utils.fillDiv))
        LaminarViz.simpleEmbed(barSpec.toString())
      }
    )
  end apply
end chartExample
