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
import org.scalajs.dom.html.Div
import scala.util.Random
import org.scalajs.dom.Element
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.Event
import scala.scalajs.js
import scala.scalajs.js.JSON
object showJsDocs:
  def apply(path: String, node: Element, width: Int = 50) =
    //val child = dom.document.getElementById(childId)
    println("here!")
    val child = dom.document.createElement("div")
    val anId = "vega" + Random.alphanumeric.take(8).mkString("")
    child.id = anId
    node.appendChild(child)
    child.setAttribute("style", s"width:${width}vmin;height:${width}vmin")
    
    val opts = viz.vega.facades.EmbedOptions
    val xhr = new XMLHttpRequest()
    xhr.open("GET", s"../assets/$path.json", false)
    xhr.send()
    val text = xhr.responseText
    val parsed = JSON.parse(text)
    viz.vega.facades.VegaEmbed.embed(s"#$anId",parsed , opts)
    ()
    

object makePlotTarget:
  def apply(node: Element, width: Int = 50) : Div = 
    val child = dom.document.createElement("div")
    val anId = "vega" + Random.alphanumeric.take(8).mkString("")
    child.id = anId
    child.setAttribute("style", s"width:${width}vmin;height:${width}vmin")
    node.appendChild(child)
    child.asInstanceOf[Div]