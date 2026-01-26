package viz.doc
import org.scalajs.dom
import scala.util.Random
import org.scalajs.dom.Element
import org.scalajs.dom.XMLHttpRequest
import scala.scalajs.js.JSON
object showJsDocs:
  def apply(path: String, node: Element, width: Int = 50) =
    val child = dom.document.createElement("div")
    val anId = "vega" + Random.alphanumeric.take(8).mkString("")
    child.id = anId
    node.appendChild(child)
    child.setAttribute("style", s"width:${width}vmin;height:${width}vmin")

    val opts = viz.vega.facades.EmbedOptions()
    val xhr = new XMLHttpRequest()
    xhr.open("GET", s"../assets/$path.json", false)
    xhr.send()
    val text = xhr.responseText
    val parsed = JSON.parse(text)
    viz.vega.facades.embed.embed(s"#$anId", parsed, opts)
    ()
  end apply
end showJsDocs
