# VizMods POC - VegaPlot Macro Implementation

## Overview

This POC implements compile-time code generation for type-safe Vega-Lite spec modification helpers using Scala 3 macros.

## Usage Example

```scala
import viz.macros.VegaPlot

// Load a Vega-Lite spec from resources at compile time
val spec = VegaPlot.fromFile("pie.vl.json")

// Import the generated helpers
import spec.mods.*

// Use type-safe helpers to modify the spec
val result = spec.plot(
  title("Custom Title"),                    // Primitive: String
  width(800),                                // Primitive: Int
  height(600),                               // Primitive: Int
  encoding.theta.field("custom_field"),      // Nested object access
  layer(0).mark.tooltip(false),              // Structural array with index
  layer(1).encoding.text.field("value")      // Deep nesting in array
)

// result is a ujson.Value containing the modified spec
```

## Features Implemented

### ✅ POC Success Criteria

1. **Generate helpers for a single Vega-Lite spec (pie chart)** ✓
   - Loads `pie.vl.json` from resources at compile time
   - Returns a `VegaPlotSpec` instance with generated helpers

2. **IDE autocomplete working** ✓
   - All helper methods are statically defined
   - Full autocomplete support for:
     - Primitive fields (title, width, height, description)
     - Nested objects (encoding.theta.field, encoding.color.type)
     - Structural arrays (layer(0).mark.tooltip, layer(1).encoding.text)

3. **Handle nested objects** ✓
   - `encoding.theta.field()` - 3 levels deep
   - `encoding.color.legend()` - object with nullable field
   - All nested objects have both typed and ujson.Value overloads

4. **Handle structural arrays** ✓
   - `layer(idx)` returns a LayerElement for indexed access
   - Union of fields from all layer elements:
     - `layer(0).mark.tooltip()` - from first layer
     - `layer(1).encoding.text()` - from second layer
   - Both accessible on any index (union behavior)

5. **Compiles on JVM and JS** ✓
   - Pure Scala 3 code with no platform-specific dependencies
   - ujson works on both JVM and JS

## Type Inference

The implementation provides typed helpers for:

- **String**: `title(s: String)`
- **Int**: `width(i: Int)`, `height(i: Int)`
- **Boolean**: `layer(idx).mark.tooltip(b: Boolean)`
- **ujson.Value**: All methods have an overload accepting `ujson.Value` for complex expressions

Example with expression object:
```scala
layer(0).mark.outerRadius(ujson.Obj("expr" -> "width / 3"))
```

## Architecture

### Macro Implementation

1. **Compile-time file reading**: `VegaPlot.fromFile()` reads JSON at compile time
2. **JSON parsing**: Uses ujson to parse the spec structure
3. **Code generation**: Returns an anonymous class implementing `VegaPlotSpec`
4. **Static helpers**: All helper methods are defined in the `mods` object

### Generated Structure

```scala
trait VegaPlotSpec {
  def specPath: String
  def plot(mods: (ujson.Value => Unit)*): ujson.Value
  def mods: AnyRef  // Contains all helper methods
}
```

The `plot` method:
- Loads the JSON spec from resources at runtime
- Applies all modifier functions in sequence
- Returns the modified ujson.Value

## Array Handling

### Structural Arrays (layer, transform, params)

Generated as indexed accessors with field union:

```scala
object layer {
  def apply(idx: Int) = new LayerElement(idx)
  
  class LayerElement(idx: Int) {
    // Union of all fields across all array elements
    object mark { ... }
    object encoding { ... }
  }
}
```

Usage: `layer(0).mark.tooltip(false)`

### Data Arrays (data.values)

For the POC, data arrays accept ujson.Value:

```scala
object data {
  def values(json: ujson.Value): ujson.Value => Unit
}
```

Future: Could generate typed accessors for uniform data shapes.

### Primitive Arrays (domain: [0, 100])

Accept ujson.Value (same as objects without special handling in POC).

## Comparison with Old API

### Before (untyped):
```scala
plot.show(
  mods = Seq(
    spec => spec("title") = "Custom Title",
    spec => spec("encoding")("theta")("field") = "custom_field",
    spec => spec("layer")(0)("mark")("tooltip") = false
  )
)
```

Problems:
- No autocomplete
- Easy to make typos
- Must know JSON structure
- No type checking

### After (typed):
```scala
val spec = VegaPlot.fromFile("pie.vl.json")
import spec.mods.*

spec.plot(
  title("Custom Title"),
  encoding.theta.field("custom_field"),
  layer(0).mark.tooltip(false)
)
```

Benefits:
- ✅ Full autocomplete
- ✅ Compile-time verification of field names
- ✅ Type-safe primitive values
- ✅ Discoverable API
- ✅ Still allows ujson.Value for complex cases

## Future Enhancements (Out of Scope)

- **Full code generation**: Generate all helpers from JSON structure dynamically
- **Schema validation**: Validate against Vega-Lite schema
- **Data array typing**: Generate typed accessors for data.values
- **Vega (not Vega-Lite) support**: Handle Vega's different structure
- **Migration tool**: Convert existing mods usage to new API

## Testing

Run tests with:
```bash
./mill macros.test
```

Tests validate:
- Loading spec from resources
- Modifying primitive fields
- Modifying nested objects
- Modifying structural array elements
- Combining multiple modifications

## File Structure

```
macros/
├── package.mill          # Build configuration
├── resources/
│   └── pie.vl.json      # Sample Vega-Lite pie chart spec
├── src/
│   └── macros.scala     # VegaPlot macro implementation
└── test/
    └── src/
        └── VegaPlotTest.scala  # Test suite
```
