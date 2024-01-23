## Getting Started
To add this library to an sbt project
```scala
libraryDependencies += "io.github.quafadas" %% "dedav4s" % "@VERSION@"
```
To use this library in ammonite / mill
```scala
import $ivy.`io.github.quafadas::dedav4s:@VERSION@`
```

Fire up an sbt console (or in a repl... )

```scala mdoc
import viz.vega.plots.{BarChart, given}
BarChart()
```

And now you have a `BarChart` object. But we can't see it, which sort of defies the point of plotting stuff. Now let's try this;
```scala
import viz.PlotTargets.desktopBrowser
BarChart()
```
And a browser window should have popped up, with a bar chart in. It should look very similar, to the chart plotted out of scala JS, below.

This code fence uses scala JS. In scala JS, we construct charts with _exactly the same code_ as on the JVM. The difference is we assign the chart to a variable, so we can plot. I like a big chart... so we'll also apply a modifier to fill the div.

```scala mdoc:js
import viz.vega.plots.{BarChart, given}
val chart = BarChart(
  List(
    viz.Utils.fillDiv
  )
)
viz.js.showChartJs(chart, node)
```
It gets mounted as a child of `node`, which is provided to us by the excellent mdocJS.

Excitingly, the chart we plotted out repl (JVM) and the chart displayed here, share the same definition! That promise - explore in the comfort of the JVM, publish in scala JS is attractive. There a trick here, which **makes charting unit testable**. We'll come back to that later.

So we've proven we can make a chart, and change it's behaviour. If all that worked in your repl, then you're ready to go! Read on!

See the [plot targets](../explanation/plotTargets.md) to understand what happened, and the [examples](../explanation/chartTypes.md) for suggestions on how to use and extend the concepts.
