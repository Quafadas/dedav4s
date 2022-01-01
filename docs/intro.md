---
layout: default
title: Explanation
---

# Explanation





<div id="viz_uLPsep9A" class="viz"></div>

<script type="text/javascript">
const specuLPsep9A = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "padding": 5,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "2Pp5Xx6C",
          "amount": "1"
        },
        {
          "category": "KtByUAhq",
          "amount": "2"
        },
        {
          "category": "SZYC5qdf",
          "amount": "3"
        },
        {
          "category": "8ggQF32B",
          "amount": "4"
        },
        {
          "category": "cwtqtlkN",
          "amount": "5"
        },
        {
          "category": "nVyeyszi",
          "amount": "6"
        },
        {
          "category": "GpNwKL34",
          "amount": "7"
        },
        {
          "category": "dhDiJxJp",
          "amount": "8"
        },
        {
          "category": "nzZjxP9W",
          "amount": "9"
        },
        {
          "category": "qvFyDL3k",
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
vegaEmbed('#viz_uLPsep9A', specuLPsep9A , {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz_uLPsep9A", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>

Ideally, this would show a second chart



<div id="viz_kD8SbDnZ" class="viz"></div>

<script type="text/javascript">
const speckD8SbDnZ = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "padding": 5,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "DhRDr9Js",
          "amount": "1"
        },
        {
          "category": "EGEgL8ZS",
          "amount": "2"
        },
        {
          "category": "RkAljwqU",
          "amount": "3"
        },
        {
          "category": "aJI82Q1o",
          "amount": "4"
        },
        {
          "category": "e6aYpbKp",
          "amount": "5"
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
vegaEmbed('#viz_kD8SbDnZ', speckD8SbDnZ , {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz_kD8SbDnZ", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>
