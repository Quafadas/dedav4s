# Dedav4s
A scala plotting concept
<head>
        <meta charset="utf-8" />
        <!-- Import Vega & Vega-Lite -->
        <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
        <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
        <!-- Import vega-embed -->
        <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
</head>

To install this library
```scala
libraryDependencies += "io.github.quafadas" % "dedav4s" % "0.0.6"
```
To use this library in ammonite
```scala
libraryDependencies += "io.github.quafadas" % "dedav4s" % "0.0.6"
```
```scala
val x = (1 to 10)
// x: Inclusive = Range(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
```
<div id="viz" width: 25vmin; height: 25vmin;></div>


<script type="text/javascript">
const spec = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "padding": 5,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "i6OMvDPT",
          "amount": "1"
        },
        {
          "category": "GhWeoByJ",
          "amount": "2"
        },
        {
          "category": "RoHyRQSo",
          "amount": "3"
        },
        {
          "category": "Ol8ATgUL",
          "amount": "4"
        },
        {
          "category": "I0z5XxM4",
          "amount": "5"
        },
        {
          "category": "EbkzEdB0",
          "amount": "6"
        },
        {
          "category": "RRgBq2Mz",
          "amount": "7"
        },
        {
          "category": "U3SKwTLo",
          "amount": "8"
        },
        {
          "category": "O5C3k32x",
          "amount": "9"
        },
        {
          "category": "CCfLI0PL",
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
vegaEmbed('#viz', spec, {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>

