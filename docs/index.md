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

```scala
import viz.PlotTargets.printlnTarget
import viz.vega.extensions.*

val firstChart = (1 to 10).plotBarChart()
// {
//   "$schema": "https://vega.github.io/schema/vega/v5.json",
//   "description": "A basic bar chart example, with value labels shown upon mouse hover.",
//   "padding": 5,
//   "data": [
//     {
//       "name": "table",
//       "values": [
//         {
//           "category": "viYBV9GU",
//           "amount": "1"
//         },
//         {
//           "category": "pu3dzJ5g",
//           "amount": "2"
//         },
//         {
//           "category": "4vq12Txo",
//           "amount": "3"
//         },
//         {
//           "category": "mupzlWdI",
//           "amount": "4"
//         },
//         {
//           "category": "rFR5WA1K",
//           "amount": "5"
//         },
//         {
//           "category": "daNHhgwA",
//           "amount": "6"
//         },
//         {
//           "category": "XmJhKdXq",
//           "amount": "7"
//         },
//         {
//           "category": "Y0XQmw9s",
//           "amount": "8"
//         },
//         {
//           "category": "8sd3Lvzb",
//           "amount": "9"
//         },
//         {
//           "category": "6VSKziBI",
//           "amount": "10"
//         }
//       ]
//     }
//   ],
//   "signals": [
//     {
//       "name": "tooltip",
//       "value": {
//         
//       },
//       "on": [
//         {
//           "events": "rect:mouseover",
//           "update": "datum"
//         },
//         {
//           "events": "rect:mouseout",
//           "update": "{}"
//         }
//       ]
//     },
//     {
//       "name": "height",
//       "init": "isFinite(containerSize()[1]) ? containerSize()[1] : 200",
//       "on": [
//         {
//           "update": "isFinite(containerSize()[1]) ? containerSize()[1] : 200",
//           "events": "window:resize"
//         }
//       ]
//     },
//     {
//       "name": "width",
//       "init": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
//       "on": [
//         {
//           "update": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
//           "events": "window:resize"
//         }
//       ]
//     }
//   ],
//   "scales": [
//     {
//       "name": "xscale",
//       "type": "band",
//       "domain": {
//         "data": "table",
//         "field": "category"
//       },
//       "range": "width",
//       "padding": 0.05,
//       "round": true
//     },
//     {
//       "name": "yscale",
//       "domain": {
//         "data": "table",
//         "field": "amount"
//       },
//       "nice": true,
//       "range": "height"
//     }
//   ],
//   "axes": [
//     {
//       "orient": "left",
//       "scale": "yscale"
//     }
//   ],
//   "marks": [
//     {
//       "type": "rect",
//       "from": {
//         "data": "table"
//       },
//       "encode": {
//         "enter": {
//           "x": {
//             "scale": "xscale",
//             "field": "category"
//           },
//           "width": {
//             "scale": "xscale",
//             "band": 1
//           },
//           "y": {
//             "scale": "yscale",
//             "field": "amount"
//           },
//           "y2": {
//             "scale": "yscale",
//             "value": 0
//           }
//         },
//         "update": {
//           "fill": {
//             "value": "steelblue"
//           }
//         },
//         "hover": {
//           "fill": {
//             "value": "red"
//           }
//         }
//       }
//     },
//     {
//       "type": "text",
//       "encode": {
//         "enter": {
//           "align": {
//             "value": "center"
//           },
//           "baseline": {
//             "value": "bottom"
//           },
//           "fill": {
//             "value": "#333"
//           }
//         },
//         "update": {
//           "x": {
//             "scale": "xscale",
//             "signal": "tooltip.category",
//             "band": 0.5
//           },
//           "y": {
//             "scale": "yscale",
//             "signal": "tooltip.amount",
//             "offset": -2
//           },
//           "text": {
//             "signal": "tooltip.amount"
//           },
//           "fillOpacity": [
//             {
//               "test": "datum === tooltip",
//               "value": 0
//             },
//             {
//               "value": 1
//             }
//           ]
//         }
//       }
//     }
//   ],
//   "autosize": {
//     "type": "fit",
//     "resize": true,
//     "contains": "padding"
//   }
// }
// firstChart: BarChart = BarChart(
//   List(
//     viz.Utils$$$Lambda$42330/0x00000008046e1278@5c604bf,
//     viz.vega.extensions.extensions$package$$$Lambda$42332/0x00000008046e1a88@1da6b562,
//     viz.vega.extensions.extensions$package$$$Lambda$42333/0x00000008046e1e90@4c730f6f
//   )
// )
```
cheat below


<!-- 
<script type="text/javascript">
const spec = {
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
          "category": "A",
          "amount": 28
        },
        {
          "category": "B",
          "amount": 55
        },
        {
          "category": "C",
          "amount": 43
        },
        {
          "category": "D",
          "amount": 91
        },
        {
          "category": "E",
          "amount": 81
        },
        {
          "category": "F",
          "amount": 53
        },
        {
          "category": "G",
          "amount": 19
        },
        {
          "category": "H",
          "amount": 87
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
      "orient": "bottom",
      "scale": "xscale"
    },
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
};  
vegaEmbed('#viz', spec, {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#vis", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script> -->