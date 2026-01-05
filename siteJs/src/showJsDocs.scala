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
