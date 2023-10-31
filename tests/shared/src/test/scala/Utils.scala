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
import viz.WithBaseSpec

class CheckUtils extends munit.FunSuite:

  import viz.PlotTargets.doNothing

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

    case class TestSpec(val baseSpecIn: ujson.Value, override val mods: viz.vega.plots.JsonMod = List())
        extends WithBaseSpec(mods):
      override lazy val baseSpec = baseSpecIn
    end TestSpec

    println("bah")
    val out = TestSpec(specStart, List(viz.Utils.fillDiv))
    println("bahM")
    val ouSpec = out.jsonSpec
    println("bah2")

    val numberSignals = ouSpec("signals").arr.length
    assertEquals(numberSignals, 3) // Should have added width, replaced height
    val names = ouSpec("signals").arr.map(in => in("name").str)
    assert(names.contains("height"))
    assert(names.contains("width"))

  }

  /** Check that the trait gets added to the serialised json.
    */
  test("Bar plot data trait") {

    import viz.vega.plots.BarChart.{*, given}
    import upickle.default.*

    def chooseColor(isScala: Boolean, bigBox: Boolean) =
      (isScala, bigBox) match
        case (true, _)     => "red"
        case (false, true) => "green"
        case _             => "steelblue"

    case class QuicktypeTestResult(
        language: String,
        minutes: Int,
        seconds: Int,
        bigBox: Boolean = false,
        isScala: Boolean = false
    ) extends BarPlottable(category = language, amount = minutes * 60 + seconds)
        with MarkColour(colour = chooseColor(isScala, bigBox))
        derives ReadWriter

    val d = QuicktypeTestResult("t", 2, 32)

    val out = upickle.default.write(d)

    assertEquals(out, """{"amount":152,"colour":"steelblue","seconds":32,"minutes":2,"language":"t","category":"t"}""")

  }
end CheckUtils
// test("check axis removal") {

//   val specStart = SeriesScatter().jsonSpec

//   val numberAxes = specStart("axes").arr.length
//   assertEquals(numberAxes, 2)

//   val remX = SeriesScatter(List(viz.Utils.removeXAxis)).jsonSpec
//   assertEquals(remX("axes").arr.length, 1)
//   assertEquals(remX("axes")(0)("orient").str, "left")

//   val remY = SeriesScatter(List(viz.Utils.removeYAxis)).jsonSpec
//   assertEquals(remY("axes").arr.length, 1)
//   assertEquals(remY("axes")(0)("orient").str, "bottom")

//   val remBoth = SeriesScatter(
//     List(
//       viz.Utils.removeYAxis,
//       viz.Utils.removeXAxis
//     )
//   ).jsonSpec

//   assertEquals(remBoth("axes").arr.length, 0)
// }
