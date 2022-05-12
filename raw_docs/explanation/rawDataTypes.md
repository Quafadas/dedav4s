
## "Raw" Data & Extension Methods

The idea here, is that "raw datatypes" have some unambiguous visualisation which is relatively common to want to plot. Pie charts, bar charts and the like, which are always going to look very similar to the examples on the vega website, and come from a simple datastructure. We want to be able to plot these as quickly as possible. 

We achieve this through scala 3's extension methods have a look in the ```viz.extensions``` package for ideas. 

Currently, extension methods are not availalbe in scala JS, so you'd have to copy paste these into a repl in order to get the idea.

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

### Bar chart
```scala
(1 to 5).plotBarChart()
```

### Pie chart
```scala
(1 to 5).plotPieChart()
```

### Pie chart with labels... 
```scala
(1 to 5).map(i => (scala.util.Random.nextString(5), i)).plotPieChart(List())
```
### Word cloud
```scala
List(
   "how much wood would a wood chuck chuck if a wood chuck could chuck wood", 
   "a wood chuck would chuck as much wood as a wood chuck could chuck if a wood chuck could chuck wood"
).plotWordcloud()
```

### Line Chart
```scala
List(1,5,3,15,7,8).plotLineChart()
```
### Dot Plot
```scala
List(1,11,1,2,3,4,4,4,4,4,5,6,8,8,9,8).plotDotPlot()
```
### Scatter Plot
```scala
List((1.0,2.0),(3.0,4.0),(0.5 , 5.0),(3.14159, 1.0)).plotScatter()
```
### Regression
```scala
import viz.extensions.jvm.*
List((1.0,2.0),(3.0,4.0),(0.5 , 5.0),(3.14159, 1.0)).plotRegression()
```
