package viz.macros

// The single import the docs tell users to start from. It must be enough on its own: the moment a reader also needs
// `import io.circe.syntax.*` for `.asJson`, circe's `KeyOps` comes with it and quietly captures `:=` on every field
// accessor. Kept as its own file because it star-imports a different object to the rest of the tests.
import io.github.quafadas.plots.SetupVegaBrowser.{*, given}

class DocumentedImport extends munit.FunSuite:

  test("the documented import alone is enough to reach .asJson") {
    val spec = VegaPlot.fromString("""{"title": "t", "data": {"values": [{"a": "A", "b": 1}]}}""")
    val rows = List((a = "A", b = 10))
    val title = (text = "Custom Data", fontSize = 25)

    val result = spec.build(_.data.values := rows, _.title := title.asJson)

    assertEquals(result.hcursor.downField("title").get[Int]("fontSize").toOption, Some(25))
    assertEquals(
      result.hcursor.downField("data").downField("values").as[List[(a: String, b: Int)]].toOption,
      Some(rows)
    )
  }
end DocumentedImport
