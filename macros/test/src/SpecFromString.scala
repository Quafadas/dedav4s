package viz.macros

import munit.FunSuite
import io.circe._
import io.circe.syntax.*
import io.circe.optics.JsonPath.*
import io.circe.literal.*

class VegaPlotTest extends FunSuite {

  test("VegaPlot.fromString should generate title accessor for string title") {
    val spec = VegaPlot.fromString("""{"title": "Population by Gender"}""")
    import spec.mod._

    val result = spec.build(title("New Title"))

    assertEquals(root.title.string.getOption(result), Some("New Title"))
  }

  test("VegaPlot.fromString should allow Json for title") {
    val spec = VegaPlot.fromString("""{"title": "Population by Gender"}""")
    import spec.mod._

    val result = spec.build(
      title(JsonObject("text" -> Json.fromString("Complex Title"), "fontSize" -> Json.fromInt(20)))
    )

    assertEquals(result.hcursor.downField("title").get[String]("text").toOption, Some("Complex Title"))
    assertEquals(result.hcursor.downField("title").get[Int]("fontSize").toOption, Some(20))
  }

  test("VegaPlot.fromString should generate accessors for multiple fields") {
    val spec = VegaPlot.fromString("""{
      "title": "My Chart",
      "description": "A description",
      "width": 400
    }""")
    import spec.mod._

    val result = spec.build(
      title("Updated Title"),
      description("New description"),
      width(800)
    )

    assertEquals(result.hcursor.get[String]("title").toOption, Some("Updated Title"))
    assertEquals(result.hcursor.get[String]("description").toOption, Some("New description"))
    assertEquals(result.hcursor.get[Int]("width").toOption, Some(800))
  }

  test("VegaPlot.fromString should handle numeric fields") {
    val spec = VegaPlot.fromString("""{
      "width": 400,
      "height": 300,
      "padding": 5
    }""")
    import spec.mod._

    val result = spec.build(
      width(800),
      height(600.5),
      padding(10)
    )

    assertEquals(result.hcursor.get[Int]("width").toOption, Some(800))
    assertEquals(result.hcursor.get[Double]("height").toOption, Some(600.5))
    assertEquals(result.hcursor.get[Int]("padding").toOption, Some(10))
  }

  test("VegaPlot.fromString should handle boolean fields") {
    val spec = VegaPlot.fromString("""{
      "autosize": true,
      "background": "white"
    }""")
    import spec.mod._

    val result = spec.build(
      autosize(false)
    )

    assertEquals(result.hcursor.get[Boolean]("autosize").toOption, Some(false))
  }

  test("VegaPlot.fromString preserves unchanged fields") {
    val spec = VegaPlot.fromString("""{
      "title": "Original",
      "width": 400,
      "height": 300
    }""")
    import spec.mod._

    val result = spec.build(
      title("Modified")
    )

    assertEquals(result.hcursor.get[String]("title").toOption, Some("Modified"))
    assertEquals(result.hcursor.get[Int]("width").toOption, Some(400))
    assertEquals(result.hcursor.get[Int]("height").toOption, Some(300))
  }

  test("original spec should not be mutated") {
    val spec = VegaPlot.fromString("""{"title": "Original"}""")
    import spec.mod._

    val result = spec.build(
      title("Modified")
    )

    assertEquals(spec.rawSpec.hcursor.get[String]("title").toOption, Some("Original"))
    assertEquals(result.hcursor.get[String]("title").toOption, Some("Modified"))
  }

  test("nested JSON fields should work") {
    val spec = VegaPlot.fromString("""{
      "title": {
        "text": "Old Title",
        "fontSize": 12
      },
      "width": 400
    }""")
    import spec.mod._

    val result = spec.build(
      title.text("New Title"),
      title.fontSize(20),
    )

    assertEquals(result.hcursor.downField("title").get[String]("text").toOption, Some("New Title"))
    assertEquals(result.hcursor.downField("title").get[Int]("fontSize").toOption, Some(20))
  }

  test("replace entire object") {
    val spec = VegaPlot.fromString("""{
      "title": {
        "text": "Old Title",
        "fontSize": 12
      },
      "width": 400
    }""")
    import spec.mod._

    val result = spec.build(
      title(json"""{"text": "New Title", "fontSize": 20}""")
    )

    assertEquals(result.hcursor.downField("title").get[String]("text").toOption, Some("New Title"))
    assertEquals(result.hcursor.downField("title").get[Int]("fontSize").toOption, Some(20))
  }

  test("type safety - invalid field names should not compile") {
    // This test verifies at compile time that only known fields are accessible

    // Uncomment any of these to see compile errors:
      {
        val spec1 = VegaPlot.fromString("""{"title": "Test"}""")
        import spec1.mod._
        val errors = compileErrors("""nonexistent("value")""")
        assert(errors.nonEmpty, "Expected compile errors for nonexistent field")
      }

    // Invalid: accessing nested field that doesn't exist
      {
        val spec2 = VegaPlot.fromString("""{"title": {"text": "Test"}}""")
        import spec2.mod._
        val errors = compileErrors("""title.invalid("value")""")
        assert(errors.nonEmpty, "Expected compile errors for invalid nested field")
      }

      // Invalid: wrong type for numeric field
      {
        val spec3 = VegaPlot.fromString("""{"width": 400}""")
        import spec3.mod._
        val errors = compileErrors("""width("string")""")
        assert(errors.nonEmpty, "Expected compile errors for wrong type")
      }
  }

  test("type safety - correct types should work") {
    val spec = VegaPlot.fromString("""{
      "title": {
        "text": "Test",
        "fontSize": 12,
        "bold": true
      },
      "width": 400,
      "height": 300.5,
      "autosize": false
    }""")
    import spec.mod._

    // All these should compile with proper types inferred from the spec
    val result = spec.build(
      title.text("New Title"),    // StringField.apply(String)
      title.fontSize(24),         // NumField.apply(Int)
      title.bold(true),           // BoolField.apply(Boolean)
      width(800),                 // NumField.apply(Int)
      height(600.0),              // NumField.apply(Double)
      autosize(true)              // BoolField.apply(Boolean)
    )

    assertEquals(result.hcursor.downField("title").get[String]("text").toOption, Some("New Title"))
    assertEquals(result.hcursor.downField("title").get[Int]("fontSize").toOption, Some(24))
    assertEquals(result.hcursor.downField("title").get[Boolean]("bold").toOption, Some(true))
    assertEquals(result.hcursor.get[Int]("width").toOption, Some(800))
    assertEquals(result.hcursor.get[Double]("height").toOption, Some(600.0))
    assertEquals(result.hcursor.get[Boolean]("autosize").toOption, Some(true))
  }



}