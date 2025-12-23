# VizMods POC - VegaPlot Macro Implementation

## Overview

This POC implements compile-time code generation for type-safe Vega-Lite spec modification helpers using Scala 3 macros. **Helper methods are dynamically generated** from the JSON spec structure at compile time, not hardcoded.

## Usage Example

```scala
import viz.macros.VegaPlot

// Load a Vega-Lite spec from resources at compile time
// The macro analyzes the JSON and generates helpers automatically
val spec = VegaPlot.fromFile("pie.vl.json")

// Import the dynamically generated helpers
import spec.mods.*

// Use type-safe helpers to modify the spec
val result = spec.plot(
  title("Custom Title"),                     // Generated from "title": "..."
  width(800),                                 // Generated from "width": "..."
  height(600),                                // Generated from "height": "..."
  encoding.theta.field("custom_field"),       // Generated from nested "encoding.theta.field"
  layer(0).mark.tooltip(false),               // Generated from array "layer[0].mark.tooltip"
  layer(1).encoding.text.field("value")       // Generated from deep nesting
)

// result is a ujson.Value containing the modified spec
```

## Dynamic Code Generation

The macro **analyzes the JSON structure at compile time** and generates appropriate helper methods:

### Type Inference from JSON

| JSON Value | Generated Scala Type | Example |
|------------|---------------------|---------|
| `"string"` | `def field(s: String)` | `"title": "Hello"` → `title(s: String)` |
| `123` (whole) | `def field(i: Int)` | `"width": 800` → `width(i: Int)` |
| `1.5` | `def field(d: Double)` | `"opacity": 0.5` → `opacity(d: Double)` |
| `true`/`false` | `def field(b: Boolean)` | `"tooltip": true` → `tooltip(b: Boolean)` |
| `{...}` | Nested object | Generates child object with recursive helpers |
| `[{...}]` | Structural array | Generates indexed accessor `array(idx)` |
| `null` | ujson.Value only | `def field(json: ujson.Value)` |

**All helpers also have a `ujson.Value` overload** for complex expressions:
```scala
layer(0).mark.outerRadius(ujson.Obj("expr" -> "width / 3"))
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
2. **JSON parsing and analysis**: Uses ujson to parse and analyze the spec structure
3. **Dynamic code generation**: Generates Scala code as strings based on JSON structure
4. **Code synthesis**: Parses generated strings into Scala AST expressions

### Code Generation Algorithm

The macro recursively analyzes the JSON structure:

1. **For each field in the JSON**:
   - Skip internal fields (starting with `$`)
   - Infer Scala type from JSON value type
   - Generate typed helper method
   - Generate ujson.Value overload

2. **For nested objects**:
   - Generate nested `object` with recursive analysis
   - All nested helpers accessible via dot notation

3. **For structural arrays**:
   - Detect array of objects vs data/primitive arrays
   - Generate indexed accessor class
   - Union all fields from all array elements
   - All union fields accessible on any index

### Generated Structure

```scala
object mods {
  // Primitives
  def title(s: String): ujson.Value => Unit = ...
  def title(json: ujson.Value): ujson.Value => Unit = ...
  
  // Nested objects
  object encoding {
    object theta {
      def field(s: String): ujson.Value => Unit = ...
    }
  }
  
  // Structural arrays
  object layer {
    def apply(idx: Int) = new LayerElement(idx)
    class LayerElement(idx: Int) {
      object mark { ... }
      object encoding { ... }
    }
  }
}
```

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
