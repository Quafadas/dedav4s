package viz

import scala.scalajs.js
import com.raquo.laminar.api.L.*
import scalajs.js.JSON

import viz.vega.facades.EmbedOptions
import viz.vega.facades.VegaView
import viz.vega.facades.EmbedResult
import viz.vega.facades.Theme

import viz.vega.facades.Helpers.*
import org.scalajs.dom.ResizeObserverEntry
import org.scalajs.dom.ResizeObserver
import org.scalajs.dom.window
import org.scalajs.dom.document

object LaminarViz:

  /** Detects if the browser is in dark mode by checking multiple sources:
    *   1. CSS media query (prefers-color-scheme: dark)
    *   2. VS Code's body attribute (data-vscode-theme-kind)
    *   3. VS Code dark class on body (vscode-dark)
    *
    * @return
    *   true if dark mode is detected, false otherwise
    */
  def isDarkMode: Boolean =
    // 1. Check CSS media query (works in most browsers)
    val mediaQueryDark = window.matchMedia("(prefers-color-scheme: dark)").matches

    // 2. Check VS Code's body attribute (notebook specific)
    val vscodeThemeAttr = Option(document.body.getAttribute("data-vscode-theme-kind"))
      .exists(_.contains("dark"))

    // 3. Check for dark class on body
    val vscodeDarkClass = document.body.classList.contains("vscode-dark")

    mediaQueryDark || vscodeThemeAttr || vscodeDarkClass
  end isDarkMode

  /** Returns the appropriate vega-embed theme based on dark mode detection.
    *
    * @return
    *   "dark" if dark mode is detected, "default" otherwise
    */
  def detectTheme: Theme =
    if isDarkMode then "dark" else "default"

  /** This function signature is intended to work with the vega view "addEventListener" function
    *
    * @return
    */
  def dataClickBus: (EventStream[js.UndefOr[js.Dynamic]], (x: js.Dynamic, y: js.Dynamic) => Unit) =
    val (dataClickedBus, callback) = EventStream.withJsCallback[js.UndefOr[js.Dynamic]]
    val fromFct = (x: js.Dynamic, y: js.Dynamic) => callback(dataClickHandler.apply(x, y))
    (dataClickedBus, fromFct)
  end dataClickBus

  /** This function signature is intended to work with the vega view "addSignalListener" function in the vega.View
    * facade.
    *
    * This example is for a vega spec.
    *
    * ```scala
    *
    * val (aSignalBus, signalCallback: ((x: String, y: scala.scalajs.js.Dynamic) => Unit)) = LaminarViz.signalBus
    *
    * ...
    *
    * div( child <-- aSignalBus.map { x => JSON.stringify(x).toString } )
    * ...
    *
    * (v: Signal[Option[VegaView]]).map((vvOpt: Option[VegaView]) =>
    *        vvOpt.map((vv: VegaView) => vv.safeAddSignalListener("tooltip", signalCallback))
    *      ) --> Observer(_ => ())
    *
    * ```
    *
    * @return
    */
  def signalBus: (EventStream[js.Dynamic], (x: String, y: js.Dynamic) => Unit) =
    val (hoverBus, callback) = EventStream.withJsCallback[js.Dynamic]
    val handler: js.Function2[String, js.Dynamic, js.Dynamic] = (_, dyn: js.Dynamic) => dyn
    val fromFct = (x: String, y: js.Dynamic) => callback(handler.apply(x, y))
    (hoverBus, fromFct)
  end signalBus

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
      chartSpec: String,
      inDivOpt: Option[Div] = None,
      embedOpt: Option[EmbedOptions] = None,
      attemptAutoResize: Boolean = false
  ): (Div, Signal[Option[VegaView]]) =

    val specObj = JSON.parse(chartSpec).asInstanceOf[js.Object]

    // Apply theme detection if not already specified in embedOpt
    val effectiveEmbedOpt = embedOpt match
      case Some(opts) if opts.theme.isDefined => opts
      case Some(opts)                         =>
        opts.theme = detectTheme
        opts
      case None =>
        EmbedOptions(theme = detectTheme)

    val (embeddedIn, embedResult) = inDivOpt match
      case Some(thisDiv) =>
        val p: js.Promise[EmbedResult] = viz.vega.facades.embed(thisDiv.ref, specObj, effectiveEmbedOpt)
        (thisDiv, p)
      case None =>
        val newDiv = div(
          width := "100%",
          height := "100%"
        )
        val p: js.Promise[EmbedResult] = viz.vega.facades.embed(newDiv.ref, specObj, effectiveEmbedOpt)
        (newDiv, p)

    val view: Signal[Option[VegaView]] = Signal.fromJsPromise(embedResult).map(er => er.map(_.view))

    if attemptAutoResize then
      val resizeMontitor = new EventBus[ResizeObserverEntry]
      val resizer = inDivOpt match
        case None =>
          val resizeObserver = new ResizeObserver((entries: scala.scalajs.js.Array[ResizeObserverEntry], _) =>
            entries.foreach(entry => resizeMontitor.emit(entry))
          )
          resizeObserver.observe(embeddedIn.ref)
          Some(resizeObserver)

        case Some(embeddedIn) => None
      embedResult.`then`(in =>
        embeddedIn.amend(
          resizeMontitor.events.debounce(100).combineWith(view.changes) --> Observer {
            (valu: (ResizeObserverEntry, Option[VegaView])) =>
              valu._2.foreach { view =>
                view.width(valu._1.contentBoxSize.head.blockSize.toInt)
                view.height(valu._1.contentBoxSize.head.blockSize.toInt)
                view.runAsync()
              }
          },
          onUnmountCallback { _ =>
            resizer.foreach(_.disconnect())
          }
        )
      )
    end if
    embedResult.`then`(in =>
      onUnmountCallback { _ =>
        in.view.finalize()
      }
    )
    embeddedIn.amend()
    (embeddedIn, view)
  end viewEmbed

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
  def simpleEmbed(chart: String, inDivOpt: Option[Div] = None, embedOpt: Option[EmbedOptions] = None): Div =
    viewEmbed(chart, inDivOpt, embedOpt)._1

  end simpleEmbed

end LaminarViz
