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
```

```scala mdoc:reset:invisible
import viz.PlotTargets.doNothing
import viz.extensions.*
```

```scala mdoc
List(1,4,6,7,4,4).plotBarChart()
```
```scala mdoc:vegaspec:simpleBarChart
List(1,4,6,7,4,4).plotBarChart()
```

A side effect should open a browser window, with this inside

```scala mdoc:js:invisible
viz.doc.showJsDocs("simpleBarChart", node, 0)
```

I like a big chart... so you may also try.... 

```scala mdoc
List(1,4,6,7,4,4).plotBarChart(List(viz.Utils.fillDiv))
```
```scala mdoc:vegaspec:simpleBarChartBig
List(1,4,6,7,4,4).plotBarChart(List(viz.Utils.fillDiv))
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("simpleBarChartBig", node, 50)
```

If that worked, then you're ready to go! See the [plot targets](../explanation/plotTargets.md) to understand what happened, and the [examples](../explanation/workflow.md) for suggestions on how to use and extend the concepts.
