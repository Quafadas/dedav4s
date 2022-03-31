/*
 * Copyright 2022 quafadas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viz.doc
import org.scalajs.dom
import scala.util.Random
import org.scalajs.dom.Element
import viz.docs.estree.estreeBooleans.`true`

object showJsDocs:
  def apply(path: String, node: Element, width: Int = 50) =
    //val child = dom.document.getElementById(childId)
    val child = dom.document.createElement("div")
    val anId = "vega" + Random.alphanumeric.take(8).mkString("")
    child.id = anId
    node.appendChild(child)
    child.setAttribute("style", s"width:${width}vmin;height:${width}vmin")
    val opts = viz.docs.vegaEmbed.mod.EmbedOptions[String, viz.docs.vegaTypings.rendererMod.Renderers]()
    opts.setActions(true)
    opts.setHover(true)
    val aPromise = viz.docs.vegaEmbed.mod.default(s"#$anId", s"../assets/$path.json", opts)

/*      scalajs.js.eval(s"""
            vegaEmbed('#$anId', "../assets/$path.json", {
                renderer: "canvas", // renderer (canvas or svg)
                container: "#$anId", // parent DOM container
                hover: true, // enable hover processing
                actions: {
                    editor : true
                }
            }).then(function(result) {
            console.log(result)
            })""") */
