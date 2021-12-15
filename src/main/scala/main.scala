import viz.vega.plots._

import viz.PlotTargets.desktopBrowser
import viz.vega.extensions.*

@main
def Main(args: String*): Unit =
  
/*   val b = new BarChart(
    List(spec => spec("height") = 1200)
  ) */

  //val b1 = Vector(5.0,2.0,6.0).plotBarChart()
  val b2 = Vector(("hi",5l),("to",2l),("number six",6l)).plotBarChart()
  b2.viewBaseSpec


  


  