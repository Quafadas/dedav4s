package viz.macros

import munit.FunSuite
import io.circe.*
import io.circe.syntax.*
import io.circe.optics.JsonPath.*
import viz.NtCirce.given

class VegaPlotFromResourceTest extends FunSuite:

  test("relativeToSource resolves from the call-site source directory") {
    val spec = VegaPlot.relativeToSource("../resources/BarChartLite.json")
    assertEquals(spec.rawSpec.hcursor.get[String]("mark").toOption, Some("bar"))
  }

  test("projectRoot resolves from the nearest project marker") {
    val spec = VegaPlot.projectRoot("core/test/resources/BarChartLite.json")
    assertEquals(spec.rawSpec.hcursor.get[String]("mark").toOption, Some("bar"))
  }

  test("array access with fromResource - tuple-style accessor for heterogeneous arrays") {
    val spec = VegaPlot.fromResource("arr.vl.json")
    val result = spec.build(
      _.layer.head.data.values := List((x = 1)).asJson,
      _.layer._1.data.sequence.start := 2
    )

    // Verify the change was applied
    val updatedStart = result.hcursor
      .downField("layer")
      .downN(1)
      .downField("data")
      .downField("sequence")
      .get[Int]("start")
      .toOption
    assertEquals(updatedStart, Some(2))
  }

end VegaPlotFromResourceTest
