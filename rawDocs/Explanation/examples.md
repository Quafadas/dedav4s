---
id: example
title: Examples
---
<head>
        <meta charset="utf-8" />
        <!-- Import Vega & Vega-Lite -->
        <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
        <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
        <!-- Import vega-embed -->
        <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
        <style>
		    div.viz {
                width: 25vmin;
                height:25vmin;
                style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"
            }
        </style>
</head>
Once you have your plot target in place, we're ready to plot some data. The idea of the library is to wrap vega by simply treating the spec as a JSON object.  

We can easily manipulate JSON objects using [ujson](https://www.lihaoyi.com/post/uJsonfastflexibleandintuitiveJSONforScala.html). 

I work with this library in 4 ways
1. I want to visualise some fairly raw dataset in a fashion which looks similar to one of the vega examples. Use scala to obtain data and pipe it directly into the spec
1. I want to see viz on some "self owned" datatype. Define an extension method on it... munge the data, pipe it into an example spec.
1. Spec has been modified enough that a list of modifiers is confusing. Extend the base spec class direct via a file or resource (see "Custom.scala"). Then pipe data into it.
1. In prod... don't use this library anymore - probably you have a webserver. Keep the spec under version control and use vega data loading capabilities to talk to the API providing data. 

Each "plot" is a case class which accepts a list of "modifiers". Each case class has the signature accepting a single argument of type; 

    Seq[ujson.Obj => Unit]

To add a title

    SimpleBarChartLite(List(spec => spec("title") = "Got Viz?"))

I use a small number of "helpers" enough that they are honoured with an implemetation in the library; 

    SunburstDrag(List(viz.Utils.fillDiv, viz.Utils.fixDefaultDataUrl))

```scala mdoc:invisible
import viz.PlotTargets.doNothing
import viz.vega.extensions.*
```
## "Raw" Data

The idea here, is that "raw datatypes" have some "unambiguous" visualisation which is relatively common to want to plot. Pie charts, bar charts and the like, which are always going to look very similar to the examples on the vega website. We want to be able to plot these as quickly as possible. 

### Labelled bar chart
```scala
List(("A", 4),("B", 6),("C", -1)).plotBarChart()
```
```scala mdoc:vegaplot
List(("A", 4),("B", 6),("C", -1)).plotBarChart()
```

### Bar chart
```scala
val secondChart = (1 to 5).plotBarChart()
```
```scala mdoc:vegaplot
val secondChart = (1 to 5).plotBarChart()
```

### Word cloud
```scala
List(
   "how much wood would a wood chuck chuck if a wood chuck could chuck wood", 
   "a wood chuck would chuck as much wood as a wood chuck could chuck if a wood chuck could chuck wood"
).plotWordcloud()
```
```scala mdoc:vegaplot
List(
   "how much wood would a wood chuck chuck if a wood chuck could chuck wood", 
   "a wood chuck would chuck as much wood as a wood chuck could chuck if a wood chuck could chuck wood"
).plotWordcloud()
```

## "Spec Customisation"
The core promise of the library, is that it wraps Vega. It makes one further step, by making the "examples" on the vega website, easy to plot.

```scala mdoc
viz.vega.plots.LineChartLite(List(viz.Utils.fixDefaultDataUrl))
```
```scala mdoc:vegaplot
viz.vega.plots.LineChartLite(List(viz.Utils.fixDefaultDataUrl))
```
As we've changed the home of the chart (which no longer is on the vega lite examples homepage), we also need to adapt it's data url to point to the right place, otherwise the chart will be blank. This is our hint on how we're going to manage minor modifications to plots. 

Here, we have the line chart example from vega lite. ```viz.vega.plots.xxx``` contains _all_ the examples on the vega, and vega-lite websites. vega-lite charts have "lite" appended.

Someone was apparently crazy enough to implement pacman in vega. So, for your gaming pleasure.

```scala mdoc
viz.vega.plots.Pacman()
```
```scala mdoc:vegaplot
viz.vega.plots.Pacman()
```
More seriously though, this is not helpful. We need a way to customise charts, which we'll provide through a list of "modifiers". A very common customisation, is to want to display your own data (*gasp!), from the scala runtime, in the chart. Conceptually this is no different from all the other modification we will make - just change the JSON object.

### Line bar chart

Let's imagine we have some "custom" datatype. For example a 

```scala mdoc
import java.time.LocalDate
case class TimeSeries(series: Seq[(LocalDate, Double)])
val ts = TimeSeries(Seq((LocalDate.now(), 1.5), (LocalDate.of(2021,1,1), 0.2), (LocalDate.of(2021,6,1), 20)))
```
That we'd like to represent as a line chart. We'll do that with a series of ```ujson.Obj => Unit```. 

For our first example, let's add a title. I'm writing out the types here in the hopes of being helpful. It looks harder than it is... After you've done it twice it gets easy. 
```scala mdoc
import viz.vega.plots.LineChartLite
import viz.Utils
val addTitle : ujson.Obj => Unit = (spec:ujson.Obj) => spec("title") = "A Timeseries"
LineChartLite(Seq(addTitle, Utils.fixDefaultDataUrl ))
```

```scala mdoc:vegaplot
LineChartLite(Seq(addTitle, Utils.fixDefaultDataUrl ))
```
So there are a couple of things which are messy;
1. We've hardcoded the title
2. the anonymous function display is very anonymous, no idea what that lambda did. 

Let's have another go.

```scala mdoc
def addTitleB(in:String): ujson.Obj => Unit = new((ujson.Obj => Unit)) {
    override def toString = s"set title to be $in"
    def apply(spec: ujson.Obj) = spec("title") = in
 }
LineChartLite(Seq(addTitleB("Much better"), Utils.fixDefaultDataUrl ))
```

```scala mdoc:vegaplot
LineChartLite(Seq(addTitleB("Much better"), Utils.fixDefaultDataUrl ))
```
At this point, i think it's clear how we're going to deal with piping in the data. Same way as we injected a title

```scala mdoc
def addData(in: TimeSeries) = new (ujson.Obj => Unit) {
    override def toString = "pipe in data" 
    def apply(spec: ujson.Obj) =    
        val data = in.series.sortBy(_._1).map(point => ujson.Obj("date" -> point._1.toString(), "price" -> point._2))
        spec("data") = ujson.Obj("values" -> data)
        spec.obj.remove("transform")
}
LineChartLite(Seq(addTitleB("Much better"), addData(ts) ))
```

```scala mdoc:vegaplot
LineChartLite(Seq(addTitleB("Much better"), addData(ts) ))
```