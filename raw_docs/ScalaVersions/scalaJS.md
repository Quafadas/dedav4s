# Scala JS

```scala mdoc:js
node.setAttribute("style", s"width:50vmin;height:50vmin")
val chart = viz.vega.plots.GroupedBarChartLite(List(viz.Utils.fillDiv))(using node.asInstanceOf[viz.PlotTarget])
```

What turns out to be really nice about scala JS support, is the seamless transition between exploration in a repl, e.g. sbt console, and  publication - it's the same code! As the core library is already javascript, it turns out to be fairly simple to use in Scala JS. There is a little more ceremony than with a repl, because we need to respect the charts position in the document. i.e. find it a parent. 

Disclaimer: Scala JS support is currently a little experimental. I'd love feedback.

=== Gotcha : dedav _does not include_ the underlying JS libraries out of it's box. You will need to include them yourself===

We have two orthogonal problems - and I'm not 100% sure about the solution to either!
1. How to we obtain the javascript libraries? 
2. How to support the bouquet of scala JS UI frameworks? 

In the below example, the "node" variable comes from mdoc. It is the dom element provided by mdoc which is displayed for this code fence. We modify it by providing a size, and giving it an identifier. In a "proper" application, you'd need to create a div element. Then create the chart, and feed it this Div. Then append that div element to your parent element on the webpage, where you want it. 

The node of interest passed (as a given) when instantiating the chart. The current supported framework is scala js dom. 

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

## Framework Support
### mDoc
You can see in the examples above how it may be used in mdoc - really easy! Follow the mdoc instructions for scala js. Now add this to your docs project sbt settings; 

```scala
    mdocVariables ++= Map("js-html-header" -> """
        <script crossorigin type="text/javascript" src="https://cdn.jsdelivr.net/npm/vega@5"></script>
        <script crossorigin type="text/javascript" src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
        <script crossorigin type="text/javascript" src="https://cdn.jsdelivr.net/npm/vega-embed@6"></script>
    """
```
Which will include the vega libraries as a header.

### Laika with mdoc
Which are a formiddable documentation team. You may need to also work with Laika docs it's [documentation](https://planet42.github.io/Laika/0.18/02-running-laika/01-sbt-plugin.html) to; 

1. disable auto linking (otherwise you'll end up with two sets of imports)
2. enable raw content

The github repo of this documentation is a successful example!

### Scala JS dom
This is what the library was intended for. Create a div element, append it to the page, then provide the div as a plot target. From first principles; 
```scala mdoc:js
import org.scalajs.dom
import scala.util.Random
import viz.Utils
import viz.vega.plots.NestedBarChart
import viz.PlotTarget
val plotDiv = dom.document.createElement("div").asInstanceOf[dom.html.Div]
val anId = "vega" + Random.alphanumeric.take(8).mkString("")
plotDiv.id = anId
plotDiv.setAttribute("style", s"width:50vmin;height:300px")
node.appendChild(plotDiv)
NestedBarChart(List(Utils.fillDiv))(using plotDiv)
```


### Laminar
In laminar, it is simple to obtain the reference to a div. Then simply proceed as above.

```scala
import com.raquo.laminar.api.L._
import org.scalajs.dom

val laminarDiv: Div = div()
val domDiv: dom.html.Div = laminarDiv.ref
NestedBarChart(List(viz.Utils.fillDiv))(using domDiv.asInstanceOf[PlotTarget])
```