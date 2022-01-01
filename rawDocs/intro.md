# Explanation


```scala mdoc:invisible
import viz.PlotTargets.printlnTarget
import viz.vega.extensions.*
```

```scala mdoc:vegaplot
(1 to 10).plotBarChart()
```

Ideally, this would show a second chart

```scala mdoc:vegaplot
val secondChart = (1 to 5).plotBarChart()
```