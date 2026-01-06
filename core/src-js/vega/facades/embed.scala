package viz.vega.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import org.scalajs.dom.HTMLElement

type Theme = "excel" | "ggplot2" | "quartz" | "vox" | "dark" | "default"

@js.native
trait Actions extends js.Object:

  var compiled: js.UndefOr[Boolean] = js.native

  var editor: js.UndefOr[Boolean] = js.native

  var `export`: js.UndefOr[Boolean | ExportAction] = js.native

  var source: js.UndefOr[Boolean] = js.native
end Actions

@js.native
trait ExportAction extends js.Object:
  var svg: js.UndefOr[Boolean] = js.native
  var png: js.UndefOr[Boolean] = js.native
end ExportAction

type renderer = "canvas" | "svg"

class EmbedOptions(
    var actions: js.UndefOr[Boolean | Actions] = true,
    var ast: js.UndefOr[Boolean] = js.undefined,
    var bind: js.UndefOr[String] = js.undefined,
    var config: js.UndefOr[Any] = js.undefined,
    var defaultStyle: js.UndefOr[Boolean | String] = js.undefined,
    var downloadFileName: js.UndefOr[String] = js.undefined,
    var editorUrl: js.UndefOr[String] = js.undefined,
    var height: js.UndefOr[Double] = js.undefined,
    var hover: js.UndefOr[Boolean] = true,
    var loader: js.UndefOr[
      /* import warning: transforms.QualifyReferences#resolveTypeRef many Couldn't qualify Loader */ Any
    ] = js.undefined,
    var logLevel: js.UndefOr[Double] = js.undefined,
    var mode: js.UndefOr[String] = js.undefined,
    var padding: js.UndefOr[Double] = js.undefined,
    var scaleFactor: js.UndefOr[Double] = js.undefined,
    var sourceFooter: js.UndefOr[String] = js.undefined,
    var sourceHeader: js.UndefOr[String] = js.undefined,
    var theme: js.UndefOr[Theme] = js.undefined,
    var tooltip: js.UndefOr[Any] = js.undefined,
    var viewClass: js.UndefOr[
      /* import warning: ResolveTypeQueries.resolve Couldn't resolve typeof View */ Any
    ] = js.undefined,
    var width: js.UndefOr[Double] = js.undefined,
    var renderer: js.UndefOr[renderer] = js.undefined
) extends js.Object

@js.native
@JSImport("##vega-embed", JSImport.Default)
object embed extends js.Object:
  def apply(
      element: HTMLElement | String,
      spec: js.Dynamic | js.Object | String,
      options: EmbedOptions
  ): js.Promise[EmbedResult] = js.native
end embed

@js.native
trait EmbedResult extends js.Object:
  val view: VegaView = js.native
  val spec: js.Object = js.native
  val vgSpec: js.Object = js.native
  override def finalize(): Unit = js.native
end EmbedResult
