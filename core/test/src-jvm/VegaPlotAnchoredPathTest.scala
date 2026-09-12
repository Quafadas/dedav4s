package viz.macros

import munit.FunSuite
import io.circe.*
import io.circe.syntax.*
import viz.NtCirce.given

/** These test sources live in `core/test/src-jvm`, so `../resources/x` from here is `core/test/resources/x` - the same
  * file the project-root anchored call addresses by its full path from the repository root.
  */
class VegaPlotAnchoredPathTest extends FunSuite:

  test("VegaPlot.relativeToSource resolves a path from the calling source file's directory") {
    val spec = VegaPlot.relativeToSource("../resources/arr.vl.json")
    val result = spec.build(
      _.layer.head.data.values := List((x = 1)).asJson,
      _.layer._1.data.sequence.start := 2
    )
    assertEquals(startOfSecondLayer(result), Some(2))
  }

  test("VegaPlot.projectRoot resolves a path from the marker-discovered project root") {
    val spec = VegaPlot.projectRoot("core/test/resources/arr.vl.json")
    val result = spec.build(_.layer._1.data.sequence.start := 3)
    assertEquals(startOfSecondLayer(result), Some(3))
  }

  test("anchored constructors treat a leading separator as anchor-relative") {
    val spec = VegaPlot.projectRoot("/core/test/resources/arr.vl.json")
    val result = spec.build(_.layer._1.data.sequence.start := 4)
    assertEquals(startOfSecondLayer(result), Some(4))
  }

  private def startOfSecondLayer(json: Json): Option[Int] =
    json.hcursor
      .downField("layer")
      .downN(1)
      .downField("data")
      .downField("sequence")
      .get[Int]("start")
      .toOption

end VegaPlotAnchoredPathTest
