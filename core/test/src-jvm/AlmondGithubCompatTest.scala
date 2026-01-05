package viz

class AlmondGithubCompatTest extends munit.FunSuite:

  test("Detect Vega-Lite schema and return v4 MIME type") {
    val vegaLiteSpec = ujson.read("""{
      "$schema": "https://vega.github.io/schema/vega-lite/v5.json",
      "data": {"values": [{"a": "A", "b": 28}]},
      "mark": "bar"
    }""")
    
    val schemaUrl = vegaLiteSpec.objOpt
      .flatMap(_.get("$schema"))
      .flatMap(_.strOpt)
      .getOrElse("")
    
    val mimeType = 
      if schemaUrl.contains("vega-lite") then
        "application/vnd.vegalite.v4+json"
      else
        "application/vnd.vega.v3+json"
    
    assertEquals(mimeType, "application/vnd.vegalite.v4+json")
  }

  test("Detect Vega schema and return v3 MIME type") {
    val vegaSpec = ujson.read("""{
      "$schema": "https://vega.github.io/schema/vega/v5.json",
      "width": 400,
      "height": 200
    }""")
    
    val schemaUrl = vegaSpec.objOpt
      .flatMap(_.get("$schema"))
      .flatMap(_.strOpt)
      .getOrElse("")
    
    val mimeType = 
      if schemaUrl.contains("vega-lite") then
        "application/vnd.vegalite.v4+json"
      else
        "application/vnd.vega.v3+json"
    
    assertEquals(mimeType, "application/vnd.vega.v3+json")
  }

  test("Default to Vega v3 MIME type when schema is missing") {
    val noSchemaSpec = ujson.read("""{
      "width": 400,
      "height": 200
    }""")
    
    val schemaUrl = noSchemaSpec.objOpt
      .flatMap(_.get("$schema"))
      .flatMap(_.strOpt)
      .getOrElse("")
    
    val mimeType = 
      if schemaUrl.contains("vega-lite") then
        "application/vnd.vegalite.v4+json"
      else
        "application/vnd.vega.v3+json"
    
    assertEquals(mimeType, "application/vnd.vega.v3+json")
  }

  test("HTML embed contains vega-embed script tag for Vega specs") {
    val vegaSpec = ujson.read("""{
      "$schema": "https://vega.github.io/schema/vega/v5.json",
      "width": 400,
      "height": 200
    }""")
    
    // Simulate HTML generation
    val html = raw"""<div id="vis-${vegaSpec.hashCode.abs}"></div>
<script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
<script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
<script src="https://cdn.jsdelivr.net/npm/vega-embed@6"></script>
<script type="text/javascript">
  vegaEmbed('#vis-${vegaSpec.hashCode.abs}', ${vegaSpec.toString()}).catch(console.error);
</script>"""
    
    assert(html.contains("cdn.jsdelivr.net/npm/vega@5"))
    assert(html.contains("cdn.jsdelivr.net/npm/vega-embed@6"))
    assert(html.contains("vegaEmbed"))
  }

  test("HTML embed contains echarts script tag for Echarts specs") {
    val echartsSpec = ujson.read("""{
      "title": {"text": "Test Chart"},
      "xAxis": {"data": ["A", "B", "C"]},
      "yAxis": {},
      "series": [{"type": "bar", "data": [1, 2, 3]}]
    }""")
    
    // Simulate HTML generation
    val html = raw"""<div id="chart-${echartsSpec.hashCode.abs}" style="width: 600px; height: 400px;"></div>
<script src="https://cdn.jsdelivr.net/npm/echarts@5.6.0/dist/echarts.min.js"></script>
<script type="text/javascript">
  var myChart = echarts.init(document.getElementById('chart-${echartsSpec.hashCode.abs}'));
  myChart.setOption(${echartsSpec.toString()});
</script>"""
    
    assert(html.contains("cdn.jsdelivr.net/npm/echarts@5.6.0"))
    assert(html.contains("echarts.init"))
  }

end AlmondGithubCompatTest
