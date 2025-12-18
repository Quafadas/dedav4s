# Scautable-Viz API Design: Proof of Concept Specification

## Context

**Project**: dedav4s
**Build Tool**: Mill
**Dependencies**: upickle/ujson

## Current State Analysis

### Critical Problems Identified

1. **Inconsistent customization API**:
   - Plots have: `mods: Seq[ujson.Value => Unit]` as the modifier which is essentially an uptyped API.

2. **Raw ujson manipulation required**: Users must understand Vega-Lite JSON structure:
   ```scala
   mods = Seq(
     spec => spec("title")("text") = "Custom Title",
     spec => spec("encoding")("color")("field") = "type"
   )
   ```

3. **No discoverability**: Users can't autocomplete what options are available

4. **Hardcoded resource paths**: Each plot tightly coupled to single JSON template

---

## Proposed Solution: Codegen Helpers from a Spec

### Target API

```scala
// VegaPlot.fromFile is a macro which analyzes the JSON spec at compile time
val spec = VegaPlot.fromFile("pie.vj.json")

import spec.*

spec.plot(
  title("Custom title") // title is a generated helper method
)

```

### Codegen

For a Spec like the following Vega-Lite JSON:

```json
{
  "$schema": "https://vega.github.io/schema/vega-lite/v6.json",
  "description": "A simple pie chart with labels.",
  "title": "Population by Gender",
  "data": {
    "values": [
      { "category": "Male", "value": 577 },
      { "category": "Female", "value": 314 }
    ]
  },
  "encoding": {
    "theta": { "field": "value", "type": "quantitative", "stack": true },
    "color": { "field": "category", "type": "nominal", "legend": null }
  },
  "layer": [
    {
      "mark": {
        "type": "arc",
        "outerRadius": {
          "expr": "width / 3"
        },
        "tooltip": true
      }
    },
    {
      "mark": {
        "type": "text",
        "fontSize": {
          "expr": "width / 40"
        },
        "radius": {
          "expr": "width / 2.5"
        }
      },
      "encoding": {
        "text": {
          "field": "category",
          "type": "nominal"
        }
      }
    }
  ],
  "width": "container",
  "height": "container"
}

```
We would want to generate these two methods for the "title" attribute:

```scala
object VizMods:
  def title(s: String) = spec => spec("title")("text") = s
  def title(json: ujson.Value) = spec => spec("title") = json
```
With the logic being, that the spec can always accept a ujson.Value, from a user that wants to manipulate the raw JSON.

If, instead, the title attribute was as follows:
```json
{
  "$schema": "https://vega.github.io/schema/vega-lite/v6.json",
  "description": "A simple pie chart with labels.",
  "title": {
    "text": "Population by Gender",
    "fontSize": 14,
    "color": "blue"
  }
}
```
Then we would intead generate helper stubs for:

```scala
object VizMods:
  def title(json: ujson.Value) = spec => spec("title") = json

  object title:
    def text(s: String) = spec => spec("title")("text") = s
    def fontSize(i: Int) = spec => spec("title")("fontSize") = i
    def color(s: String) = spec => spec("title")("color") = s

    def text(s: ujson.Value) = spec => spec("title")("text") = s
    def fontSize(i: ujson.Value) = spec => spec("title")("fontSize") = i
    def color(s: ujson.Value) = spec => spec("title")("color") = s

```
Our goal would be to analyze the spec at each point, and generate these helper methods to allow users to easily modify the spec without needing to understand the underlying JSON structure.

