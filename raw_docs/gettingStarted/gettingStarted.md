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
And a browser window should have popped up, with a bar chart in. It should very similar, to the chart plotted out of scala JS, below.

This code fence uses scala JS. Observe how similar the code is to the code you just ran in the REPL. 

```scala mdoc:js
import viz.vega.plots.{BarChart, given}
import org.scalajs.dom.html.Div
import viz.doc.makePlotTarget

val child : Div = makePlotTarget(node, 50)
BarChart(List())
```

In fact, it's cross compiled from the same shared source code! That promise - explore in the REPL, publish in scala JS. I believe to be an attractive proposition.

I like a big chart... let's see if we can fill the div.
```scala
BarChart(List(viz.Utils.fillDiv))
```

```scala mdoc:js:invisible
import viz.vega.plots.{BarChart, given}
import org.scalajs.dom.html.Div
import viz.doc.makePlotTarget
val inDiv : Div = makePlotTarget(node, 50)
val chart = BarChart(List(viz.Utils.fillDiv))
viz.doc.showChartJs(inDiv, chart)
```

If you're curious about how that worked, then you're ready to go! Read on! 

See the [plot targets](../explanation/plotTargets.md) to understand what happened, and the [examples](../working_chart/workflow.md) for suggestions on how to use and extend the concepts.
