package viz

import viz.vega.Framework
import viz.vega.Framework.*
import collection.JavaConverters.*
import org.jsoup.Jsoup

private[viz] abstract class PlatformGetSpec(val url: String, val f: Framework):

  lazy val jsonSpec: ujson.Value = f match
    case Vega     => ujson.read(requests.get(url).text())
    case VegaLite =>
      val page = Jsoup.connect(url).get
      val pre = page.select(".language-json")
      val code = pre.asScala.head.text
      ujson.read(code)
end PlatformGetSpec
