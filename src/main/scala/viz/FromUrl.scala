package viz

import ujson.Value
import viz.vega.plots.SpecUrl
import java.net.URI
import viz.vega.Framework

abstract class FromUrl(val location:SpecUrl)(using PlotTarget) extends WithBaseSpec {

    //lazy val url : String = ???

    override lazy val baseSpec = location.jsonSpec

    def viewBaseSpec(f: Framework = Framework.Vega) = 
        java.awt.Desktop.getDesktop.browse(URI(location.url.replace(f.ext, "")))

}
