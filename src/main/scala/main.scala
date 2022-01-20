
import viz.vega.plots.*

//import viz.PlotTargets.desktopBrowser
import viz.PlotTargets.desktopBrowser
import viz.extensions.*

@main
def Main(args: String*): Unit =
   
   // Check extension methods
   // 1. Numeric Iterable
   (1 to 10).plotBarChart()
/*    (1 to 10).plotPieChart()
   List(1,5,3,15,7,8).plotLineChart()
   List(1,11,1,2,3,4,4,4,4,4,5,6,8,8,9,8).plotDotPlot()
   List((1.0,2.0),(3.0,4.0),(0.5 , 5.0),(3.14159, 1.0)).plotScatter()
   List((1.0,2.0),(3.0,4.0),(0.5 , 5.0),(3.14159, 1.0)).plotRegression()

   //2. Wordcloud
   List(
   "how much wood would a wood chuck chuck if a wood chuck could chuck wood", 
   "a wood chuck would chuck as much wood as a wood chuck could chuck if a wood chuck could chuck wood"
   ).plotWordcloud()
   

   // Check a vega lite viz
   SimpleBarChartLite(List(viz.Utils.fillDiv, spec => spec("title") = "Got Viz?" ))

   // Check a vega viz
   BarChart(List(viz.Utils.fillDiv))      
   List(("A", 4),("B", 6),("C", -1)).plotBarChart(List())

   // Custom viz
   SunburstDrag(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl))

   // With fixed URLs
   CirclePacking(
    List(viz.Utils.fixDefaultDataUrl, viz.Utils.fillDiv)
   ) */