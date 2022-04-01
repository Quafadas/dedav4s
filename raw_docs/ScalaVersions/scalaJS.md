# Scala JS

```scala
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.html.Document
val node = dom.document.createElement("div").asInstanceOf[html.Div]
val root = dom.document.documentElement
root.appendChild(node)
node.setAttribute("style", s"width:50vmin;height:50vmin")
val chart = viz.vega.plots.GroupedBarChartLite(List(viz.Utils.fillDiv))(using node.asInstanceOf[html.Div])
```
Should display the chart. We've replaced the ```given``` plot target with a div! Where, surprise! Your chart will show up. If all has gone well.

What turns out to be really nice about scala JS support, is the seamless transition between exploration in a repl, e.g. sbt console, and  publication - it's the same code! As the core library is already javascript, it turns out to be fairly simple to use in Scala JS. There is a only a little more ceremony than with a repl - we need to respect decide the charts position in the document. i.e. find it a parent. 

Disclaimer: Scala JS support is currently a little experimental. I'd love feedback.

<mark>Gotcha : dedav ***does not include*** the underlying JS libraries out of it's box</mark>. 

The ~~burden~~ freedom is left to the user to include vega embed in your bundling solution. The simplest package.json depedancies would be;

```json
"dependencies": {
    "vega-embed" : "6.20.8"
}
```
A "complete" project may be found here; 
[Mill, Scala Js, Snowpack, Laminar, Dedav](https://github.com/Quafadas/scalajs-snowpack-example)

# Ecosystem support
We have two orthogonal problems

1. How to we obtain the javascript libraries? 
2. How to support the bouquet of scala JS UI frameworks? 

## Javascript libraries
The example dependency is set out above. It _should_ work with _any_ bundling solution, or by directly embedding the dependancies in the header of the html.

## Scala JS UI frameworks
It turns out, that scala JS Dom is simply a facade for the browser API. Dedav works, through providing a reference to a scala js dom Div element.. 

Due to how fundamental the statement above is, we implicitly support _all_ UI frameworks. It must be possible to coerce the DIV wrapper of your framework into a scala js dom Div.  

### Examples
#### MDoc
In the below example, the "node" variable comes from mdoc (automagically appended to the right place in the html). It is the dom element provided by mdoc which is displayed for this code fence. We give it a size, and an identifier. The node of interest passed (as a given) when instantiating the chart. 

Just to prove we can have more than one chart on a page... and that the vega examples work.

```scala
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
You'll need to have either gone through the bundling process on the mdoc docs, or include this in build.sbt to suck down the javascript dependancies into the html.

```scala
    mdocVariables ++= Map("js-html-header" -> """
        <script crossorigin type="text/javascript" src="https://cdn.jsdelivr.net/npm/vega@5"></script>
        <script crossorigin type="text/javascript" src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
        <script crossorigin type="text/javascript" src="https://cdn.jsdelivr.net/npm/vega-embed@6"></script>
    """
```
### Laika with mdoc
Which are a formiddable documentation team. You may need to also work with Laika docs it's [documentation](https://planet42.github.io/Laika/0.18/02-running-laika/01-sbt-plugin.html) to; 

1. disable auto linking (otherwise you'll end up with two sets of imports)
2. enable raw content

The github repo of this documentation is a successful example!

### Laminar
In laminar, it is simple to obtain the reference to a div.

```scala
import com.raquo.laminar.api.L._
import org.scalajs.dom

val laminarDiv: Div = div()
val domDiv: dom.html.Div = laminarDiv.ref
NestedBarChart(List(viz.Utils.fillDiv))(using domDiv.asInstanceOf[PlotTarget])
```