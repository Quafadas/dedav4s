---
id: home
title: Home
---
# Dedav4s

Declarative data visualization for scala - a scala plotting concept. 

It is written exclusively in scala 3 (currently), but will work with scala 2.13.7+ via forward compatibility.

<head>
        <meta charset="utf-8" />
        <!-- Import Vega & Vega-Lite -->
        <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
        <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
        <!-- Import vega-embed -->
        <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
        <style>
		    div.viz {
                width: 25vmin;
                height:25vmin;
                style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"
            }
        </style>
</head>

To add this library to an sbt project
```scala
libraryDependencies += "io.github.quafadas" %% "dedav4s" % "@VERSION@"
```
To use this library in ammonite
```scala
import $ivy.`io.github.quafadas::dedav4s:@VERSION@`
```

Fire up an sbt console (or in a repl... )

```scala mdoc
import viz.PlotTargets.desktopBrowser
import viz.vega.extensions.*
```

```scala mdoc:reset:invisible
import viz.PlotTargets.doNothing
import viz.vega.extensions.*
```

```scala mdoc
(1 to 10).plotBarChart()
```

A side effect should open a browser window, with this inside

```scala mdoc:vegaplot
val secondChart = (1 to 10).plotBarChart()
```
If that worked, then you're ready to go! See the [plot targets](explanation/plotTargets.md) to understand what happened, and the [examples](explanation/examples.md) for suggestions on how to use and extend the concepts.

# Background Information
This is a thin shim around [vega](https://vega.github.io/vega/) and [vega lite](https://vega.github.io/vega-lite/). It is aimed at repl, interactive environments and exploratory analysis

It pays to have an understanding (or at least some idea of what those are), both Vega & Vega-Lite. It may be worth taking a few minutes to orient yourself with this [talk/demo](https://www.youtube.com/watch?v=9uaHRWj04D4) from the creators at the Interactive Data Lab (IDL) at University of Washington.

If you are interested in plotting in general, I think that you will not regret learning a declarative paradigm.