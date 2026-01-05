package viz.doc
import org.scalajs.dom
import scala.util.Random
import org.scalajs.dom.Element
import org.scalajs.dom.XMLHttpRequest
import scala.scalajs.js.JSON
object JsDocs:

  def fromUjson(spec: ujson.Value, node: Element, width: Int = 50) =
    val spec_ = upickle.default.write(spec)
    showSpec(spec_, node, width)
  end fromUjson

  def showSpec(spec: String, node: Element, width: Int = 50) =
    val child = dom.document.createElement("div")
    val anId = "vega" + Random.alphanumeric.take(8).mkString("")
    child.id = anId
    child.setAttribute("style", s"width:${width}vmin;height:${width}vmin")
    node.appendChild(child)

    val opts = viz.vega.facades.EmbedOptions()
    val parsed = JSON.parse(spec)
    viz.vega.facades.embed(s"#$anId", parsed, opts)
    ()
  end showSpec

  def showPath(path: String, node: Element, width: Int = 50) =
    val child = dom.document.createElement("div")
    val anId = "vega" + Random.alphanumeric.take(8).mkString("")
    child.id = anId
    child.setAttribute("style", s"width:${width}vmin;height:${width}vmin")
    node.appendChild(child)

    val opts = viz.vega.facades.EmbedOptions()
    val xhr = new XMLHttpRequest()
    xhr.open("GET", s"../assets/$path.json", false)
    xhr.send()
    val text = xhr.responseText
    val parsed = JSON.parse(text)
    viz.vega.facades.embed(s"#$anId", parsed, opts)
    ()
  end showPath
end JsDocs
