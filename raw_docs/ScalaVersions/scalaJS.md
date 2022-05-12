# Scala JS

The charts in these documents, are display using scala JS :-).

What turns out to be really nice about scala JS support, is the seamless transition between exploration in a repl  on the JVM, luxuriating in it's rapid feedback and typsafe tooling, and subsequent publication into a browser with scala JS. It's the same code! There is a only a little more ceremony than with a repl - we need to decide the charts position in the document. i.e. find it a parent. 

<mark>Gotcha : dedav ***does not include*** the underlying JS libraries out of it's box</mark>. 

The ~~burden~~ freedom is left to the user to include vega embed in your bundling solution. The simplest package.json depedancies would be;

```json
"dependencies": {
    "vega-embed" : "6.20.8"
}
```
I may list out some toy examples on the github readme. Here's one... 
[Mill, Scala Js, Snowpack, Laminar, Dedav](https://github.com/Quafadas/scalajs-snowpack-example)

# Ecosystem support
We have two orthogonal problems

1. How to we obtain the javascript libraries? 
2. How to support the bouquet of scala JS UI frameworks? 

## Javascript libraries
The example dependency is set out above. It _should_ work with _any_ bundling solution, or even by directly embedding the dependancies in the header of the html. Your choice.

## Scala JS UI frameworks
It turns out, that scala JS Dom is simply a facade for the browser API. Dedav works, through providing a reference to a scala js dom Div element.

Due to how fundamental the statement above is, we implicitly support _all_ UI frameworks. It must be possible to coerce the DIV wrapper of your framework into a scala js dom Div.  

### Examples

#### Laminar
In laminar, it is simple to obtain the reference to a div.

```scala
import com.raquo.laminar.api.L._
import org.scalajs.dom
import viz.Utils
import viz.vega.plots.BarChart
import viz.PlotTarget

  val newDiv = div(
    height :="40vmin", width := "100vmin", idAttr := "viz",
    onMountCallback{ nodeCtx =>      
      BarChart(List(viz.Utils.fillDiv))(using nodeCtx.thisNode.ref)      
    }
  )  

```
#### MDoc
Is how this documentation works. Setup mdoc with scalajs bundler, and include vega in the bundle. Read the source of this library :-). 

### Laika with mdoc
Which are a formiddable documentation team. You may need to also work with Laika docs it's [documentation](https://planet42.github.io/Laika/0.18/02-running-laika/01-sbt-plugin.html) to; 

1. disable auto linking (otherwise you'll end up with two sets of imports)
2. enable raw content

The github repo of this documentation is a successful example!
