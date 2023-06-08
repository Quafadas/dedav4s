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
@JSImport("vega-view", JSImport.Namespace, "view.Vega")
class VegaView(parsedSpec: js.Dynamic, config: js.Dynamic) extends js.Object:

  def runAsync(): Unit = js.native

  def data(s: String, j: js.Dynamic): Unit = js.native

  def data(s: String, j: js.Array[js.Object]): Unit = js.native

  def signal(s: String): js.Dynamic = js.native

  def addSignalListener(s: String, handler: js.Function2[String, js.Dynamic, js.Dynamic]): VegaView = js.native
end VegaView
