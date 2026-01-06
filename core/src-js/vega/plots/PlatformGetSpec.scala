package viz

import viz.vega.Framework
import viz.vega.Framework.*
import org.scalajs.dom.XMLHttpRequest

private[viz] trait PlatformGetSpec(val url: String, val f: Framework):

  // I am aware this is naughty, but everything else is synchronous...
  lazy val jsonSpec: ujson.Value = f match
    case Vega =>
      // println(s"Fetching Vega spec from $url")
      val xhr = new XMLHttpRequest()
      xhr.open("GET", url, false)
      xhr.send()
      ujson.read(xhr.responseText)

    case VegaLite =>
      val xhr = new XMLHttpRequest()
      xhr.open("GET", url, false)
      xhr.send()
      val text = xhr.responseText
      val parser = new org.scalajs.dom.DOMParser
      val virtualDoc = parser.parseFromString(text, org.scalajs.dom.MIMEType.`text/html`)
      val something = virtualDoc.querySelector(".example-spec")
      ujson.read(something.textContent)
end PlatformGetSpec
