import viz.vega.plots.*

import viz.PlotTargets.desktopBrowser
import viz.vega.extensions.*

@main
def Main(args: String*): Unit =
   

   // Check extension methods
   // 1. Numeric Iterable
   (1 to 10).plotBarChart()

   //2. Wordcloud
   List(
   "how much wood would a wood chuck chuck if a wood chuck could chuck wood", 
   "a wood chuck would chuck as much wood as a wood chuck could chuck if a wood chuck could chuck wood"
   ).plotWordcloud()
   

   // Check a vega lite viz
   SimpleBarChartLite(List(viz.Utils.fillDiv, spec => spec("title") = "Got Viz?" ))

   // Check a vega viz
   BarChart(List(viz.Utils.fillDiv))   

   // Custom viz
   SunburstDrag(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl))

   // With fixed URLs
   CirclePacking(
    List(viz.Utils.fixDefaultDataUrl, viz.Utils.fillDiv)
   )