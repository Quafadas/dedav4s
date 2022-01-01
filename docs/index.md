# Dedav4s
A scala plotting concept
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

To add this library to an sbt project
```scala
libraryDependencies += "io.github.quafadas" % "dedav4s" % "0.0.6"
```
To use this library in ammonite
```scala
import $ivy.`io.github.quafadas::dedav4s:0.0.6`
```

Next, fire up an sbt console (or in a repl... ) Firstly... 

```scala
import viz.PlotTargets.desktopBrowser
import viz.vega.extensions.*
```


```scala
(1 to 10).plotBarChart()
// res1: BarChart = BarChart(
//   List(
//     viz.Utils$$$Lambda$22368/0x0000000802ceb628@1eecb2a3,
//     viz.vega.extensions.extensions$package$$$Lambda$22370/0x0000000802cebe38@3fbdb45a,
//     viz.vega.extensions.extensions$package$$$Lambda$22371/0x0000000802cec240@38e8e475
//   )
// )
```
A side effect should open a browser window, with this inside


<div id="viz_BG0pwBte" class="viz"></div>

<script type="text/javascript">
const specBG0pwBte = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "padding": 5,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "Nj3uEmo9",
          "amount": "1"
        },
        {
          "category": "qSarvPvn",
          "amount": "2"
        },
        {
          "category": "64zvut9O",
          "amount": "3"
        },
        {
          "category": "PS88FaIe",
          "amount": "4"
        },
        {
          "category": "IQfcD5Kf",
          "amount": "5"
        },
        {
          "category": "E00AcuNe",
          "amount": "6"
        },
        {
          "category": "khcx7JAt",
          "amount": "7"
        },
        {
          "category": "RvedqhTo",
          "amount": "8"
        },
        {
          "category": "3lE5h6Fp",
          "amount": "9"
        },
        {
          "category": "jjhQW6QC",
          "amount": "10"
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
vegaEmbed('#viz_BG0pwBte', specBG0pwBte , {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz_BG0pwBte", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>
If that worked, then you're ready to go!

# Background Information
This is a thin shim around [vega](https://vega.github.io/vega/) and [vega lite](https://vega.github.io/vega-lite/). It is aimed at repl, interactive environments and exploratory analysis

It pays to have an understanding (or at least some idea of what those are) core Vega & Vega-Lite. It's worth taking a few minutes to orient yourself with this [talk/demo](https://www.youtube.com/watch?v=9uaHRWj04D4) from the creators at the Interactive Data Lab (IDL) at University of Washington.

If you are interested in plotting in general, I think that you will not regret learning a declaritive paradigm.