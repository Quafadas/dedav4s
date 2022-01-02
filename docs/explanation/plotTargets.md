---
id: plotTargets
title: Plot Targets
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

Every time an object is created which extends the "Spec" trait, it executes the ```newObject.show()``` side effect. That side effect requires context, provided through a 

## Desktop Browser

Will open a new browser window in your desktop based browser, pointing to a temporary file. 


```scala 
import viz.PlotTargets.desktopBrowser
import viz.vega.extensions.*
```

```scala
List(("A",5),("B",8),("C",-1)).plotBarChart()
```


<div id="viz_9ySBYAOx" class="viz"></div>

<script type="text/javascript">
const spec9ySBYAOx = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "padding": 5,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "A",
          "amount": "5"
        },
        {
          "category": "B",
          "amount": "8"
        },
        {
          "category": "C",
          "amount": "-1"
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
  ],
  "autosize": {
    "type": "fit",
    "resize": true,
    "contains": "padding"
  }
}
vegaEmbed('#viz_9ySBYAOx', spec9ySBYAOx , {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz_9ySBYAOx", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>
The library writes a (temporary) file, assuming that

    java.io.File.createTempFile() 

Is available. That temporary file assumes that you have an internet connection, and can resolve 

    <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>

Finally, we assume the existence of a 

    java.awt.Desktop

Which has a browser available... 

    java.awt.Desktop.browse()

And we browse to the temporary file in step one.

## Websockets / Post
The post side effect is cool used with a [companion project](https://github.com/Quafadas/viz-websockets).

WIP to integrate in a friendly way into this library.

## [Almond](www.almond.sh)

WIP - need a release of almond which supports scala 3

## VSCode 

WIP

## Do Nothing
```scala
import viz.PlotTargets.doNothing
import viz.vega.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart()
// res1: BarChart = BarChart(
//   List(
//     viz.vega.extensions.extensions$package$$$Lambda$22585/0x00000008021623d0@6d411974,
//     viz.Utils$$$Lambda$22368/0x0000000802ceb628@1eecb2a3
//   )
// )
```
To no ones surprise, does nothing! The implementation simply executes unit ```()```. I regret the CPU cycles. 

## printlnTarget
```scala
import viz.PlotTargets.printlnTarget
import viz.vega.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart()
// {
//   "$schema": "https://vega.github.io/schema/vega/v5.json",
//   "description": "A basic bar chart example, with value labels shown upon mouse hover.",
//   "padding": 5,
//   "data": [
//     {
//       "name": "table",
//       "values": [
//         {
//           "category": "A",
//           "amount": "5"
//         },
//         {
//           "category": "B",
//           "amount": "8"
//         },
//         {
//           "category": "C",
//           "amount": "-1"
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
//       "orient": "bottom",
//       "scale": "xscale"
//     },
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
// res3: BarChart = BarChart(
//   List(
//     viz.vega.extensions.extensions$package$$$Lambda$22585/0x00000008021623d0@285a3334,
//     viz.Utils$$$Lambda$22368/0x0000000802ceb628@1eecb2a3
//   )
// )
```