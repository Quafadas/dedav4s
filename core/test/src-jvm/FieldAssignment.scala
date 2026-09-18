package viz.macros

// Deliberately the whole of circe's syntax package, which is what users used to be told to import. It brings
// `KeyOps` into scope, whose `:=` applies to literally any receiver, so it will happily capture a `:=` the
// field accessors reject. These tests pin down that the accessors answer first, and say something useful.
import io.circe.syntax.*
import io.github.quafadas.plots.SetupVega.{*, given}

class FieldAssignment extends munit.FunSuite:

  val spec = VegaPlot.fromString("""{
    "title": "t",
    "width": 400,
    "autosize": true,
    "padding": null,
    "data": { "values": [ {"a": "A", "b": 28} ] }
  }""")

  val rows = List((a = "A", b = 28), (a = "B", b = 55))
  val dataCfg = (values = rows)

  test("an array field takes a Seq of encodable values, with no .asJson") {
    val result = spec.build(_.data.values := rows)
    assertEquals(
      result.hcursor.downField("data").downField("values").as[List[(a: String, b: Int)]].toOption,
      Some(rows)
    )
  }

  test("an array field appends a Seq of encodable values") {
    val result = spec.build(_.data.values += rows)
    assertEquals(
      result.hcursor.downField("data").downField("values").as[List[(a: String, b: Int)]].toOption,
      Some((a = "A", b = 28) :: rows)
    )
  }

  test("an object field takes anything that encodes to a JSON object") {
    val result = spec.build(_.data := dataCfg)
    assertEquals(
      result.hcursor.downField("data").downField("values").as[List[(a: String, b: Int)]].toOption,
      Some(rows)
    )
  }

  test(".asJson is available from the SetupVega export alone") {
    val result = spec.build(_.data.values := rows.asJson)
    assertEquals(
      result.hcursor.downField("data").downField("values").as[List[(a: String, b: Int)]].toOption,
      Some(rows)
    )
  }

  // Each of these used to report circe's "Context bounds will map to context parameters" from `KeyOps`,
  // which names nothing the user wrote. Assert on our own wording instead.
  test("a wrongly typed assignment names the field kind it was made on") {
    assert(clue(compileErrors("""spec.build(_.title := 42)""")).contains("`:=` on a string field"))
    assert(clue(compileErrors("""spec.build(_.width := "wide")""")).contains("`:=` on a numeric field"))
    assert(clue(compileErrors("""spec.build(_.autosize := "yes")""")).contains("`:=` on a boolean field"))
    assert(clue(compileErrors("""spec.build(_.padding := "lots")""")).contains("`:=` on a null field"))
    assert(clue(compileErrors("""spec.build(_.data.values := "hello")""")).contains("`:=` on an array field"))
    assert(clue(compileErrors("""spec.build(_.data := "not an object")""")).contains("`:=` on an object field"))
  }

  test("no assignment error mentions circe's KeyOps") {
    val errs = compileErrors("""spec.build(_.data.values := "hello")""")
    assert(!clue(errs).contains("Context bounds"))
    assert(!errs.contains("KeyOps"))
  }
end FieldAssignment
