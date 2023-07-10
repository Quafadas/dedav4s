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
import scala.concurrent.Future
import scala.concurrent.Promise
import org.scalajs.dom.HTMLElement
type Theme = "excel" | "ggplot2" | "quartz" | "vox" | "dark"

@js.native
trait Actions extends js.Object:

  var compiled: js.UndefOr[Boolean] = js.native

  var editor: js.UndefOr[Boolean] = js.native

  var `export`: js.UndefOr[Boolean | ExportAction ] = js.native

  var source: js.UndefOr[Boolean] = js.native
end Actions

@js.native
trait ExportAction extends js.Object {
  var svg: js.UndefOr[Boolean] = js.native
  var png: js.UndefOr[Boolean] = js.native
}

object EmbedOptions extends EmbedOptions()

trait EmbedOptions:

  var actions: js.UndefOr[Boolean | Actions] = true

  var ast: js.UndefOr[Boolean] = js.undefined

  var bind: js.UndefOr[String] = js.undefined

  var config: js.UndefOr[Any] = js.undefined

  var defaultStyle: js.UndefOr[Boolean | String] = js.undefined

  var downloadFileName: js.UndefOr[String] = js.undefined

  var editorUrl: js.UndefOr[String] = js.undefined

  var height: js.UndefOr[Double] = js.undefined

  var hover: js.UndefOr[Boolean] = true

  var loader: js.UndefOr[
    /* import warning: transforms.QualifyReferences#resolveTypeRef many Couldn't qualify Loader */ Any
  ] = js.undefined

  var logLevel: js.UndefOr[Double] = js.undefined

  var mode: js.UndefOr[String] = js.undefined

  var padding: js.UndefOr[Double] = js.undefined

  var scaleFactor: js.UndefOr[Double] = js.undefined

  var sourceFooter: js.UndefOr[String] = js.undefined

  var sourceHeader: js.UndefOr[String] = js.undefined

  var theme: js.UndefOr[Theme] = js.undefined

  var tooltip: js.UndefOr[Any] = js.undefined

  var viewClass: js.UndefOr[
    /* import warning: ResolveTypeQueries.resolve Couldn't resolve typeof View */ Any
  ] = js.undefined

  var width: js.UndefOr[Double] = js.undefined
end EmbedOptions

@js.native
@JSImport("vega-embed", JSImport.Default)
object VegaEmbed extends js.Object:
  def apply(element: HTMLElement, spec: js.Object, options: EmbedOptions): js.Promise[EmbedResult] = js.native

  //def embedChart(element: HTMLElement, spec: viz.Spec , options: EmbedOptions): js.Promise[EmbedResult] = js.native
  
  def embed(clz: String, spec: js.Dynamic, opts: EmbedOptions): js.Promise[EmbedResult] = js.native
end VegaEmbed

@js.native
trait EmbedResult extends js.Object {
  val view: VegaView = js.native
  val spec: js.Object = js.native
  val vgSpec: js.Object = js.native
  override def finalize(): Unit = js.native
}
