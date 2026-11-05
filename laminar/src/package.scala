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
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

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
    * The chart is embedded straight away. When the div is unmounted the vega view is finalized, which removes its event
    * listeners (including `window:resize`) and timers, and the returned signal goes back to `None`. If the same div is
    * mounted again, a fresh view is embedded and emitted on the signal, so attach listeners by observing the signal
    * rather than holding on to a single view. A div that is never mounted is never unmounted, so it is never cleaned
    * up.
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

    val embeddedIn: Div = inDivOpt.getOrElse(
      div(
        width := "100%",
        height := "100%"
      )
    )

    val viewVar = Var(Option.empty[VegaView])
    val view: Signal[Option[VegaView]] = viewVar.signal

    // The embed that currently owns the div. Kept as a future, not the result, so a chart that is unmounted before
    // vega-embed resolves is still finalized once it does resolve.
    var live: Option[Future[EmbedResult]] = None
    // Completes once the previous embed has been finalized. A new embed waits for it, so two vega-embed calls never
    // write into the div at the same time (unmounted and mounted again before the first embed resolved).
    var previousReleased: Future[Unit] = Future.unit

    def embedChart(): Unit =
      def start() = viz.vega.facades.embed(embeddedIn.ref, specObj, effectiveEmbedOpt).toFuture
      val embedded = if previousReleased.isCompleted then start() else previousReleased.flatMap(_ => start())
      live = Some(embedded)
      embedded.onComplete { result =>
        // If the chart was released while vega-embed was still working, don't publish a view that is already finalized.
        if live.exists(_ eq embedded) then viewVar.setTry(result.map(er => Some(er.view)))
      }
    end embedChart

    // EmbedResult.finalize runs view.finalize(), which removes vega's own listeners and timers (e.g. the window:resize
    // listener that width: "container" compiles to). It also removes vega-embed's document click listener for the
    // actions menu.
    def releaseChart(): Unit =
      live.foreach { embedded =>
        previousReleased = embedded.map(_.finalize()).recover { case _ => () }
      }
      live = None
      viewVar.set(None)
    end releaseChart

    // Embed straight away, as before, so the view is ready as soon as possible.
    embedChart()

    embeddedIn.amend(
      onMountUnmountCallback(
        // Mounting again after an unmount: the previous view was finalized, so embed a fresh one.
        mount = _ => if live.isEmpty then embedChart(),
        unmount = _ => releaseChart()
      )
    )

    if attemptAutoResize && inDivOpt.isEmpty then
      val resizeMonitor = new EventBus[ResizeObserverEntry]
      val resizeObserver = new ResizeObserver((entries: js.Array[ResizeObserverEntry], _) =>
        entries.foreach(entry => resizeMonitor.emit(entry))
      )
      embeddedIn.amend(
        onMountUnmountCallback(
          mount = _ => resizeObserver.observe(embeddedIn.ref),
          unmount = _ => resizeObserver.disconnect()
        ),
        resizeMonitor.events.debounce(100).toWeakSignal.combineWith(view) --> Observer {
          (valu: (Option[ResizeObserverEntry], Option[VegaView])) =>
            for
              entry <- valu._1
              v <- valu._2
            do
              // contentRect is the physical width and height. contentBoxSize is in writing-mode terms (inline / block).
              v.width(entry.contentRect.width.toInt)
              v.height(entry.contentRect.height.toInt)
              v.runAsync()
        }
      )
    end if

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
