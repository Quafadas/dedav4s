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
I'm unclear if this would make sense. Many of the libraries used don't cross compile to JS - they could be replaced, but I'm unclear if there is any value in doing so. If you're in JS land, you already have access to vega... would you really want this?

```scala mdoc:js
<p>I am a custom <code>loader</code></p>
---
println(node.innerHTML)
```

```scala mdoc:js
import viz.PlotTargets.div
import org.scalajs.dom
import scala.util.Random
import org.scalajs.dom.Element
import org.scalajs.dom.html

val child = dom.document.createElement("div")
val anId = "vega" + Random.alphanumeric.take(8).mkString("")
child.id = anId
node.appendChild(child)
child.setAttribute("style", s"width:50vmin;height:50vmin")
val chart = viz.vega.plots.GroupedBarChartLite(List(viz.Utils.fillDiv))
chart.show(child.asInstanceOf[html.Div])
```

# Scala Native
Not totally sure how it fits... I guess you'd use scala-js hosted from a webserver.