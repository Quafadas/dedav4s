# Scala Versions
## Scala 3
Is the first class citizen.

## Scala 2
### 2.13.6+
_Should_ work through the forward compatibility mechanism. There's nothing fundamental about why it couldn't be cross compiled for native support, there is simply limited resource availalbe for this project!

So, there isn't really "support" for scala 2 per se, however... if you have scala 2.13.6+, then the library may be used via the forward compatibility mechanism.

Noting the below. 
```
scala.util.Properties.versionString
```
Will need to say 2.13.6 or higher. To import

```scala 
import $ivy.`io.github.quafadas:dedav4s_3:@VERSION@`
```
i.e. don't auto derive the artefact version, or use your built tools cross methods.

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

## 2.13.5 and prior
Is not supported

# Scala JS
What turns out to be really nice about this, is the seamless transition between exploration in a repl, e.g. sbt console, and subsequent publication if you already happen to be in a full stack environment. 

It turns out to be fairly simple to use in Scala JS. There is a little more ceremony than with a repl, because we need to respect the charts position in the document. i.e. find it a parent.

The "node" variable comes from mdoc. It is the dom element provided by mdoc which is displayed for this code fence. We modify it by providing a size, and giving it an identifier. In a "proper" application, you'd need to create a div element. Then create the chart, and feed it this Div. Then append that div element to your parent element on the webpage, where you want it. 

The node of interest passed (as a given) when instantiating the chart. The current supported framework is scala js dom. 

```scala mdoc:js
node.setAttribute("style", s"width:50vmin;height:50vmin")
val chart = viz.vega.plots.GroupedBarChartLite(List(viz.Utils.fillDiv))(using node.asInstanceOf[viz.PlotTarget])
```

Just to prove we can have more than one chart on a page... and that the vega examples work.

```scala mdoc:js
import viz.vega.plots.BarChart
node.setAttribute("style", s"width:50vmin;height:50vmin")
val chart = BarChart(List(viz.Utils.fillDiv))(using node.asInstanceOf[viz.PlotTarget])
```
Let's see if the extension methods work. 

```scala mdoc:js
import viz.extensions.*

node.setAttribute("style", s"width:50vmin;height:50vmin")
given n: viz.PlotTarget = node.asInstanceOf[viz.PlotTarget]
val chart = List(1,5,7,3,20).plotBarChart(List(viz.Utils.fillDiv))
```
# Scala Native
Not totally sure how it fits... I guess you'd use scala-js hosted from a webserver.