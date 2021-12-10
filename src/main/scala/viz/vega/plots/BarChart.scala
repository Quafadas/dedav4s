package viz.vega.plots

import viz.WithBaseSpec
import viz.target.PlotTarget



class BarChart(override val modifiers : List[ujson.Value => Unit] = List()) extends WithBaseSpec {

    override lazy val baseSpec = ujson.read(requests.get("https://vega.github.io/vega/examples/bar-chart.vg.json"))    

    

}


