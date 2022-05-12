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
import viz.PlotTargets.desktopBrowser
import viz.extensions.*
import viz.vega.plots.BarChart
```

```scala mdoc:reset:invisible
import viz.PlotTargets.doNothing
import viz.extensions.*
import viz.vega.plots.BarChart
```

```scala mdoc
BarChart(List())
```
A side effect should open a browser window, that should look like the plot below the next code fence. 

This code fence uses scala JS. Observe how similar the code is to the code you just ran in the REPL. 

```scala mdoc:js
import viz.vega.plots.BarChart
import org.scalajs.dom.html.Div
import viz.doc.makePlotTarget

val child : Div = makePlotTarget(node, 50)
BarChart(List())(using child)
```

In fact, it's cross compiled from the same shared source code! That promise - explore in the REPL, publish in scala JS. I believe to be an attractive proposition.

I like a big chart... let's see if we can fill the div.
```scala
BarChart(List(viz.Utils.fillDiv))
```

```scala mdoc:js:invisible
import viz.vega.plots.BarChart
import org.scalajs.dom.html.Div
import viz.doc.makePlotTarget
val child : Div = makePlotTarget(node, 50)
BarChart(List(viz.Utils.fillDiv))(using child)
```

If you're curious about how that worked, then you're ready to go! Read on! 

See the [plot targets](../explanation/plotTargets.md) to understand what happened, and the [examples](../explanation/workflow.md) for suggestions on how to use and extend the concepts.
