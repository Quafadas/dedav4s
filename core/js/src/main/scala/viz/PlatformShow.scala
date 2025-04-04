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

import org.scalajs.dom.html
import scala.scalajs.js.JSON
import scala.annotation.nowarn

type VizReturn = Unit

@nowarn
trait PlatformPlot[T]:
  extension (plottable: T)
    def plot()(using plotTarget: LowPriorityPlotTarget): Unit
    def plot(inDiv: html.Div): Unit
  end extension
end PlatformPlot
//   def show(inDiv: html.Div): Unit =
//     val anId = inDiv.id
//     val _ = if anId.isEmpty then
//       val temp = java.util.UUID.randomUUID()
//       inDiv.setAttribute("id", temp.toString())
//     else anId

//     val opts = viz.vega.facades.EmbedOptions()
//     val parsed = JSON.parse(spec)
//     viz.vega.facades.embed.embed(s"#$anId", parsed, opts)
//     ()
//   end show

//   // // when the class is instantiated, show the plot as a side effect...
//   plotTarget match
//     case _: LowPriorityPlotTarget => ()
//     case h: html.Div              => show(h)
//   end match
// end PlatformShow
