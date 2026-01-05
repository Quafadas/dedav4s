/*
 * Copyright 2023 quafadas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viz

import scala.scalajs.js
import com.raquo.laminar.api.L.*
import scalajs.js.JSON

import viz.vega.facades.EmbedOptions
import viz.vega.facades.VegaView
import viz.vega.facades.EmbedResult

import viz.vega.facades.Helpers.*
import org.scalajs.dom.ResizeObserverEntry
import org.scalajs.dom.ResizeObserver

object LaminarViz:

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

    val (embeddedIn, embedResult) = (inDivOpt, embedOpt) match
      case (Some(thisDiv), Some(opts)) =>
        val p: js.Promise[EmbedResult] = viz.vega.facades.embed(thisDiv.ref, specObj, opts)
        (thisDiv, p)
      case (Some(thisDiv), None) =>
        val specObj = JSON.parse(chartSpec).asInstanceOf[js.Object]
        val p: js.Promise[EmbedResult] = viz.vega.facades.embed(thisDiv.ref, specObj, EmbedOptions())
        (thisDiv, p)
      case (None, Some(opts)) =>
        val newDiv = div(
          width := "100%",
          height := "100%"
        )
        val p: js.Promise[EmbedResult] = viz.vega.facades.embed(newDiv.ref, specObj, opts)
        (newDiv, p)
      case (None, None) =>
        val newDiv = div(
          width := "100%",
          height := "100%"
        )
        val p: js.Promise[EmbedResult] = viz.vega.facades.embed(newDiv.ref, specObj, EmbedOptions())
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
