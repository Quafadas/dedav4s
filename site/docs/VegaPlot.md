## VegaPlot

The concept is that the macro parses some JSON template which represents a visualization spec (in vega / vega-lite / echarts), that is close to the plot you wish to draw. The spec is assumed to be incomplete - it may be missing data for example that is generated at runtime.

The macro parses the JSON object and generates
- setters for every field that match the JSON AST provided - e.g. title would usually be a String.
- setters which accept a circe `Json` value. This is the escape hatch - you can put anything in here.

## Example

Let's make a bar chart. The vega-lite spec for a bar chart is [here](https://vega.github.io/vega/examples/bar-chart/). It is recommended to follow these examples along in a repl.

```scala
import io.github.quafadas.plots.SetupVegaBrowser.{*, given}
import io.circe.syntax.*

val barChart = VegaPlot.fromString("""{
  "$schema": "https://vega.github.io/schema/vega-lite/v6.json",
  "description": "A simple bar chart with embedded data.",
  "data": {
    "values": [
      {"a": "A", "b": 28}, {"a": "B", "b": 55}, {"a": "C", "b": 43}
    ]
  },
  "mark": "bar",
  "encoding": {
    "x": {"field": "a", "type": "nominal", "axis": {"labelAngle": 0}},
    "y": {"field": "b", "type": "quantitative"}
  }
}""")

```
We can set some trivial property.

```scala
barChart.plot(
  _description := "Redescribed...",
)
```

Note that if we try to set a title:

```scala
barChart.plot(
  _.title := "My Bar Chart"
)

```
This is a compile error because there is no title field in the JSON spec. If we put the title field _in_ the JSON spec:

```scala
import io.github.quafadas.plots.SetupVegaBrowser.{*, given}
import io.circe.syntax.*

val barChart = VegaPlot.fromString("""{
  "$schema": "https://vega.github.io/schema/vega-lite/v6.json",
  "description": "A simple bar chart with embedded data.",
  "title": "My Bar Chart",
  "data": {
    "values": [
      {"a": "A", "b": 28}, {"a": "B", "b": 55}, {"a": "C", "b": 43}
    ]
  },
  "mark": "bar",
  "encoding": {
    "x": {"field": "a", "type": "nominal", "axis": {"labelAngle": 0}},
    "y": {"field": "b", "type": "quantitative"}
  }
}""")
```

And give it another go

```scala
barChart.plot(
  _.title := "With title"
)
```
Then it will accept it.

Also: at every node in the JSON AST, `VegaPlot` will accept circe's `Json` type. For example, Vega accepts a broader set of properties for title other than just text.

But this will accept arbitrary JSON.
```scala
import io.circe.literal.*
barChart.plot(
  _.title := json"""{"text": "A bigger title", "fontSize": 30}"""
)
```
This will fail - `nottitle` is not a valid field.

```scala
import io.circe.literal.*
barChart.plot(
  _.nottitle := json"""{"text": "A bigger title", "fontSize": 30}"""
)
```

We can take advantage of this, and the neatness of scala's `NamedTuple`, to set more or less anything we want in an anonymous, convienient manner.

```scala
barChart.plot(
  _.data.values := List(
    (a = "A", b = 10),
    (a = "B", b = 20),
    (a = "C", b = 30)
  ).asJson,
  _.title := (text = "Custom Data", fontSize = 25).asJson
)
```
This exposes the entire oportunity set of vega / lite in a reasonably convienient manner.

One can easily build fairly robust, typesafe visualiations on top of this small set of abstractions.
