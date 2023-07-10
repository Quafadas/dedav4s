# Scala JS

The charts in these documents, are display using scala JS :-).

What turns out to be really nice about scala JS support, is the seamless transition between exploration in a repl  on the JVM, luxuriating in it's rapid feedback and typsafe tooling, and subsequent publication into a browser with scala JS. It's the same code! There is a only a little more ceremony than with a repl - we need to decide the charts position in the document. i.e. find it a parent.

<mark>Gotcha : dedav ***does not include*** the underlying JS libraries out of it's box</mark>.

I may list out some toy examples on the github readme. Here's one...
[Mill, Scala Js, Snowpack, Laminar, Dedav](https://github.com/Quafadas/scalajs-snowpack-example)

# Ecosystem support
We have two orthogonal problems

1. How to we obtain the javascript libraries?
2. How to support the bouquet of scala JS UI frameworks?

## Javascript libraries
The example dependency is set out above. It _should_ work with _any_ bundling solution, or even by directly embedding the dependancies in the header of the html. Your choice.

The ~~burden~~ freedom is left to you to get vega itself in scope. The simplest package.json depedancies would be;

```json
"dependencies": {
    "vega-embed" : "6.20.8"
}
```

You could also consider going sans-bundler via ESM modules or directly via a script tag in the header of your html - have a look at the source of this page for such an example.

## Scala JS UI frameworks
It turns out, that scala JS Dom is simply a facade for the browser API. Dedav works, through providing a reference to a scala js dom Div element.

Due to how fundamental the statement above is, we implicitly support _all_ JS UI frameworks. It must be possible to coerce the DIV wrapper of your framework into a scala js dom Div.

## Integrations
In flight!

### Laminar
In laminar, it is simple to obtain the reference to a div.

```scala mdoc:js
// import com.raquo.laminar.api.L._
// import org.scalajs.dom
// import viz.extensions.*
// import viz.Utils
// import viz.vega.plots.{BarChart, given}
// import livechart.simpleEmbed

// println(node.id)

// object App {
//   def main(args: Array[String]): Unit = {
//     lazy val appContainer = dom.document.querySelector(s".div")
//     val appElement = div(h1("Hello world"))
//     renderOnDomContentLoaded(appContainer, appElement)
//   }
// }

//println(node)

// object chartExample:
//   val data = Var(List(2.4, 3.4, 5.1, -2.3))

//   def apply(): Div =
//     div(
//       p("We want to make it as easy as possible, to build a chart"),
//       span("Here's a random data set: "),
//       child.text <-- data.signal.map { data =>
//         data.mkString("[", ",", "]")
//       },
//       button(
//         "Add a random number",
//         onClick --> { _ =>
//           data.update { data =>
//             data :+ scala.util.Random.nextDouble() * 5
//           }
//         }
//       ),
//       p(),
//       child <-- data.signal.map { data =>
//         val barChart: BarChart = data.plotBarChart(List(viz.Utils.fillDiv))
//         simpleEmbed(barChart)
//       }
//     )
//   end apply
// end chartExample

```
### Calico


### MDoc
Is how this documentation works. Setup mdoc with scalajs bundler, and include vega in the bundle. Read the source of this library :-).

### Laika with mdoc
Which are a formiddable documentation team. You may need to also work with Laika docs it's [documentation](https://planet42.github.io/Laika/0.18/02-running-laika/01-sbt-plugin.html) to;

1. disable auto linking (otherwise you'll end up with two sets of imports)
2. enable raw content

The github repo of this documentation is a successful example!
