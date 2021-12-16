import viz.vega.plots._

import viz.PlotTargets.postHttp
import viz.vega.extensions.*

@main
def Main(args: String*): Unit =
  
/*   val b = new BarChart(
    List(spec => spec("height") = 1200)
  ) */

   List(
   "how much wood would a wood chuck chuck if a wood chuck could chuck wood", 
   "a wood chuck would chuck as much wood as a wood chuck could chuck if a wood chuck could chuck wood"
   ).plotWordcloud()