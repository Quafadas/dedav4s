package viz.macros

import munit.FunSuite

class VegaPlotTest extends FunSuite {
  
  test("VegaPlot.fromFile should load pie chart spec") {
    val spec = VegaPlot.fromFile("pie.vl.json")
    assert(spec.specPath == "pie.vl.json")
  }
  
  test("VegaPlot should allow modifying title") {
    val spec = VegaPlot.fromFile("pie.vl.json")
    import spec.mods.*
    
    val result = spec.plot(
      title("Custom Title")
    )
    
    assert(result("title").str == "Custom Title")
  }
  
  test("VegaPlot should allow modifying nested encoding.theta.field") {
    val spec = VegaPlot.fromFile("pie.vl.json")
    import spec.mods.*
    
    val result = spec.plot(
      encoding.theta.field("custom_field")
    )
    
    assert(result("encoding")("theta")("field").str == "custom_field")
  }
  
  test("VegaPlot should allow modifying layer array elements") {
    val spec = VegaPlot.fromFile("pie.vl.json")
    import spec.mods.*
    
    val result = spec.plot(
      layer(0).mark.tooltip(false)
    )
    
    assert(result("layer")(0)("mark")("tooltip").bool == false)
  }
  
  test("VegaPlot should allow multiple modifications") {
    val spec = VegaPlot.fromFile("pie.vl.json")
    import spec.mods.*
    
    val result = spec.plot(
      title("New Title"),
      width(800),
      height(600),
      layer(0).mark.tooltip(false),
      layer(1).encoding.text.field("value")
    )
    
    assert(result("title").str == "New Title")
    assert(result("width").num == 800)
    assert(result("height").num == 600)
    assert(result("layer")(0)("mark")("tooltip").bool == false)
    assert(result("layer")(1)("encoding")("text")("field").str == "value")
  }
}
