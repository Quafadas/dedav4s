# Scala JS

The charts in these documents, are display using scala JS :-).

What turns out to be really nice about scala JS support, is the seamless transition between exploration in a repl  on the JVM, luxuriating in it's rapid feedback and typsafe tooling, and subsequent publication into a browser with scala JS. It's the same code! There is a only a little more ceremony than with a repl - we need to decide the charts position in the document. i.e. find it a parent.

<mark>Gotcha : dedav ***does not include*** the underlying JS libraries out of it's box</mark>.

I may list out some toy examples on the github readme. Here's one...
[Mill, Scala Js, Snowpack, Laminar, Dedav](https://github.com/Quafadas/scalajs-snowpack-example)

## Scala JS UI frameworks
It turns out, that scala JS Dom is simply a facade for the browser API. Dedav works, through providing a reference to a scala js dom Div element.

Due to how fundamental the statement above is, we implicitly support _all_ JS UI frameworks. It must be possible to coerce the DIV wrapper of your framework into a scala js dom Div. However, some frameworks have a little more polish...

## Integrations

### Laminar

See the `LaminarViz.simpleEmbed` function, to get started. It returns a div, which you can put, anywhere you want in your app. Here it's just added where it's constructed for the sake of simplicity.

The only constraint, is that it must have a well defined size and hieght.

```scala mdoc:js
import com.raquo.laminar.api.L._
import org.scalajs.dom
import viz.extensions.*
import viz.Utils
import viz.LaminarViz
import viz.vega.plots.{BarChart, given}

val appContainer = dom.document.querySelector(s"#${node.id}")
node.setAttribute("style", s"width:50vmin;height:50vmin")
renderOnDomContentLoaded(appContainer, chartExample())

object chartExample:
  val data = Var(List(2.4, 3.4, 5.1, -2.3))
  val chartDiv = div(
    width := "40vmin",
    height := "40vmin",
  )

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
      child <-- data.signal.map { data =>
        val barChart: BarChart = data.plotBarChart(List(viz.Utils.fillDiv))
        LaminarViz.simpleEmbed(barChart, Some(chartDiv))
      },
      p()
    )
  end apply
end chartExample

```

This works quickly and easily, but it has some downsides.

- Replot the whole chart, on every render.
- No interaction. The chart assumes, that the last thing you want to do with it, is get it on the screen.

It turns out, we can do w whole lot better, with vega View ... in the following example. At the expense of a little complexity, our chart is now interactive!

```scala mdoc:js
import com.raquo.laminar.api.L.*

import org.scalajs.dom
import viz.extensions.*
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
      p()
    )
  end apply
end chartExample
```

The key difference, is that we also return the vega view. Hover over, and click the chart items, and you'll see the data printed out. So we have "bi-directional" communication with the chart.

Further, the chart itself, is "updated", rather than thrown away and replotted every time.

Finally, this sets out some low leverl building blocks. If you were to know they types of the things you wanted to plot, one could easily construct some higher level, more typesafe abstractions.

### Calico

Most peculiar

### MDoc
Is how this documentation works. Setup mdoc with scalajs bundler, and include vega in the bundle. Read the source of this library :-).

### Laika with mdoc


Which are a formiddable documentation team. You may need to also work with Laika docs it's [documentation](https://planet42.github.io/Laika/0.18/02-running-laika/01-sbt-plugin.html). This seciton is here to remind me to;

1. disable auto linking (otherwise you'll end up with two sets of imports)
2. enable raw content

```
laikaConfig ~= { _.withRawContent },

and
tlSiteHeliumConfig := {
  .site
  // NOTE: Needed for Javasriptin in Laika
  .autoLinkJS()
}
```

The github repo of this documentation is a successful example!


# Ecosystem support
We have two orthogonal problems

1. How to we obtain the javascript libraries?
2. How to support the bouquet of scala JS UI frameworks?

## Javascript libraries
The example dependency is set out above. It _should_ work with _any_ bundling solution, or even by directly embedding the dependancies in the header of the html. Your choice.

The ~~burden~~ freedom is left to you to get vega itself in scope. The simplest package.json depedancies would be;

```json
"dependencies": {
    "vega-embed" : "6.20.8"
}
```

You could also consider going sans-bundler via ESM modules or directly via a script tag in the header of your html - have a look at the source of this page for such an example.
