# Dedav4s
A scala plotting concept
<head>
        <meta charset="utf-8" />
        <!-- Import Vega & Vega-Lite -->
        <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
        <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
        <!-- Import vega-embed -->
        <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
        <style>
            div#viz {
            width: 25vmin;
            height: 25vmin;
            style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"
        }
    </style>
</head>

To install this library
```scala
libraryDependencies += "io.github.quafadas" % "dedav4s" % "@VERSION@"
```
To use this library in ammonite
```scala
libraryDependencies += "io.github.quafadas" % "dedav4s" % "@VERSION@"
```
```scala mdoc
val x = (1 to 10)
```
<div id="viz">    
</div>

```scala mdoc:vegaplot
import viz.PlotTargets.printlnTarget
import viz.vega.extensions.*

val firstChart = (1 to 10).plotBarChart()
```