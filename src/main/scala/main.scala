import viz.vega.plots._

import viz.target.PlotTargets.desktopBrowser

@main
def Main(args: String*): Unit =
  
  val b = new BarChart(
    List(spec => spec("height") = 1200)
  )
  

  