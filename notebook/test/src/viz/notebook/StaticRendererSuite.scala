package viz.notebook

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import scala.concurrent.Await
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration.*

import com.sun.net.httpserver.HttpServer
import munit.FunSuite
import viz.ChartLibrary
import viz.CustomPlots
import viz.Utils

class StaticRendererSuite extends FunSuite:

  override val munitTimeout = 3.minutes

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

  private def svgOf(result: Either[String, String]): String =
    result match
      case Right(svg)   => svg
      case Left(reason) => fail(s"expected an svg, got: $reason")

  private def svgSize(svg: String): (Double, Double) =
    def attr(name: String) = raw"""<svg[^>]*\s$name="([\d.]+)"""".r.findFirstMatchIn(svg).map(_.group(1).toDouble)
    (attr("width").getOrElse(fail("svg has no width")), attr("height").getOrElse(fail("svg has no height")))
  end svgSize

  test("vega v5 spec renders, with an opaque background") {
    val svg = svgOf(StaticRenderer.vegaSvg(vegaBar, 640, 400))
    assert(svg.startsWith("<svg"), svg.take(100))
    assert(svg.contains("""fill="white""""), "expected a white background rect")
    assert(svg.contains("""role-mark"""), "expected rendered marks")
  }

  test("vega spec on the v6 schema renders") {
    val v6 = ujson.copy(vegaBar)
    v6("$schema") = "https://vega.github.io/schema/vega/v6.json"
    assert(svgOf(StaticRenderer.vegaSvg(v6, 640, 400)).startsWith("<svg"))
  }

  test("vega-lite spec is compiled then rendered") {
    val svg = svgOf(StaticRenderer.vegaSvg(liteBar, 640, 400))
    assert(svg.contains("role-mark"))
    assert(svg.contains(">B<"), "expected axis labels")
  }

  test("specs sized to their container get the static size") {
    val vega = ujson.copy(vegaBar)
    Utils.fillDiv(vega)
    val (w, h) = svgSize(svgOf(StaticRenderer.vegaSvg(vega, 640, 400)))
    assertEqualsDouble(w, 640, 1)
    assertEqualsDouble(h, 400, 1)

    val lite = ujson.copy(liteBar)
    Utils.fillDiv(lite)
    val (lw, _) = svgSize(svgOf(StaticRenderer.vegaSvg(lite, 640, 400)))
    assertEqualsDouble(lw, 640, 1)
  }

  test("specs bundled in core render") {
    for path <- Seq(CustomPlots.sankey, CustomPlots.simpleRegression, CustomPlots.seriesScatter) do
      val svg = StaticRenderer.vegaSvg(ujson.read(os.read(path)), 640, 400)
      assert(svg.isRight, s"$path: $svg")
  }

  test("echarts option renders at the requested size") {
    val svg = svgOf(StaticRenderer.echartsSvg(echartsBar, 500, 300))
    assertEquals(svgSize(svg), (500d, 300d))
    assert(svg.contains("letters"))
  }

  test("data urls are fetched over http") {
    val server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0)
    server.createContext(
      "/data.json",
      exchange =>
        val body = """[{"a": "X", "b": 3}, {"a": "Y", "b": 7}]""".getBytes("UTF-8")
        exchange.sendResponseHeaders(200, body.length)
        exchange.getResponseBody.write(body)
        exchange.close()
    )
    server.start()
    try
      val spec = ujson.copy(liteBar)
      spec("data") = ujson.Obj("url" -> s"http://127.0.0.1:${server.getAddress.getPort}/data.json")
      val svg = svgOf(StaticRenderer.vegaSvg(spec, 640, 400))
      assert(svg.contains(">Y<"), "expected labels from the fetched data")
    finally server.stop(0)
    end try
  }

  test("relative data urls fail cleanly") {
    val spec = ujson.copy(liteBar)
    spec("data") = ujson.Obj("url" -> "data/cars.json")
    assert(StaticRenderer.vegaSvg(spec, 640, 400).isLeft)
  }

  test("invalid specs fail with a reason, and the renderer keeps working") {
    val bad = ujson.read("""{"$schema": "https://vega.github.io/schema/vega/v5.json", "marks": [{"type": "nope"}]}""")
    val result = StaticRenderer.vegaSvg(bad, 640, 400)
    assert(result.left.exists(_.nonEmpty), result.toString)
    assert(StaticRenderer.vegaSvg(liteBar, 640, 400).isRight)
  }

  test("concurrent renders are serialised") {
    val pool = Executors.newFixedThreadPool(8)
    given ExecutionContext = ExecutionContext.fromExecutor(pool)
    try
      val results = Await.result(
        Future.sequence((1 to 16).map { i =>
          Future(
            if i % 2 == 0 then StaticRenderer.vegaSvg(liteBar, 640, 400)
            else StaticRenderer.echartsSvg(echartsBar, 640, 400)
          )
        }),
        2.minutes
      )
      assert(results.forall(_.isRight), results.filter(_.isLeft).toString)
    finally pool.shutdown()
    end try
  }

  test("vega-lite detection") {
    assert(StaticRenderer.isVegaLite(liteBar))
    assert(!StaticRenderer.isVegaLite(vegaBar))
    assert(StaticRenderer.isVegaLite(ujson.read("""{"mark": "bar"}""")))
    assert(StaticRenderer.isVegaLite(ujson.read("""{"layer": []}""")))
    assert(!StaticRenderer.isVegaLite(ujson.read("""{"marks": [], "data": []}""")))
  }

  test("compressForUrl matches lz-string") {
    // reference values from lz-string's compressToEncodedURIComponent
    assertEquals(StaticRenderer.compressForUrl("hello"), Right("BYUwNmD2Q"))
  }

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
end StaticRendererSuite
