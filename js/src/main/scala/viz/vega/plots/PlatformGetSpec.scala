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

package viz

import viz.vega.Framework
import viz.vega.Framework.*
import scala.concurrent.Await
import scala.concurrent.Future

trait PlatformGetSpec:

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.scalajs.js.Thenable.Implicits.*

  val f: Framework
  val url: String

  // I am aware this is naughty, but everything else is synchronous...
  lazy val jsonSpec: ujson.Value = Await.result(
    f match
      case Vega =>
        for
          response <- org.scalajs.dom.fetch(url)
          text <- response.text()
        yield ujson.read(text)

      case VegaLite => Future(ujson.read(""))
    ,
    scala.concurrent.duration.Duration.Inf
  )
/*       val page = Jsoup.connect(url).get
      val pre = page.select(".language-json")
      val code = pre.asScala.head.text
      ujson.read(code) */
