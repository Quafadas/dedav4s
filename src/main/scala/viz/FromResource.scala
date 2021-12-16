package viz

import ujson.Value
import java.net.URI

trait FromResource extends WithBaseSpec {

    lazy val path : String = ???

    override lazy val baseSpec = ujson.read( scala.io.Source.fromResource(path, classOf[FromUrl].getClassLoader ).getLines.mkString("") )

}
