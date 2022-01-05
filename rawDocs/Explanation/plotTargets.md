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

Every time an object is created which extends the "Spec" trait, it executes the ```newObject.show()``` side effect. That side effect requires context, provided through a [given](https://dotty.epfl.ch/docs/reference/contextual/givens.html) which is in scope. 

Those "given" targets are listed below, all accessible at ```viz.PlotTargets.xxxxx```

# Scala 3
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
List(("A",5),("B",8),("C",-1)).plotBarChart(List())
```
```scala mdoc:vegaplot
List(("A",5),("B",8),("C",-1)).plotBarChart(List())
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

## [Almond](https://www.almond.sh)

WIP - need a release of almond which supports scala 3

## VSCode 

WIP - will work via notebooks... i.e. almond, once the above is ready.

## Do Nothing
```scala mdoc
import viz.PlotTargets.doNothing
import viz.vega.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart(List())
```
To no ones surprise, does nothing! The implementation simply executes unit ```()```. I regret the CPU cycles. 

## printlnTarget

Formats and prints the final JSON spec to the console. 

```scala mdoc:reset
import viz.PlotTargets.printlnTarget
import viz.vega.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart(List())
```

# Scala 2
There isn't really "support" for scala 2 per se, however... if you have scala 2.13.7, then the library may be used via the forward compatibility mechanism, so for almond / ammonite, or the equivalent SBT statment.

Everything will work as above, noting the below. 

```
scala.util.Properties.versionString
```
Will need to say 2.13.7 or higher. To import

```scala 
import $ivy.`io.github.quafadas:dedav4s_3:0.0.9`
```
You'll need the tasty reader scalac flag set true. 

```scala
interp.configureCompiler(_.settings.YtastyReader.value = true)
```

Finally, if you wish to use this in an almond or vscode notebook environment with scala 2, you'll need to interact with the kernel directly. 

```scala
import viz.PlotTarget.doNothing

val chart = BarChart()

kernel.publish.display(
  almond.interpreter.api.DisplayData(
    data = Map(      
      "application/vnd.vega.v5+json" -> chart.spec
    )
  )  
)
```


