# Laminar

See the `LaminarViz.simpleEmbed` function, to get started. It returns a `div`, which you can put, anywhere you want in your app.

> The div _must_ have a well defined size and height.

If div height and width are not well defined, this will usually result in an error in the console. The chart will not render.

## Simple strategy

```scala mdoc:js
import com.raquo.laminar.api.L._
import org.scalajs.dom
import viz.extensions.RawIterables.*
import viz.Utils
import viz.LaminarViz
import viz.vega.plots.{BarChart, given}

val appContainer = dom.document.querySelector(s"#${node.id}")
node.setAttribute("style", s"width:50vmin;height:50vmin")
renderOnDomContentLoaded(appContainer, chartExample())

object chartExample:
  val data = Var(List(2.4, 3.4, 5.1, -2.3))

  def apply(): Div =
    div(
      p("We want to make it as easy as possible, to build a chart and get our data into it."),
      span("Here's a random data set: "),
      child.text <-- data.signal.map { data =>
        data.mkString("[", ",", "]")
      },
      p(),
      button(
        "Add a random number",
        onClick --> {_=>data.update{data =>data :+ scala.util.Random.nextDouble() * 5}}),
      p(),
      div(
        width := "40vmin",
        height := "40vmin",
        child <-- data.signal.map { data =>
          val barChart: BarChart = data.plotBarChart(List(viz.Utils.fillDiv))
          LaminarViz.simpleEmbed(barChart)
        }
      ),
      p()
    )
  end apply
end chartExample

```

This works quickly and easily, but it has some downsides.

- Replot the whole chart, on every render.
- No interaction. The chart assumes, that the last thing you want to do with it, is get it on the screen.

## Using vegas `View` class

It turns out, we can do a whole lot better, using `vega.View`. In the following example, At the expense of a little complexity, our chart is now

- interactive
- performant

Inside the apply method we:

- Instantiate the chart, and obtain both a div, and the `View` object.
- We then use the `View` object to add a signal listener, and a click listener, which return data from the chart into Laminar `Vars`.
- When the button is clicked
    - We update the data via standard laminar observer.
    - The data `Var` has an observed registered, which pipes the data directly into the chart object - this does _not_ involve reinstantiating the chart. Rather, it plugs into vegas eventstream - which is performant.
    ```
    view.data("table", dataJs)
    view.runAsync() // Don't forget this or nothing happens :-)
    ```
-



```scala mdoc:js
import com.raquo.laminar.api.L.*

import org.scalajs.dom
import viz.Utils
import viz.LaminarViz
import viz.vega.facades.VegaView
import viz.vega.facades.Helpers.*
import scala.scalajs.js
import js.JSConverters.*
import scala.util.Random

import viz.vega.plots.{BarChart, given}

val appContainer = dom.document.querySelector(s"#${node.id}")
node.setAttribute("style", s"width:50vmin;height:50vmin")
renderOnDomContentLoaded(appContainer, chartExample())

def textIfObject(in: js.UndefOr[js.Dynamic]): String =
  if in == js.undefined then "undefined"
  else js.JSON.stringify(in.get)


object chartExample:
  val (chartDataClickedBus, chartClickCallback) = LaminarViz.dataClickBus
  val (aSignalBus, signalCallback) = LaminarViz.signalBus
  val data = Var(List(2.4, 3.4, 5.1, -2.3))
  val baseChart = BarChart(
    List(
      viz.Utils.fillDiv,
      viz.Utils.removeXAxis,
      viz.Utils.removeYAxis
    )
  )
  val setDivSize = div(
    width := "40vmin",
    height := "40vmin",
  )

  def apply(): Div =
    val (chartDiv : Div, viewOpt: Signal[Option[VegaView]]) =
      LaminarViz.viewEmbed(baseChart, Some(setDivSize))

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
      data.signal.combineWith(viewOpt) --> Observer {
        (in: (List[Double], Option[VegaView])) =>
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
      p("You last hovered on : ", child.text <-- aSignalBus.map(textIfObject)),
      p(),p("")
    )
  end apply
end chartExample
```

To use it - hover over, and / or click the chart items, and you'll see the data printed out. So we have "bi-directional" communication with the chart.

Further, the chart itself, is "updated", rather than thrown away and replotted every time.

This sets out some low level building blocks. A motivated developer could establish reliable, typesafe communication with the chart. Due to how close Laminars reactive paradigm is to vegas event stream - they play startling nicely together.

