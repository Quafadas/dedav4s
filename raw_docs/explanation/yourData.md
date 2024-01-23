# Your Data

My experience has been, that I don't usually know the structure of the data, that I'm going to plot much in advance of wanting to see a plot. In advance, I know know little more than it's probably a `Seq[A]`. Plotting is a visual problem, which all the typesafety in the world, won't help you with.

Instead, the aim is plot it - stat. Then evolve.

```scala mdoc:js
import viz.extensions.*
import viz.PlotTargets.doNothing
import com.github.tarao.record4s.%

// We don't know this in advance, but we have a Seq of it, and we want a bar chart.
case class MyData(badlyNamed:Double, namely:String, otherData: Option[(Int,Int)], extraneous: String)


val toPlot = Seq(
  MyData(1.0, "A", Some((1,2)), "foo"),
  MyData(2.0, "B", None, "bar"),
)

val plot = toPlot.plotBarChartR(x =>
    %(amount = x.badlyNamed, category = x.namely)
  )(List(viz.Utils.fillDiv))

viz.js.showChartJs(plot, node, 50)

```





