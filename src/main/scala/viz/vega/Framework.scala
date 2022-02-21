package viz.vega

enum Framework(val stub: String, val ext: String):
  case Vega extends Framework("https://vega.github.io/vega-lite/examples/", ".vg.json")
  case VegaLite extends Framework("https://vega.github.io/vega-lite/examples/", ".vl.json")
