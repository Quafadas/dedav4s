package viz.macros.examples

import ujson.Value
import viz.macros.VegaPlot

/**
 * Example usage of the VegaPlot macro.
 * 
 * This demonstrates the POC implementation showing how to:
 * 1. Load a Vega-Lite spec from resources at compile time
 * 2. Use type-safe helpers to modify the spec
 * 3. Get IDE autocomplete for all fields
 */
object PieChartExample {
  
  def customizedPieChart(): Value = {
    // Load the pie chart spec - this happens at compile time
    val spec = VegaPlot.fromFile("pie.vl.json")
    
    // Import the generated helpers for autocomplete
    import spec.mods.*
    
    // Modify the spec using type-safe helpers
    spec.plot(
      // Primitive fields
      title("Sales by Department"),
      width(800),
      height(600),
      description("A customized pie chart showing sales distribution"),
      
      // Nested objects
      encoding.theta.field("sales"),
      encoding.theta.`type`("quantitative"),
      encoding.color.field("department"),
      encoding.color.`type`("nominal"),
      
      // Structural arrays - layer 0
      layer(0).mark.tooltip(true),
      
      // Structural arrays - layer 1
      layer(1).encoding.text.field("department"),
      layer(1).encoding.text.`type`("nominal")
    )
  }
}
