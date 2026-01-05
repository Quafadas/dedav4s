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

end AlmondGithubCompatTest
