package viz.notebook

import munit.FunSuite
import viz.ChartLibrary

class NotebookTargetsSuite extends FunSuite:

  private val vegaBar = ujson.read(os.read(os.resource / "test.vg.json"))

  private val liteBar = ujson.read("""{
    "$schema": "https://vega.github.io/schema/vega-lite/v6.json",
    "description": "letters",
    "data": {"values": [{"a": "A", "b": 28}, {"a": "B", "b": 55}, {"a": "C", "b": 43}]},
    "mark": "bar",
    "encoding": {"x": {"field": "a", "type": "nominal"}, "y": {"field": "b", "type": "quantitative"}}
  }""")

  private val echartsBar = ujson.read("""{
    "title": {"text": "letters"},
    "xAxis": {"type": "category", "data": ["A", "B", "C"]},
    "yAxis": {"type": "value"},
    "series": [{"type": "bar", "data": [28, 55, 43]}]
  }""")

  test("vega outputs: interactive mimetype, svg, text, then the editor link") {
    val outputs = NotebookTargets.outputs(liteBar, ChartLibrary.Vega, NotebookOptions())
    assertEquals(outputs.length, 2)
    val plot = outputs.head.detailedData
    assertEquals(plot.keys.toList, List(NotebookTargets.VegaLiteMime, "image/svg+xml", "text/plain"))
    assert(plot(NotebookTargets.VegaLiteMime).isInstanceOf[almond.interpreter.api.DisplayData.Value.Json])
    assertEquals(plot("text/plain").asString, Some("Vega-Lite plot: letters"))
    val link = outputs(1).detailedData
    assertEquals(link.keys.toList, List("text/markdown", "text/html", "text/plain"))
    assert(link("text/markdown").asString.exists(_.contains("https://vega.github.io/editor/#/url/vega-lite/")))

    val vegaPlot = NotebookTargets.outputs(vegaBar, ChartLibrary.Vega, NotebookOptions(editorLink = false))
    assertEquals(vegaPlot.length, 1)
    assertEquals(vegaPlot.head.detailedData.keys.head, NotebookTargets.VegaMime)
  }

  test("echarts outputs: svg and text") {
    val outputs = NotebookTargets.outputs(echartsBar, ChartLibrary.Echarts, NotebookOptions())
    assertEquals(outputs.map(_.detailedData.keys.toList), Seq(List("image/svg+xml", "text/plain")))
  }

  test("a failed static render still publishes the interactive plot") {
    val spec = ujson.copy(liteBar)
    spec("data") = ujson.Obj("url" -> "data/cars.json")
    val keys = NotebookTargets.outputs(spec, ChartLibrary.Vega, NotebookOptions()).head.detailedData.keys.toList
    assertEquals(keys, List(NotebookTargets.VegaLiteMime, "text/plain"))
  }

  test("huge specs get no editor link") {
    val big = ujson.copy(liteBar)
    big("data") = ujson.Obj("values" -> ujson.Arr.from((1 to 20000).map(i => ujson.Obj("a" -> s"k$i", "b" -> i))))
    assertEquals(NotebookTargets.editorLink(big, lite = true), None)
  }
end NotebookTargetsSuite
