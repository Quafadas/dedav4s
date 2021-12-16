package viz.vega.plots

import viz.PlotTarget
import viz.FromUrl


class Wordcloud(override val modifiers : List[ujson.Value => Unit] = List())(using plotTarget : PlotTarget) extends FromUrl {

    override lazy val url = "https://vega.github.io/vega/examples/word-cloud.vg.json"

}


