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
libraryDependencies += "io.github.quafadas" %% "dedav4s" % "0.2.0"
```
To use this library in ammonite
```scala
import $ivy.`io.github.quafadas::dedav4s:0.2.0`
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
//     viz.Utils$$$Lambda$15765/0x0000000803754000@60fa50d6,
//     viz.extensions.extensions$package$$$Lambda$16091/0x000000080380b450@80498ad,
//     remove X axis
//   )
// )
```

A side effect should open a browser window, with this inside



<div id="viz_sUODGtyr" class="viz"></div>

<script type="text/javascript">
const specsUODGtyr = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "padding": 5,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "BSyz6RJR",
          "amount": 1
        },
        {
          "category": "kPWEXgKC",
          "amount": 2
        },
        {
          "category": "dQoGbHin",
          "amount": 3
        },
        {
          "category": "Op4WbroT",
          "amount": 4
        },
        {
          "category": "wPrX25YB",
          "amount": 5
        },
        {
          "category": "UjUcDEAQ",
          "amount": 6
        },
        {
          "category": "q2NrMDDM",
          "amount": 7
        },
        {
          "category": "Vn9jW7G8",
          "amount": 8
        },
        {
          "category": "3UtyTeyQ",
          "amount": 9
        },
        {
          "category": "TXWFaZmn",
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
    },
    {
      "name": "height",
      "init": "isFinite(containerSize()[1]) ? containerSize()[1] : 200",
      "on": [
        {
          "update": "isFinite(containerSize()[1]) ? containerSize()[1] : 200",
          "events": "window:resize"
        }
      ]
    },
    {
      "name": "width",
      "init": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
      "on": [
        {
          "update": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
          "events": "window:resize"
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
  ],
  "autosize": {
    "type": "fit",
    "resize": true,
    "contains": "padding"
  }
}
vegaEmbed('#viz_sUODGtyr', specsUODGtyr , {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz_sUODGtyr", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>
If that worked, then you're ready to go! See the [plot targets](explanation/plotTargets.md) to understand what happened, and the [examples](explanation/examples.md) for suggestions on how to use and extend the concepts.

### Gotcha
One of the targets of this library is the amazing [almond](https://almond.sh) project. That brings in a dependance on jvm-repr which is not on maven central. You'll need to add the resolver 

For sbt
```scala
resolvers += "4 jvm repr" at "https://maven.scijava.org/content/repositories/public/"
```

In predef.sc for ammonite

```scala
interp.repositories() ++= Seq(coursierapi.MavenRepository.of(
   "https://maven.scijava.org/content/repositories/public/"
))
```
If you cannot resolve the artefact due to jvm repr.
# Background Information
This is a thin shim around [vega](https://vega.github.io/vega/) and [vega lite](https://vega.github.io/vega-lite/). It is aimed at repl, interactive environments and exploratory analysis

It pays to have an understanding (or at least some idea of what those are), both Vega & Vega-Lite. It may be worth taking a few minutes to orient yourself with this [talk/demo](https://www.youtube.com/watch?v=9uaHRWj04D4) from the creators at the Interactive Data Lab (IDL) at University of Washington.

If you are interested in plotting in general, I think that you will not regret learning a declarative paradigm.

# The important links
[Vega documentation](https://vega.github.io/vega/docs/)

[Vega Lite documentation](https://vega.github.io/vega-lite/docs/)
