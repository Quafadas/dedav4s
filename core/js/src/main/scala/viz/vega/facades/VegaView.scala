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

package viz.vega.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.JSON

@js.native
trait Options extends js.Object:
  // var loader: js.UndefOr[Loader] = js.native
  var logLevel: js.UndefOr[Int] = js.native
  var renderer: js.UndefOr[String] = js.native
  // var tooltip: js.UndefOr[Tooltip] = js.native
  // var functions: js.UndefOr[js.Array[Function]] = js.native
end Options

/** https://vega.github.io/vega/docs/api/view/
  *
  * @param parsedSpec
  * @param config
  */
@js.native
@JSImport("vega-view", JSImport.Namespace, "vega.View")
class VegaView(parsedSpec: js.Dynamic, config: js.Dynamic) extends js.Object:

  def runAsync(): Unit = js.native

  // def data(s: String, j: js.Dynamic): Unit = js.native

  // Most likely, a js.Array[js.Object]
  def data(s: String, j: js.UndefOr[js.Any]): Unit = js.native

  def signal(s: String, j: js.Dynamic): js.Dynamic = js.native

  def getState(): js.Dynamic = js.native

  def addSignalListener(s: String, handler: js.Function2[String, js.Dynamic, js.UndefOr[js.Dynamic]]): VegaView =
    js.native

  // def data(s:String, j: js.Array[js.Dynamic]): js.Dynamic = js.native

  def addEventListener(s: String, handler: js.Function2[js.Dynamic, js.Dynamic, js.UndefOr[js.Dynamic]]): VegaView =
    js.native

  override def finalize(): Unit = js.native

end VegaView

object Helpers:

  val dataPrintOnlyClickHandler: js.Function2[js.Dynamic, js.Dynamic, js.UndefOr[js.Dynamic]] =
    (event: js.Dynamic, item: js.Dynamic) =>
      val tmp = item.datum
      if tmp == js.undefined then println("No data found")
      else println(JSON.stringify(tmp))

  val dataClickHandler: js.Function2[js.Dynamic, js.Dynamic, js.UndefOr[js.Dynamic]] =
    (event: js.Dynamic, item: js.Dynamic) => item.datum

  extension (vv: VegaView)
    def printState(): Unit =
      println(JSON.stringify(vv.getState(), space = 2))

    def safeAddSignalListener(
        forSignal: String,
        handler: (x: String, y: js.Dynamic) => Unit
    ): VegaView =
      val signals = getSignals()
      assert(
        signals.contains(forSignal),
        s"Signal $forSignal not found in this graph - try the getSignals method or getState() to view the list of current signals"
      )
      vv.addSignalListener(forSignal, handler)
    end safeAddSignalListener

    def getSignals(): List[String] =
      val tmp = vv.getState()
      js.Object.keys(tmp.signals.asInstanceOf[js.Object]).toList
    end getSignals

    def printSignalEventHandler(forSignal: String): VegaView =
      val signals = getSignals()
      assert(
        signals.contains(forSignal),
        s"Signal $forSignal not found in this graph - try the getSignals method or getState() to view the list of current signals"
      )
      val handler: js.Function2[String, js.Dynamic, js.Dynamic] = (str: String, dyn: js.Dynamic) =>
        println(s"Signal ${str.toString()} fired with value")
        println(JSON.stringify(dyn, space = 1))
        js.Dynamic.literal()

      vv.addSignalListener(forSignal, handler)
    end printSignalEventHandler

    def getSignalEventHandler(forSignal: String): VegaView =
      val signals = getSignals()
      assert(
        signals.contains(forSignal),
        s"Signal $forSignal not found in this graph - try the getSignals method or getState() to view the list of current signals"
      )
      val handler: js.Function2[String, js.Dynamic, js.Dynamic] = (str: String, dyn: js.Dynamic) => dyn
      vv.addSignalListener(forSignal, handler)
    end getSignalEventHandler

    def printEventHandler(forEvent: String = "click"): VegaView =

      val getCircularReplacer: js.Function0[js.Function2[String, js.Any, js.Any]] = () =>
        val seen = new js.Set[js.Object]()

        { (key: String, value: js.Any) =>
          value match
            case v: js.Object =>
              if seen.contains(v) then js.undefined
              else
                seen.add(v)
                v
            case _ => value
        }

      val handler: js.Function2[js.Dynamic, js.Dynamic, js.Dynamic] = (event: js.Dynamic, item: js.Dynamic) =>
        println(JSON.stringify(event, space = 1))
        println(JSON.stringify(item, getCircularReplacer()))
        js.Dynamic.literal()

      vv.addEventListener(forEvent, handler)
    end printEventHandler

  end extension

end Helpers
