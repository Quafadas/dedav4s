package integrationapp

import viz.LaminarViz
import com.raquo.laminar.api.L.*
import viz.vega.facades.EmbedOptions

// A fixed 400 x 200 chart that can only follow its container through attemptAutoResize: no containerSize() signals,
// and autosize "none" with no padding, so the svg is exactly the view's width and height. It is written out here
// rather than taken from SpecUrl, because applyMods mutates the shared SpecUrl specs (the other tabs add fillDiv).
// The container is deliberately not square, so mixing up width and height shows.
val autoResizeSpec = """{
  "$schema": "https://vega.github.io/schema/vega/v6.json",
  "width": 400,
  "height": 200,
  "padding": 0,
  "autosize": "none",
  "data": [
    {
      "name": "table",
      "values": [
        {"category": "A", "amount": 28},
        {"category": "B", "amount": 55},
        {"category": "C", "amount": 43}
      ]
    }
  ],
  "scales": [
    {"name": "x", "type": "band", "domain": {"data": "table", "field": "category"}, "range": "width", "padding": 0.1},
    {"name": "y", "domain": {"data": "table", "field": "amount"}, "nice": true, "range": "height"}
  ],
  "marks": [
    {
      "type": "rect",
      "from": {"data": "table"},
      "encode": {
        "update": {
          "x": {"scale": "x", "field": "category"},
          "width": {"scale": "x", "band": 1},
          "y": {"scale": "y", "field": "amount"},
          "y2": {"scale": "y", "value": 0}
        }
      }
    }
  ]
}"""

def autoResizeBar: Div =
  val (chart, _) = LaminarViz.viewEmbed(
    autoResizeSpec,
    None,
    // No actions menu, so the chart's content box is exactly the container.
    Some(EmbedOptions(actions = false)),
    attemptAutoResize = true
  )
  div(
    cls := "auto-resize",
    width := "80vw",
    height := "300px",
    chart
  )
end autoResizeBar
