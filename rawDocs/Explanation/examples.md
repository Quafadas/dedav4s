---
id: example
title: Examples
---
<head>
        <meta charset="utf-8" />
        <!-- Import Vega & Vega-Lite -->
        <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
        <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
        <!-- Import vega-embed -->
        <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
        <style>
		    div.viz {
                width: 25vmin;
                height:25vmin;
                style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"
            }
        </style>
</head>
# Targets
You need to decide where you want to see the chart. For this library, the first class citizen is a browser... 


```scala mdoc:invisible
import viz.PlotTargets.doNothing
import viz.vega.extensions.*
```

```scala mdoc:vegaplot
List(("A", 4),("B", 6),("C", -1)).plotBarChart()
```

Ideally, this would show a second chart

```scala mdoc:vegaplot
val secondChart = (1 to 5).plotBarChart()
```