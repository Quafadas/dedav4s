/*
 * Copyright 2024 quafadas
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

import com.github.tarao.record4s.%
import com.github.tarao.record4s.Tag
import ujson.Value

import upickle.default.writeJs
import upickle.default.ReadWriter
import com.github.tarao.record4s.*
// import com.github.tarao.record4s.upickle.Record.readWriter

trait VegaPlot

object RecordsExtensions:
  extension [R <: %](e: R & Tag[VegaPlot])(using rw: ReadWriter[R], lpt: LowPriorityPlotTarget)
    def plot: WithBaseSpec =
      val u = writeJs(e)(rw)
      new WithBaseSpec:
        override lazy val baseSpec: Value = u
      end new

end RecordsExtensions
