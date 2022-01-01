# Explanation





<div id="viz_u8vbwh72" class="viz"></div>

<script type="text/javascript">
const specu8vbwh72 = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "padding": 5,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "Uzqqc7gt",
          "amount": "1"
        },
        {
          "category": "1hIo0aQK",
          "amount": "2"
        },
        {
          "category": "dTJVrAUt",
          "amount": "3"
        },
        {
          "category": "DC6JPNck",
          "amount": "4"
        },
        {
          "category": "7TNRzssU",
          "amount": "5"
        },
        {
          "category": "idiyGe9t",
          "amount": "6"
        },
        {
          "category": "bE9nXWYe",
          "amount": "7"
        },
        {
          "category": "UeM4Sxnr",
          "amount": "8"
        },
        {
          "category": "BspgVTCd",
          "amount": "9"
        },
        {
          "category": "N1SFrlY7",
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
vegaEmbed('#viz_u8vbwh72', specu8vbwh72 , {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz_u8vbwh72", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>

Ideally, this would show a second chart



<div id="viz_Ua1cRyoV" class="viz"></div>

<script type="text/javascript">
const specUa1cRyoV = {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "description": "A basic bar chart example, with value labels shown upon mouse hover.",
  "padding": 5,
  "data": [
    {
      "name": "table",
      "values": [
        {
          "category": "B8pT4RDo",
          "amount": "1"
        },
        {
          "category": "MYcIVMBI",
          "amount": "2"
        },
        {
          "category": "5ymEGVaj",
          "amount": "3"
        },
        {
          "category": "CFuv1NBe",
          "amount": "4"
        },
        {
          "category": "V6w1QruD",
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
vegaEmbed('#viz_Ua1cRyoV', specUa1cRyoV , {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz_Ua1cRyoV", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>
