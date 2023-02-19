/*
 * Copyright 2023 quafadas
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

package viz

import viz.vega.Framework
import viz.vega.Framework.*
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.Event
import scala.scalajs.js

trait PlatformGetSpec:

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.scalajs.js.Thenable.Implicits.*

  val f: Framework
  val url: String

  // I am aware this is naughty, but everything else is synchronous...
  lazy val jsonSpec: ujson.Value = f match
    case Vega =>
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
