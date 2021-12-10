import viz.vega.plots._

import viz.target.PlotTargets.doNothing

@main
def Main(args: String*): Unit =

  val c = new BarChart()
  val b = new BarChart(
    List(spec => spec("height") = 600)
  )
  

  