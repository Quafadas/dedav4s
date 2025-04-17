## Getting Started

Fire up scala-cli (needs scala > 3.7.0)

```shell
scala-cli --dependency io.github.quafadas::dedav4s:@VERSION@ --scala 3.7.0
```
Then,

```scala mdoc:compile-only
import viz.PlotTargets.desktopBrowser
import viz.vegaFlavour
import viz.Plottable.ppSpecUrl

viz.vega.plots.SpecUrl.BarChart.plot
```
And a browser window should have popped up, with a bar chart in. It should look very similar, to the chart plotted out of scala JS, below.

If you are using it in a project context;

Scala cli

```scala
//> using dep io.github.quafadas::dedav4s:@VERSION@
```

ammonite / mill
```scala
import $ivy.`io.github.quafadas::dedav4s:@VERSION@`
```
SBT
```scala
libraryDependencies += "io.github.quafadas" %% "dedav4s" % "@VERSION@"
```

Now, scala JS. The JS API is different given the different control flow requirements.

```scala mdoc:js
import viz.vegaFlavour
import viz.mod
val chart = viz.vega.plots.SpecUrl.BarChart.jsonSpec.mod(
  List(
    viz.Utils.fillDiv
  )
)
viz.js.showChartJs(chart, node)
```
It gets mounted as a child of `node`, which is provided to us by the excellent mdocJS.

Excitingly, the chart we plotted out repl (JVM) and the chart displayed here, share the same definition! That promise - explore in the JVM and publish in scala JS is attractive for me. Further, with playwright we can **make charting unit testable**. We'll come back to that later.