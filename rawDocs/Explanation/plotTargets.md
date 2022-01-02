---
id: plotTargets
title: Plot Targets
---
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
# Targets
You need to decide where you want to see the chart. For this library, the first class citizen is a browser... 

Every time an object is created which extends the "Spec" trait, it executes the ```newObject.show()``` side effect. That side effect requires context, provided through a 

## Desktop Browser

Will open a new browser window in your desktop based browser, pointing to a temporary file. 

```scala mdoc:invisible
import viz.PlotTargets.doNothing
import viz.vega.extensions.*
```

```scala 
import viz.PlotTargets.desktopBrowser
import viz.vega.extensions.*
```

```scala
List(("A",5),("B",8),("C",-1)).plotBarChart()
```
```scala mdoc:vegaplot
List(("A",5),("B",8),("C",-1)).plotBarChart()
```
The library writes a (temporary) file, assuming that

    java.io.File.createTempFile() 

Is available. That temporary file assumes that you have an internet connection, and can resolve 

    <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>

Finally, we assume the existence of a 

    java.awt.Desktop

Which has a browser available... 

    java.awt.Desktop.browse()

And we browse to the temporary file in step one.

## Websockets / Post
The post side effect is cool used with a [companion project](https://github.com/Quafadas/viz-websockets).

WIP to integrate in a friendly way into this library.

## [Almond](www.almond.sh)

WIP - need a release of almond which supports scala 3

## VSCode 

WIP

## Do Nothing
```scala mdoc:vegaplot
import viz.PlotTargets.doNothing
import viz.vega.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart()
```
To no ones surprise, does nothing! The implementation simply executes unit ```()```. I regret the CPU cycles. 

## printlnTarget
```scala mdoc:reset
import viz.PlotTargets.printlnTarget
import viz.vega.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart()
```