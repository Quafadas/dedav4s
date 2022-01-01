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
            div#vis {
            width: 25vmin;
            height: 25vmin;
            style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"
        }
    </style>
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
<div id="viz">    
</div>


<script type="text/javascript">
const spec = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "padding": 5,
  "height":400,
  "width":400,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "56Ltdeug",
          "amount": "1"
        },
        {
          "category": "oqPTbUPd",
          "amount": "2"
        },
        {
          "category": "9BuDKKkY",
          "amount": "3"
        },
        {
          "category": "dB3ZhyZV",
          "amount": "4"
        },
        {
          "category": "Z4Hyx3EK",
          "amount": "5"
        },
        {
          "category": "VUX5rJ4u",
          "amount": "6"
        },
        {
          "category": "S0SF2fuu",
          "amount": "7"
        },
        {
          "category": "UkmJ42YG",
          "amount": "8"
        },
        {
          "category": "2r4ZqdGj",
          "amount": "9"
        },
        {
          "category": "A5SmQC16",
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

