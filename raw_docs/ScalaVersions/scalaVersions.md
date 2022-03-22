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
See [here](scalaJS.md)

# Scala Native
Not totally sure how it fits... I guess you'd use scala-js hosted from a webserver.