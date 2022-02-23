package viz.doc
import org.scalajs.dom
import scala.util.Random
import org.scalajs.dom.Element

object showJsDocs:
    def apply(path: String, node: Element, width:Int = 50) = 
        //val child = dom.document.getElementById(childId)
        val child = dom.document.createElement("div")
        val anId = "vega" + Random.alphanumeric.take(8).mkString("")        
        child.id = anId
        node.appendChild(child)
        child.setAttribute("style",s"width:${width}vmin;height:${width}vmin")
        scalajs.js.eval(s"""
            vegaEmbed('#$anId', "../assets/$path.json", {
                renderer: "canvas", // renderer (canvas or svg)
                container: "#$anId", // parent DOM container
                hover: true, // enable hover processing
                actions: {
                    editor : true
                }
            }).then(function(result) {
            console.log(result)
            })""")
