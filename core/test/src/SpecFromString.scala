package viz.macros

import munit.FunSuite
import io.circe.*
import io.circe.syntax.*
import io.circe.optics.JsonPath.*
import io.circe.literal.*

class VegaPlotTest extends FunSuite:

  test("VegaPlot.fromString should generate title accessor for string title") {
    val spec = VegaPlot.fromString("""{"title": "Population by Gender"}""")

    val result = spec.build(_.title := "New Title")

    assertEquals(root.title.string.getOption(result), Some("New Title"))
  }

  test("VegaPlot.fromString should allow Json for title") {
    val spec = VegaPlot.fromString("""{"title": "Population by Gender"}""")

    val result = spec.build(
      _.title := JsonObject("text" -> Json.fromString("Complex Title"), "fontSize" -> Json.fromInt(20))
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

    val result = spec.build(
      _.title := "Updated Title",
      _.description := "New description",
      _.width := 800
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

    val result = spec.build(
      _.width := 800,
      _.height := 600.5,
      _.padding := 10
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

    val result = spec.build(_.autosize := false)

    assertEquals(result.hcursor.get[Boolean]("autosize").toOption, Some(false))
  }

  test("VegaPlot.fromString preserves unchanged fields") {
    val spec = VegaPlot.fromString("""{
      "title": "Original",
      "width": 400,
      "height": 300
    }""")

    val result = spec.build(_.title := "Modified")

    assertEquals(result.hcursor.get[String]("title").toOption, Some("Modified"))
    assertEquals(result.hcursor.get[Int]("width").toOption, Some(400))
    assertEquals(result.hcursor.get[Int]("height").toOption, Some(300))
  }

  test("original spec should not be mutated") {
    val spec = VegaPlot.fromString("""{"title": "Original"}""")

    val result = spec.build(_.title := "Modified")

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

    val result = spec.build(
      _.title.text := "New Title",
      _.title.fontSize := 20
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

    val result = spec.build(
      _.title := json"""{"text": "New Title", "fontSize": 20}"""
    )

    assertEquals(result.hcursor.downField("title").get[String]("text").toOption, Some("New Title"))
    assertEquals(result.hcursor.downField("title").get[Int]("fontSize").toOption, Some(20))
  }

  test("type safety - invalid field names should not compile") {
    // This test verifies at compile time that only known fields are accessible

    // Invalid: accessing a field that doesn't exist
    {
      val spec1 = VegaPlot.fromString("""{"title": "Test"}""")
      val errors = compileErrors("""spec1.build(_.nonexistent := "value")""")
      assert(errors.nonEmpty, "Expected compile errors for nonexistent field")
    }

    // Invalid: accessing nested field that doesn't exist
    {
      val spec2 = VegaPlot.fromString("""{"title": {"text": "Test"}}""")
      val errors = compileErrors("""spec2.build(_.title.invalid := "value")""")
      assert(errors.nonEmpty, "Expected compile errors for invalid nested field")
    }

    // Invalid: wrong type for numeric field
    {
      val spec3 = VegaPlot.fromString("""{"width": 400}""")
      val errors = compileErrors("""spec3.build(_.width := "string")""")
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

    // All these should compile with proper types inferred from the spec
    val result = spec.build(
      _.title.text := "New Title", // StringField.:=(String)
      _.title.fontSize := 24, // NumField.:=(Int)
      _.title.bold := true, // BoolField.:=(Boolean)
      _.width := 800, // NumField.:=(Int)
      _.height := 600.0, // NumField.:=(Double)
      _.autosize := true // BoolField.:=(Boolean)
    )

    assertEquals(result.hcursor.downField("title").get[String]("text").toOption, Some("New Title"))
    assertEquals(result.hcursor.downField("title").get[Int]("fontSize").toOption, Some(24))
    assertEquals(result.hcursor.downField("title").get[Boolean]("bold").toOption, Some(true))
    assertEquals(result.hcursor.get[Int]("width").toOption, Some(800))
    assertEquals(result.hcursor.get[Double]("height").toOption, Some(600.0))
    assertEquals(result.hcursor.get[Boolean]("autosize").toOption, Some(true))
  }

  test("data values") {
    val spec = VegaPlot.fromString("""{
      "data": {
        "values": [
          {"category": "A", "value": 28},
          {"category": "B", "value": 55}
        ]
      }
    }""")
    import viz.NtCirce.given

    val data = List((category = "cat1", value = 100), (category = "cat2", value = 200))

    val result = spec.build(
      _.data.values := data.asJson
    )

    val extractedValues = root.data.values.arr.getOption(result).map { arr =>
      arr.toList.map { item =>
        Map(
          "category" -> item.hcursor.get[String]("category").getOrElse(""),
          "value" -> item.hcursor.get[Int]("value").getOrElse(0)
        )
      }
    }

    assertEquals(
      extractedValues,
      Some(
        List(
          Map("category" -> "cat1", "value" -> 100),
          Map("category" -> "cat2", "value" -> 200)
        )
      )
    )
  }
end VegaPlotTest
