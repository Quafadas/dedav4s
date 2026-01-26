package viz.macros

import munit.FunSuite
import io.circe.*
import io.circe.optics.JsonPath.*

class VegaPlotFromResourceTest extends FunSuite:

  test("array access with fromResource - tuple-style accessor for heterogeneous arrays") {
    val spec = VegaPlot.fromResource("arr.vl.json")
    val result = spec.build(
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
