dedav is a shim to Vega. Anything vega can plot, it can plot. Learning vega is a fun journey that this library _does not attempt to insulate you from_. Rather it encourages it. Vega is battle tested, has great resources an internet full of examples and is super bot-friendly.

Here's a chart written as a `NamedTuple`.

```scala mdoc:js
import viz.Macros.Implicits.given_Writer_T
import viz.vegaFlavour

val spec = (
  `$schema` = "https://vega.github.io/schema/vega-lite/v6.json",
  width = "container",
  height = "container",
  data = (url = "https://raw.githubusercontent.com/vega/vega/refs/heads/main/docs/data/cars.json"),
  mark = "bar",
  encoding = (
    x = (`field` = "Origin"),
    y = (
      aggregate = "count",
      title = "Number of Cars"
    )
  )
)

val specJs = upickle.default.writeJs(spec)

viz.js.showChartJs(specJs, node, 25)
```

Note: JSON serialisation only works, if the type inference is more specific than `AnyNamedTuple`.

To get started, find an [existing chart](https://vega.github.io/vega/examples/), and evolve it to what you want... this library hooks directly into vegas repository of example charts.

Here are some examples... of using vegas examples (!), but making them bigger.

In a repl, try tab completion on `viz.vega.plots.[tab]`. Compare the list of available charts, to the list of vega examples. It ought to be the same...

# Example Charts
## Vega

https://vega.github.io/vega/examples/

```scala mdoc:js
import viz.vega.plots.SpecUrl
import viz.{vegaFlavour, *}


viz.js.showChartJs(SpecUrl.BarChart.jsonSpec.mod(List(viz.Utils.fillDiv)), node, 50)
viz.js.showChartJs(SpecUrl.LineChart.jsonSpec.mod(List(viz.Utils.fillDiv)), node, 50)
viz.js.showChartJs(SpecUrl.PieChart.jsonSpec.mod(List(viz.Utils.fillDiv)), node, 50)
viz.js.showChartJs(SpecUrl.ScatterPlot.jsonSpec.mod(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl)), node, 50)
```

## Vega-Lite
https://vega.github.io/vega-lite/examples/

```scala mdoc:js
import viz.vega.plots.SpecUrl
import viz.{vegaFlavour, *}

viz.js.showChartJs(SpecUrl.SimpleBarChartLite.jsonSpec.mod(List(viz.Utils.fillDiv)), node, 50)
viz.js.showChartJs(SpecUrl.HistogramLite.jsonSpec.mod(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl)), node, 50)
viz.js.showChartJs(SpecUrl.ScatterplotLite.jsonSpec.mod(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl)), node, 50)

viz.js.showChartJs(
  SpecUrl.InteractiveScatterplotMatrixLite.jsonSpec.mod(
      List( spec => spec("spec")("data")("url") = "https://raw.githubusercontent.com/vega/vega/main/docs/data/cars.json" )
  ),
  node,
  50
)
```