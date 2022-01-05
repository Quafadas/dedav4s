package viz.vega.plots

import viz.FromResource

import viz.PlotTarget

class SunburstDrag(override val mods : JsonMod=List())(using PlotTarget) extends FromResource {
    override lazy val path = "SunburstDrag.json"
}
class SimpleRegression(override val mods : JsonMod=List())(using PlotTarget) extends FromResource {
    override lazy val path = "SimpleRegression.json"
}