---
id: home
title: Home
---
# Dedav4s

Declarative data visualization for scala - a scala plotting concept. 

It is written exclusively in scala 3 (currently), but will work with scala 2.13.6+ via forward compatibility.

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

![intro](assets/dedav_intro.gif)

To add this library to an sbt project
```scala
libraryDependencies += "io.github.quafadas" %% "dedav4s" % "0.4.0"
```
To use this library in ammonite
```scala
import $ivy.`io.github.quafadas::dedav4s:0.4.0`
```

Fire up an sbt console (or in a repl... )

```scala
import viz.PlotTargets.desktopBrowser
import viz.extensions.*
```


```scala
(1 to 10).plotBarChart()
// res1: BarChart = BarChart(
//   mods = List(
//     viz.extensions.extensions$package$$$Lambda$11180/0x00000008027aa9e0@31e20b12,
//     remove X axis
//   )
// )
```

A side effect should open a browser window, with this inside



<div id="viz_sdyCoscN" class="viz"></div>

<script type="text/javascript">
const specsdyCoscN = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "width": 400,
  "height": 200,
  "padding": 5,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "Yp1CkvLR",
          "amount": 1
        },
        {
          "category": "pMbpAO5g",
          "amount": 2
        },
        {
          "category": "nZPBmyB6",
          "amount": 3
        },
        {
          "category": "u4HvYJ8V",
          "amount": 4
        },
        {
          "category": "Ff8aPEI8",
          "amount": 5
        },
        {
          "category": "W8z1kjFy",
          "amount": 6
        },
        {
          "category": "TB6mdUBt",
          "amount": 7
        },
        {
          "category": "dL64Fbik",
          "amount": 8
        },
        {
          "category": "ClYkIXyu",
          "amount": 9
        },
        {
          "category": "ImlTLxps",
          "amount": 10
        }
      ]
    }
  ],
  "signals": [
    {
      "name": "tooltip",
      "value": {
        
      },
      "on": [
        {
          "events": "rect:mouseover",
          "update": "datum"
        },
        {
          "events": "rect:mouseout",
          "update": "{}"
        }
      ]
    }
  ],
  "scales": [
    {
      "name": "xscale",
      "type": "band",
      "domain": {
        "data": "table",
        "field": "category"
      },
      "range": "width",
      "padding": 0.05,
      "round": true
    },
    {
      "name": "yscale",
      "domain": {
        "data": "table",
        "field": "amount"
      },
      "nice": true,
      "range": "height"
    }
  ],
  "axes": [
    {
      "orient": "left",
      "scale": "yscale"
    }
  ],
  "marks": [
    {
      "type": "rect",
      "from": {
        "data": "table"
      },
      "encode": {
        "enter": {
          "x": {
            "scale": "xscale",
            "field": "category"
          },
          "width": {
            "scale": "xscale",
            "band": 1
          },
          "y": {
            "scale": "yscale",
            "field": "amount"
          },
          "y2": {
            "scale": "yscale",
            "value": 0
          }
        },
        "update": {
          "fill": {
            "value": "steelblue"
          }
        },
        "hover": {
          "fill": {
            "value": "red"
          }
        }
      }
    },
    {
      "type": "text",
      "encode": {
        "enter": {
          "align": {
            "value": "center"
          },
          "baseline": {
            "value": "bottom"
          },
          "fill": {
            "value": "#333"
          }
        },
        "update": {
          "x": {
            "scale": "xscale",
            "signal": "tooltip.category",
            "band": 0.5
          },
          "y": {
            "scale": "yscale",
            "signal": "tooltip.amount",
            "offset": -2
          },
          "text": {
            "signal": "tooltip.amount"
          },
          "fillOpacity": [
            {
              "test": "datum === tooltip",
              "value": 0
            },
            {
              "value": 1
            }
          ]
        }
      }
    }
  ]
}
vegaEmbed('#viz_sdyCoscN', specsdyCoscN , {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz_sdyCoscN", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>
If that worked, then you're ready to go! See the [plot targets](explanation/plotTargets.md) to understand what happened, and the [examples](explanation/examples.md) for suggestions on how to use and extend the concepts.

# Background Information
This is a thin shim around [vega](https://vega.github.io/vega/) and [vega lite](https://vega.github.io/vega-lite/). It is aimed at repl, interactive environments and exploratory analysis

It pays to have an understanding (or at least some idea of what those are), both Vega & Vega-Lite. It may be worth taking a few minutes to orient yourself with this [talk/demo](https://www.youtube.com/watch?v=9uaHRWj04D4) from the creators at the Interactive Data Lab (IDL) at University of Washington.

If you are interested in plotting in general, I think that you will not regret learning a declarative paradigm.

# The important links
[Vega documentation](https://vega.github.io/vega/docs/)

[Vega Lite documentation](https://vega.github.io/vega-lite/docs/)
