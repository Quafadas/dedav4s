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

    Seq[ujson.Value => Unit]

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