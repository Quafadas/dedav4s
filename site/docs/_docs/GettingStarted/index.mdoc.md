---
title: Getting Started
---

## Getting Started

Fire up scala-cli (needs scala > 3.7.0)

```shell
scala-cli --dependency io.github.quafadas::dedav4s:{{projectVersion}} --scala 3.7.0
```
Then,

```scala sc:nocompile
import viz.PlotTargets.desktopBrowser
import viz.vegaFlavour
import viz.Plottable.ppSpecUrl

viz.vega.plots.SpecUrl.BarChart.plot
```
And a browser window should have popped up, with a bar chart in. It should look very similar, to the chart plotted out of scala JS, below.

If you are using it in a project context;

Scala cli

```scala sc:nocompile
//> using dep io.github.quafadas::dedav4s:{{projectVersion}}
```

ammonite / mill
```scala sc:nocompile
import $ivy.`io.github.quafadas::dedav4s:{{projectVersion}}`
```
SBT
```scala sc:nocompile
libraryDependencies += "io.github.quafadas" %% "dedav4s" % "{{projectVersion}}"
```

Now, scala JS. The JS API is different given the different control flow.

```scala mdoc:js sc:nocompile
import viz.vegaFlavour
import viz.mod
val chart = viz.vega.plots.SpecUrl.BarChart.jsonSpec.mod(
  List(
    viz.Utils.fillDiv
  )
)
val chartSpec = upickle.default.write(chart)
viz.doc.JsDocs.showSpec( chartSpec , node, 50)
```
It gets mounted as a child of `node`, which is provided to us by the excellent mdocJS.

Excitingly, the chart we plotted out repl (JVM) and the chart displayed here, share the same definition! That promise - explore in the JVM and publish in scala JS is attractive for me. Further, with playwright we can **make charting unit testable**. We'll come back to that later.
<script>
    const sse = new EventSource("/refresh/v1/sse");
    sse.addEventListener("message", (e) => {
    const msg = JSON.parse(e.data);

    if ("KeepAlive" in msg) console.log("KeepAlive");

    if ("PageRefresh" in msg) location.reload();
    });
</script>