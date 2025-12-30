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

import viz.PlotTargets

class CheckUtils extends munit.FunSuite:

  import viz.PlotTargets.tempHtmlFile
  import viz.vegaFlavour
  import viz.Plottable.*

  test("spec with existing height signal, gets overriten") {
    val specStart = ujson.read("""{"$schema": "https://vega.github.io/schema/vega/v5.json",
    "signals": [
    {
      "name": "rangeStep", "value": 20,
      "bind": {"input": "range", "min": 5, "max": 50, "step": 1}
    },
    {
      "name": "height",
      "update": "trellisExtent[1]"
    }]}""".stripMargin.stripLineEnd)

    val _ = specStart.plot(List(viz.Utils.fillDiv))

    // val numberSignals = ouSpec("signals").arr.length
    // assertEquals(numberSignals, 3) // Should have added width, replaced height
    // val names = ouSpec("signals").arr.map(in => in("name").str)
    // assert(names.contains("height"))
    // assert(names.contains("width"))

  }

end CheckUtils
