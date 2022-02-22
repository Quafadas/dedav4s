# Customisations

The key idea behind the library allows what are, to my mind some exciting extensions. 

It would be very common, to have your own representations of data. An example of a library with it's own data structures (there are many) might [breeze](https://github.com/scalanlp/breeze), which is, as far as I can tell the de facto standard for linear algebra in scala. 

The below extension method would add line plotting functionality, to _all_ Breeze DenseVectors. What's cool? _You don't need to own either Breeze or dedav to make this work_. 

```scala
extension [T: Numeric](l: DenseVector[T])(using plotTarget: PlotTarget)
  def plotLineChart(mods: JsonMod = List()): LineChart =
    l.toArray.plotLineChart(mods)
```

The moment where I figured it might be worth an attempt at at publishing this library was this; 
```scala
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


```scala
Gaussian(2,2).plotDensity()
// res0: ProbabilityDensity = ProbabilityDensity(
//   mods = List(
//     repl.MdocSession$App$$Lambda$10814/0x00000008027483d0@14e13737,
//     viz.Utils$$$Lambda$10815/0x000000080274c2f8@4240ce1e
//   )
// )
LogNormal(1,0.5).plotDensity()
// res1: ProbabilityDensity = ProbabilityDensity(
//   mods = List(
//     repl.MdocSession$App$$Lambda$10814/0x00000008027483d0@43f053d,
//     viz.Utils$$$Lambda$10815/0x000000080274c2f8@4240ce1e
//   )
// )
```

