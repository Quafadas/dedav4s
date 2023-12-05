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

object LaminarViz:

  def dataClickBus: (EventStream[js.UndefOr[js.Dynamic]], (x: js.Dynamic, y: js.Dynamic) => Unit) =
    val (dataClickedBus, callback) = EventStream.withJsCallback[js.UndefOr[js.Dynamic]]
    val fromFct = (x: js.Dynamic, y: js.Dynamic) => callback(dataClickHandler.apply(x, y))
    (dataClickedBus, fromFct)
  end dataClickBus

  def signalBus: (EventStream[js.Dynamic], (x: String, y: js.Dynamic) => Unit) =
    val (hoverBus, callback) = EventStream.withJsCallback[js.Dynamic]
    val handler: js.Function2[String, js.Dynamic, js.Dynamic] = (str: String, dyn: js.Dynamic) => dyn
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
      chart: Spec,
      inDivOpt: Option[Div] = None,
      embedOpt: Option[EmbedOptions] = None
  ): (Div, Signal[Option[VegaView]]) =



    val specObj = JSON.parse(chart.spec).asInstanceOf[js.Object]

    val (embeddedIn, embedResult) = (inDivOpt, embedOpt) match
      case (Some(thisDiv), Some(opts)) =>
        val p: js.Promise[EmbedResult] = viz.vega.facades.VegaEmbed(thisDiv.ref, specObj, opts)
        (thisDiv, p)
      case (Some(thisDiv), None) =>
        val specObj = JSON.parse(chart.spec).asInstanceOf[js.Object]
        val p: js.Promise[EmbedResult] = viz.vega.facades.VegaEmbed(thisDiv.ref, specObj, EmbedOptions)
        (thisDiv, p)
      case (None, Some(opts)) =>
        val newDiv = div(
          width := "40vmin",
          height := "40vmin"
        )
        val p: js.Promise[EmbedResult] = viz.vega.facades.VegaEmbed(newDiv.ref, specObj, opts)
        (newDiv, p)
      case (None, None) =>
        val newDiv = div(
          width := "40vmin",
          height := "40vmin"
        )
        val p: js.Promise[EmbedResult] = viz.vega.facades.VegaEmbed(newDiv.ref, specObj, EmbedOptions)
        (newDiv, p)


    val view : Signal[Option[VegaView]] = Signal.fromJsPromise(embedResult).map(in => in.map(_.view))


    embedResult.`then`(in =>
      embeddedIn.amend(
        onUnmountCallback(_ =>
          in.view.finalize()
        )
      )
    )
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
  def simpleEmbed(chart: Spec, inDivOpt: Option[Div] = None, embedOpt: Option[EmbedOptions] = None): Div =
    viewEmbed(chart, inDivOpt, embedOpt)._1

  end simpleEmbed

end LaminarViz
