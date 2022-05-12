# Customising an existing data structure

The key idea behind the library allows what are, to my mind some exciting extensions. 
```scala mdoc:invisible
import viz.PlotTargets.doNothing
```

It would be very common, to have your own representations of data. An example of a library with it's own data structures (there are many) might [breeze](https://github.com/scalanlp/breeze), which is, as far as I can tell the de facto standard for linear algebra in scala. 

The below extension method would add line plotting functionality, to _all_ Breeze DenseVectors. What's cool? _You don't need to own either Breeze or dedav to make this work_. 

```scala
extension [T: Numeric](l: DenseVector[T])(using plotTarget: PlotTarget)
  def plotLineChart(mods: JsonMod = List()): LineChart =
    l.toArray.plotLineChart(mods)
```
Give it a go in a project - this turns a breeze vector into a plottable data structure that emits a line chart!

The moment where I figured it might be worth an attempt at at publishing this library was this; 
```scala mdoc
import breeze.stats.distributions._
import Rand.VariableSeed._
import viz.PlotTarget
import viz.vega.plots.ProbabilityDensity

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


```scala mdoc
Gaussian(2,2).plotDensity(Seq(viz.Utils.fillDiv))
LogNormal(1,0.5).plotDensity(Seq(viz.Utils.fillDiv))
```

```scala mdoc:vegaspec:plotDensityGaussian
Gaussian(2,2).plotDensity(Seq(viz.Utils.fillDiv))
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("plotDensityGaussian", node)
```

```scala mdoc:vegaspec:plotDensityLogNormal
LogNormal(1,0.5).plotDensity(Seq(viz.Utils.fillDiv))
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("plotDensityLogNormal", node )
```