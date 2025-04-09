dedav is a shim to Vega. Anything vega can plot, it can plot. Learning vega is a wonderful journey that this library, does not attempt to insulate you from. Rather it encourages it. Vega is battle tested, has great resources and an internet full of examples. I think it's worth learning.

To get started, find an [existing chart](https://vega.github.io/vega/examples/), and evolve it to what you want... this library hooks directly into vegas repository of example charts.

Here are some examples... of using vegas examples (!), but making them bigger.

In a repl, try tab completion on `viz.vega.plots.[tab]`. Compare the list of available charts, to the list of vega examples. It ought to be the same...

# Example Charts
## Vega

https://vega.github.io/vega/examples/

```scala mdoc:js
import viz.vega.plots.{SpecUrl, given}

viz.js.showChartJs(SpecUrl.BarChart.jsonSpec(List(viz.Utils.fillDiv)), node, 50)
viz.js.showChartJs(SpecUrl.LineChart.jsonSpec(List(viz.Utils.fillDiv)), node, 50)
viz.js.showChartJs(SpecUrl.PieChart.jsonSpec(List(viz.Utils.fillDiv)), node, 50)
viz.js.showChartJs(SpecUrl.ScatterPlot.jsonSpec(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl)), node, 50)
```

## Vega-Lite
https://vega.github.io/vega-lite/examples/

```scala mdoc:js
import viz.vega.plots.{SimpleBarChartLite, HistogramLite, ScatterplotLite, InteractiveScatterplotMatrixLite, given}

viz.js.showChartJs(SpecUrl.SimpleBarChartLite(List(viz.Utils.fillDiv)), node, 50)
viz.js.showChartJs(SpecUrl.HistogramLite(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl)), node, 50)
viz.js.showChartJs(SpecUrl.ScatterplotLite(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl)), node, 50)

viz.js.showChartJs(
  SpecUrl.InteractiveScatterplotMatrixLite(
      List( spec => spec("spec")("data")("url") = "https://raw.githubusercontent.com/vega/vega/main/docs/data/cars.json" )
  ),
  node,
  50
)
```