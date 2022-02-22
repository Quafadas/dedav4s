package viz.doc
import org.scalajs.dom
import scala.util.Random
import org.scalajs.dom.Element

object showJsDocs:
    def apply(path: String, node: Element) =
        val child = dom.document.createElement("div")
        val anId = "vega" + Random.alphanumeric.take(8).mkString("")        
        child.id = anId
        node.appendChild(child)
        scalajs.js.eval(s"""
            vegaEmbed('#$anId', "$path", {
                renderer: "canvas", // renderer (canvas or svg)
                container: "#$anId", // parent DOM container
                hover: true, // enable hover processing
                actions: {
                    editor : true
                }
            }).then(function(result) {
            console.log(result)
            })""")
