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

import viz.PlotTargets.doNothing
import viz.vega.plots.SeriesScatter
import viz.WithBaseSpec

class CheckUtils extends munit.FunSuite:

  test{"spec with existing height signal, gets overriten"} {
    val specStart = ujson.read("""{  
    "$schema": "https://vega.github.io/schema/vega/v5.json",
    "signals": [
    {
      "name": "rangeStep", "value": 20,
      "bind": {"input": "range", "min": 5, "max": 50, "step": 1}
    },
    {
      "name": "height",
      "update": "trellisExtent[1]"
    }
  ]}""")
    class TestSpec(val baseSpecIn:ujson.Value, override val mods: viz.vega.plots.JsonMod = List()) extends WithBaseSpec {
      override lazy val baseSpec = baseSpecIn
    }    
    
    val out = TestSpec(specStart, List(viz.Utils.fillDiv)).jsonSpec

    val numberSignals = out("signals").arr.length
    assertEquals(numberSignals, 3) // Should have added width, replaced height
    val names = out("signals").arr.map(in => in("name").str)
    assert(names.contains("height"))
    assert(names.contains("width"))


  }

  test("check axis removal") {

    val specStart = SeriesScatter().jsonSpec

    val numberAxes = specStart("axes").arr.length
    assertEquals(numberAxes, 2)

    val remX = SeriesScatter(List(viz.Utils.removeXAxis)).jsonSpec
    assertEquals(remX("axes").arr.length, 1)
    assertEquals(remX("axes")(0)("orient").str, "left")

    val remY = SeriesScatter(List(viz.Utils.removeYAxis)).jsonSpec
    assertEquals(remY("axes").arr.length, 1)
    assertEquals(remY("axes")(0)("orient").str, "bottom")

    val remBoth = SeriesScatter(
      List(
        viz.Utils.removeYAxis,
        viz.Utils.removeXAxis
      )
    ).jsonSpec

    assertEquals(remBoth("axes").arr.length, 0)
  }
