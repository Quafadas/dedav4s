# Dedav4s

Declarative data visualization for scala - a scala plotting concept.

## Getting Started

Fire up scala-cli.

```sh
scala-cli --dep io.github.quafadas:dedav4s_3:@VERSION@ --repl-init-script 'import io.github.quafadas.plots.SetupVegaBrowser.{*, given}; import io.circe.syntax.*'
```

Paste this into a repl / main method. Note the imports...

```scala
import io.github.quafadas.plots.SetupVegaBrowser.{*, given}
import io.circe.syntax.*

// source: https://vega.github.io/vega-lite/examples/arc_pie.html
val piePlot = VegaPlot.fromString("""{
"$schema": "https://vega.github.io/schema/vega-lite/v6.json",
"title": "A Pie Chart",
"description": "A simple pie chart with embedded data.",
"width": "container",
"height": "container",
"data": {
  "values": [
    {"category": "cat1", "value": 4}
  ]
},
"mark": "arc",
"encoding": {
  "theta": {"field": "value", "type": "quantitative"},
  "color": {"field": "category", "type": "nominal"}
}}""")

piePlot.plot(
  _.title := "Pie Chart: Madeup data}",
  _.data.values := List(
    (category = "cat1", value = 4),
    (category = "cat2", value = 6),
    (category = "cat3", value = 10)
  ).asJson
)

```

You should see a pie chart. To get a feel of how this works, see [VegaPlot](VegaPlot.md).

# Background Information
This is a thin shim around [vega](https://vega.github.io/vega/),  [vega lite](https://vega.github.io/vega-lite/) and [echarts](https://echarts.apache.org/). It's aims are:

1. To make exploratory analysis in a repl (or in a notebook) as easy as possible.
2. To make the barrier to publication (via scala js) as low as possible.
3. To wrap vega / lite in such a manner that charting is as reasonably robust at compile time as possible.

It pays to have an understanding (or at least some idea of what vega / lite are), both Vega & Vega-Lite. It may be worth taking a few minutes to orient yourself with this [talk/demo](https://www.youtube.com/watch?v=9uaHRWj04D4) from the creators at the Interactive Data Lab (IDL) at University of Washington.

If you are interested in plotting in general, I think that you will not regret learning a declarative paradigm.

# Underlying Libraries
[Vega documentation](https://vega.github.io/vega/docs/)

[Vega Lite documentation](https://vega.github.io/vega-lite/docs/)

[Echarts documentation](https://echarts.apache.org/en/index.html)

# Project status
On the JVM it currently contains targets for:

1. repl
2. notebooks
3. websockets
5. Svg, pdf and png files

It further aims to help plotting in scala JS, so that the same charts are easily re-useable in both environments. See Getting started for more information...

<script src="assets/js/refresh.js"></script>