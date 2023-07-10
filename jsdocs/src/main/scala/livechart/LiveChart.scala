// package livechart

// import scala.scalajs.js
// import scala.scalajs.js.annotation.*
// import com.raquo.laminar.api.L.*

// import org.scalajs.dom
// import scalajs.js.JSON

// import viz.PlotTargets.doNothing
// import viz.extensions.*
// import viz.vega.facades.EmbedOptions
// import viz.Spec

// import viz.vega.plots.BarChart

// @main
// def LiveChart(): Unit =
//   renderOnDomContentLoaded(
//     dom.document.getElementById("app"),
//     chartExample()
//   )
// end LiveChart

// /**
//   * Embed a chart in a div. This method is a good choice if you are not at all worried about performance (mostly you won't be),
//   * you want to get started quickly, and you want things to "just work".
//   *
//   * There is one "gotcha". If _you_ choose to provide your own div, it _must_ have a well defined width and height.
//   *
//   * If it does not the chart will not initialize properly, and it will not recover from this failure.
//   *
//   * @param chart - the chart you wish to plot
//   * @param inDivOpt - optionally, the div you wish to plot into. If you don't provide one, one will be created for you.
//   * @param embedOpt - optionally, the embed options you wish to use
//   */
// def simpleEmbed(chart: Spec, inDivOpt: Option[Div] = None, embedOpt: Option[EmbedOptions] = None): Div =

//   val specObj = JSON.parse(chart.spec).asInstanceOf[js.Object]
//   (inDivOpt, embedOpt) match
//     case (Some(thisDiv), Some(opts)) =>
//       viz.vega.facades.VegaEmbed(thisDiv.ref, specObj, opts)
//       thisDiv
//     case (Some(thisDiv), None) =>
//       val specObj = JSON.parse(chart.spec).asInstanceOf[js.Object]
//       viz.vega.facades.VegaEmbed(thisDiv.ref, specObj, EmbedOptions)
//       thisDiv
//     case (None, Some(opts)) =>
//       val newDiv = div(
//         width := "50vmin",
//         height := "50vmin"
//       )
//       viz.vega.facades.VegaEmbed(newDiv.ref, specObj, opts)
//       newDiv
//     case (None, None) =>
//       val newDiv = div(
//         width := "50vmin",
//         height := "50vmin"
//       )
//       viz.vega.facades.VegaEmbed(newDiv.ref, specObj, EmbedOptions)
//       newDiv
//   end match

// end simpleEmbed

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
