package viz.vega.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.JSON
import scala.concurrent.Future
import scala.concurrent.Promise

object VegaEmbed {  

        @js.native
        @JSImport("vega-embed", JSImport.Default)
        def embed(clz : String, spec : js.Dynamic, opts : js.Dynamic) : js.Promise[js.Dynamic] = js.native

}