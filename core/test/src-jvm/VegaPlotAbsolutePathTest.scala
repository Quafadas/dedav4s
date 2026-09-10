package viz.macros

import munit.FunSuite
import io.circe.*
import io.circe.syntax.*
import viz.NtCirce.given

class VegaPlotAbsolutePathTest extends FunSuite:

  test("VegaPlot.absolutePath loads a spec given an absolute path") {
    val spec = VegaPlot.absolutePath(testAbsolutePath("core/test/resources/arr.vl.json"))
    val result = spec.build(
      _.layer.head.data.values := List((x = 1)).asJson,
      _.layer._1.data.sequence.start := 2
    )

    val updatedStart = result.hcursor
      .downField("layer")
      .downN(1)
      .downField("data")
      .downField("sequence")
      .get[Int]("start")
      .toOption
    assertEquals(updatedStart, Some(2))
  }

end VegaPlotAbsolutePathTest
