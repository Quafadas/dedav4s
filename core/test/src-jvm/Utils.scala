import viz.PlotTargets

class CheckUtils extends munit.FunSuite:

  import viz.PlotTargets.tempHtmlFile
  import viz.vegaFlavour
  import viz.Plottable.*

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

    val _ = specStart.plot(List(viz.Utils.fillDiv))

    // val numberSignals = ouSpec("signals").arr.length
    // assertEquals(numberSignals, 3) // Should have added width, replaced height
    // val names = ouSpec("signals").arr.map(in => in("name").str)
    // assert(names.contains("height"))
    // assert(names.contains("width"))

  }

end CheckUtils
