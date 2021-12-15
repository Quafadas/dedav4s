package viz.vega.plots

import viz.FromUrl
import viz.PlotTarget



class BarChart(override val modifiers : List[ujson.Value => Unit] = List())(using plotTarget : PlotTarget) extends FromUrl {

    override lazy val url = "https://vega.github.io/vega/examples/bar-chart.vg.json"

}


