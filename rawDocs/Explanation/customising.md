---
id: customising
title: Custom Datatypes
---
The key idea behind the library allows what are, to my mind some exciting extensions. 

It would be very common, to have your own representations of data. One example would be [breeze](https://github.com/scalanlp/breeze), which is, as far as I can tell the de facto standard for linear algebra in scala. 

The below extension method would add line plotting functionality, so all Breeze DenseVectors. What's cool? _You don't need to own either Breeze or dedav to make this work_ 

```scala
extension [T: Numeric](l: DenseVector[T])(using plotTarget: PlotTarget)
  def plotLineChart(mods: JsonMod = List()): LineChart =
    l.toArray.plotLineChart(mods)
```

The moment where I figured it might be worth an attempt at at publishing this library was this; 
```scala mdoc
import breeze.stats.distributions._
import Rand.VariableSeed._
import viz.PlotTarget
import viz.vega.plots.ProbabilityDensity // see vega examples

extension (l: HasInverseCdf)(using plotTarget: PlotTarget)
    def plotDensity(mods: Seq[ujson.Value => Unit] = List()): ProbabilityDensity =
        val probs = breeze.linalg.linspace(0.0+1.0/1000.0,1.0-1.0/1000.0,1000)
        val icdfs = probs.map(l.inverseCdf).toScalaVector.zip(probs.toScalaVector)
        val inject = for((i, p) <- icdfs) yield {
            ujson.Obj("u" -> i, "v" -> p)
        }
        val pipeData :ujson.Value => Unit = spec => spec("data")(0) = ujson.Obj("name" -> "points", "values"->inject)
        ProbabilityDensity(List(pipeData, viz.Utils.fillDiv))
```
We extend the trait ```HasInverseCdf```, and now? _Every_ distribution with an inverse CDF is plottable! 

in 12. lines. of. code. It took way longer to write the documentation, than implement that entire class of plots :-). 

```scala mdoc:invisible
import viz.PlotTargets.doNothing
```

```scala mdoc
Gaussian(2,2).plotDensity()
LogNormal(1,0.5).plotDensity()
```

```scala mdoc:vegaplot
Gaussian(2,2).plotDensity()
```

```scala mdoc:vegaplot
LogNormal(1,0.5).plotDensity()
```