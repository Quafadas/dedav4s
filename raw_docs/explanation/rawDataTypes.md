
## "Raw" Data & Extension Methods

The idea here, is that "raw datatypes" have some unambiguous visualisation which is relatively common to want to plot. Pie charts, bar charts and the like, which are always going to look very similar to the examples on the vega website, and come from a simple datastructure. We want to be able to plot these as quickly as possible. 

We achieve this through scala 3's extension methods have a look in the ```viz.extensions``` package for ideas. 

```scala mdoc
import viz.PlotTargets.desktopBrowser
import viz.extensions.*
```

```scala mdoc:invisible:reset
import viz.PlotTargets.doNothing
import viz.extensions.*
```

### Labelled bar chart
```scala
List(("A", 4),("B", 6),("C", -1)).plotBarChart(List())
```
```scala mdoc:vegaspec:labelledBarChart
List(("A", 4),("B", 6),("C", -1)).plotBarChart(List())
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("labelledBarChart", node, 0)
```

### Bar chart
```scala
(1 to 5).plotBarChart()
```
```scala mdoc:vegaspec:unlballedBarChart
(1 to 5).plotBarChart()
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("unlballedBarChart", node,0)
```

### Pie chart
```scala
(1 to 5).plotPieChart()
```
```scala mdoc:vegaspec:pieChart
(1 to 5).plotPieChart()
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("pieChart", node,0)
```
### Pie chart with labels... 
```scala
(1 to 5).map(i => (scala.util.Random.nextString(5), i)).plotPieChart(List())
```
```scala mdoc:vegaspec:pieChartLabelled
(1 to 5).map(i => (scala.util.Random.nextString(5), i)).plotPieChart(List())
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("pieChartLabelled", node,0)
```

### Word cloud
```scala
List(
   "how much wood would a wood chuck chuck if a wood chuck could chuck wood", 
   "a wood chuck would chuck as much wood as a wood chuck could chuck if a wood chuck could chuck wood"
).plotWordcloud()
```
```scala mdoc:vegaspec:wordcloud
List(
   "how much wood would a wood chuck chuck if a wood chuck could chuck wood", 
   "a wood chuck would chuck as much wood as a wood chuck could chuck if a wood chuck could chuck wood"
).plotWordcloud()
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("wordcloud", node)
```


### Line Chart
```scala
List(1,5,3,15,7,8).plotLineChart()
```
```scala mdoc:vegaspec:lineChart
List(1,5,3,15,7,8).plotLineChart()
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("lineChart", node, 0)
```
### Dot Plot
```scala
List(1,11,1,2,3,4,4,4,4,4,5,6,8,8,9,8).plotDotPlot()
```
```scala mdoc:vegaspec:dotplot
List(1,11,1,2,3,4,4,4,4,4,5,6,8,8,9,8).plotDotPlot()
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("dotplot", node,0)
```
### Scatter Plot
```scala
List((1.0,2.0),(3.0,4.0),(0.5 , 5.0),(3.14159, 1.0)).plotScatter()
```
```scala mdoc:vegaspec:scatterPlot
List((1.0,2.0),(3.0,4.0),(0.5 , 5.0),(3.14159, 1.0)).plotScatter()
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("scatterPlot", node,0)
```
### Regression
```scala
List((1.0,2.0),(3.0,4.0),(0.5 , 5.0),(3.14159, 1.0)).plotRegression()
```
```scala mdoc:vegaspec:regression
List((1.0,2.0),(3.0,4.0),(0.5 , 5.0),(3.14159, 1.0)).plotRegression()
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("regression", node, 25)
```