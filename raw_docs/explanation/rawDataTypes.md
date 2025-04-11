
## "RawIterables" Extension Methods

For convienience, sometimes I can easily get "raw datatypes" which I just wan to plot quickly an with minimum ceremoney.

Pie charts, bar charts and the like, which are always going to look very similar to the examples on the vega website, and come from a simple datastructure. We want to be able to plot these as quickly as possible.

We achieve this through scala 3's extension methods - have a look in the ```viz.extensions.RawIterables.*``` package for ideas. Feel free to contribute more or create your own - it's quick enough.

### Labelled bar chart
```scala mdoc:js
import viz.extensions.*
import viz.PlotTargets.doNothing

val chart = List(("A", 4),("B", 6),("C", -1)).barSpec(List())
viz.js.showChartJs(chart,node)
```

### Bar chart
```scala mdoc:js
import viz.extensions.RawIterables.*
import viz.PlotTargets.doNothing

viz.js.showChartJs(
  (1 to 5).plotBarChart( List(viz.Utils.fillDiv) ),
  node
)

```

### Pie chart
```scala mdoc:js
import viz.extensions.RawIterables.*
import viz.PlotTargets.doNothing

viz.js.showChartJs(
  (1 to 5).plotPieChart(List(viz.Utils.fillDiv)),
  node
)
```

### Pie chart with labels...
```scala mdoc:js
import viz.extensions.RawIterables.*
import viz.PlotTargets.doNothing

viz.js.showChartJs(
  (1 to 5).map(i => (scala.util.Random.nextString(5), i)).plotPieChart(List()),
  node
)


```

### Line Chart
```scala mdoc:js
import viz.extensions.RawIterables.*
import viz.PlotTargets.doNothing

viz.js.showChartJs(
  List(1,5,3,15,7,8).lineSpec,
  node
)
```

### Scatter Plot
```scala mdoc:js
import viz.extensions.RawIterables.*
import viz.PlotTargets.doNothing

viz.js.showChartJs(
  List((1.0,2.0),(3.0,4.0),(0.5 , 5.0),(3.14159, 1.0)).scatterSpec,
  node
)
```