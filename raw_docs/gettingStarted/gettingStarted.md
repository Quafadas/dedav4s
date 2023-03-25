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

This code fence uses scala JS. In scala JS, we construct charts with _exactly the same code_ as on the JVM. 

```scala mdoc:js
import viz.vega.plots.{BarChart, given}
viz.doc.showChartJs(BarChart(), node)
```

Because it's cross compiled from the same shared source code! That promise - explore in the comfort of the JVM, publish in scala JS is one of the big concepts this library explores. 

I like a big chart... let's see if we can fill the div.

```scala mdoc:js
import viz.vega.plots.{BarChart, given}
val chart = BarChart(
  List(
    viz.Utils.fillDiv
  )
)
viz.doc.showChartJs(chart, node)
```

If you're curious about how all worked, then you're ready to go! Read on! 

See the [plot targets](../explanation/plotTargets.md) to understand what happened, and the [examples](../working_chart/workflow.md) for suggestions on how to use and extend the concepts.
