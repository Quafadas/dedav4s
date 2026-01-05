package viz

import scala.scalajs.js

import scalajs.js.JSON
import cats.effect.*
import fs2.dom.*

import viz.vega.facades.EmbedOptions
import viz.vega.facades.VegaView
import viz.vega.facades.EmbedResult

object CalicoViz:

  // def dataClickBus: (EventStream[js.UndefOr[js.Dynamic]], (x: js.Dynamic, y: js.Dynamic) => Unit) =
  //   val (dataClickedBus, callback) = EventStream.withJsCallback[js.UndefOr[js.Dynamic]]
  //   val fromFct = (x: js.Dynamic, y: js.Dynamic) => callback(dataClickHandler.apply(x, y))
  //   (dataClickedBus, fromFct)
  // end dataClickBus

  // def signalBus: (EventStream[js.Dynamic], (x: String, y: js.Dynamic) => Unit) =
  //   val (hoverBus, callback) = EventStream.withJsCallback[js.Dynamic]
  //   val handler: js.Function2[String, js.Dynamic, js.Dynamic] = (str: String, dyn: js.Dynamic) => dyn
  //   val fromFct = (x: String, y: js.Dynamic) => callback(handler.apply(x, y))
  //   (hoverBus, fromFct)
  // end signalBus

  /** Embed a chart in a div. This method is a good choice if you are not at all worried about performance (mostly you
    * won't be), you want to get started quickly, and you want things to "just work".
    *
    * There is one "gotcha". If _you_ choose to provide your own div, it _must_ have a well defined width and height.
    *
    * If it does not the chart will not initialize properly, and it will not recover from this failure.
    *
    * @param chart
    *   \- the chart you wish to plot
    * @param inDivOpt
    *   \- optionally, the div you wish to plot into. If you don't provide one, one will be created for you.
    * @param embedOpt
    *   \- optionally, the embed options you wish to use
    */
  def viewEmbed(
      chart: String,
      inDivOpt: Option[Resource[IO, HtmlDivElement[IO]]] = None,
      embedOpt: Option[EmbedOptions] = None
  ): Resource[IO, (HtmlDivElement[IO], IO[VegaView])] =

    val specObj = JSON.parse(chart).asInstanceOf[js.Object]
    val tmp = (inDivOpt, embedOpt) match
      case (Some(thisDiv), Some(opts)) =>
        thisDiv.map { (d: HtmlDivElement[IO]) =>
          val dCheat = d.asInstanceOf[org.scalajs.dom.html.Div]
          dCheat.style.height = "40vmin"
          dCheat.style.width = "40vmin"
          val p: js.Promise[EmbedResult] =
            viz.vega.facades.embed(d.asInstanceOf[org.scalajs.dom.html.Div], specObj, opts)
          val pIop = IO.fromPromise(IO(p))
          (d, pIop.map(_.view))
        }
      // case (Some(thisDiv), None) => ???
      // This case doesn't work
      // thisDiv.flatMap { (d: HtmlDivElement[IO]) =>
      //   val dCheat = d.asInstanceOf[org.scalajs.dom.html.Div]
      //   dCheat.style.height = "40vmin"
      //   dCheat.style.width = "40vmin"
      //   val p: js.Promise[EmbedResult] = viz.vega.facades.VegaEmbed(d.asInstanceOf[org.scalajs.dom.html.Div], specObj, opts)
      //   val pIop = IO.fromPromise[EmbedResult](IO(p)).toResource
      //   pIop.map(_.view).map((d, _))
      // }
      case _ => ???
      // case (Some(thisDiv), None) =>
      //   val specObj = JSON.parse(chart.spec).asInstanceOf[js.Object]
      //   val p: js.Promise[EmbedResult] = viz.vega.facades.VegaEmbed(thisDiv.ref, specObj, EmbedOptions)
      //   (thisDiv, p)
      // case (None, Some(opts)) =>
      //   val newDiv = div(
      //     width := "40vmin",
      //     height := "40vmin"
      //   )
      //   val p: js.Promise[EmbedResult] = viz.vega.facades.VegaEmbed(newDiv.ref, specObj, opts)
      //   (newDiv, p)
      // case (None, None) =>
      //   val newDiv = div(
      //     width := "40vmin",
      //     height := "40vmin"
      //   )
      //   val p: js.Promise[EmbedResult] = viz.vega.facades.VegaEmbed(newDiv.ref, specObj, EmbedOptions)
      //   (newDiv, p)
    tmp

    // val viewSignal: IO[Option[VegaView]] = IO.fromPromise(embedResult).map(in => in.map(_.view))
    // // val viewSignal: Signal[Option[VegaView]] = Signal.fromValue(None)

    // (embeddedIn, viewSignal)
  end viewEmbed
  // end viewEmbed

  /** Embed a chart in a div. This method is a good choice if you are not at all worried about performance (mostly you
    * won't be), you want to get started quickly, and you want things to "just work".
    *
    * There is one "gotcha". If _you_ choose to provide your own div, it _must_ have a well defined width and height.
    *
    * If it does not the chart will not initialize properly, and it will not recover from this failure.
    *
    * @param chart
    *   \- the chart you wish to plot
    * @param inDivOpt
    *   \- optionally, the div you wish to plot into. If you don't provide one, one will be created for you.
    * @param embedOpt
    *   \- optionally, the embed options you wish to use
    */
  // def simpleEmbed(chart: Spec, inDivOpt: Option[Div] = None, embedOpt: Option[EmbedOptions] = None): Div =
  //   viewEmbed(chart, inDivOpt, embedOpt)._1

  // end simpleEmbed

end CalicoViz
