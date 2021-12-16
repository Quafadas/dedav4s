package viz

import ujson.Value
import java.net.URI

trait FromUrl extends WithBaseSpec {

    lazy val url : String = ???

    override lazy val baseSpec = ujson.read(requests.get(url).text())

    def viewBaseSpec = java.awt.Desktop.getDesktop.browse(URI(url.replace(".vg.json", "")))

}
