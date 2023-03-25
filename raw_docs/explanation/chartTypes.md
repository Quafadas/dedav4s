Anything vega can plot. This can plot. Unfortuntately, learning vega is hard work. 

The concept is to start from an existing chart, and evolve it to what you want... this library hooks directly into vegas example library. 

Here are some examples... of using vegas examples... In a repl, try tab completion on `viz.vega.plots.[tab]`. Compare the list of available charts, to the list of vega examples. It ought to be the same... 

# Example Charts
## Vega

https://vega.github.io/vega/examples/

```scala mdoc:js
import viz.vega.plots.{BarChart,LineChart, PieChart, ScatterPlot, given}

viz.doc.showChartJs(BarChart(List(viz.Utils.fillDiv)), node, 50)
viz.doc.showChartJs(LineChart(List(viz.Utils.fillDiv)), node, 50)
viz.doc.showChartJs(PieChart(List(viz.Utils.fillDiv)), node, 50)
viz.doc.showChartJs(ScatterPlot(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl)), node, 50)
```

## Vega-Lite
https://vega.github.io/vega-lite/examples/

```scala mdoc:js
import viz.vega.plots.{SimpleBarChartLite, HistogramLite, ScatterplotLite, InteractiveScatterplotMatrixLite, given}

viz.doc.showChartJs(SimpleBarChartLite(List(viz.Utils.fillDiv)), node, 50)
viz.doc.showChartJs(HistogramLite(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl)), node, 50)
viz.doc.showChartJs(ScatterplotLite(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl)), node, 50)

viz.doc.showChartJs(
  InteractiveScatterplotMatrixLite(
      List( spec => spec("spec")("data")("url") = "https://raw.githubusercontent.com/vega/vega/main/docs/data/cars.json" )
  ),
  node, 
  50
)
```