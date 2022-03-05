package viz

import viz.vega.Framework
import viz.vega.Framework.*
import scala.concurrent.Await
import scala.concurrent.Future

trait PlatformGetSpec:

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.scalajs.js.Thenable.Implicits.*

  val f: Framework
  val url: String

  // I am aware this is naughty, but everything else is synchronous... 
  lazy val jsonSpec: ujson.Value = Await.result(f match
    case Vega =>
      for
        response <- org.scalajs.dom.fetch(url)
        text <- response.text()
      yield ujson.read(text)

    case VegaLite => Future(ujson.read(""))
    , scala.concurrent.duration.Duration.Inf
  )
/*       val page = Jsoup.connect(url).get
      val pre = page.select(".language-json")
      val code = pre.asScala.head.text
      ujson.read(code) */
