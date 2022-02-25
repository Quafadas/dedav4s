# Working with a chart

```scala mdoc:invisible:reset
import viz.PlotTargets.doNothing
import viz.extensions.*
```
We'll want to manipulate the underlaying JSON specification, we'll need a ```Seq[ujson.Value => Unit]```, and the vega-lite example line chart. 

As always... lean into vega;

![The Vega Editor](../assets/vegaEditor.png)

Ultimately, our aim might be to plot a line chart with our own data, for example with this datastructure

```scala mdoc
import java.time.LocalDate
case class TimeSeries(series: Seq[(LocalDate, Double)])
val ts = TimeSeries(
        Seq(            
            (LocalDate.of(2021,1,1), 0.2), 
            (LocalDate.of(2021,6,1), 20),
            (LocalDate.now(), 5.5), 
        )
    )
```

## Adding a title
To start simple, let's add a title modifier. 

I'm writing out the types here in the hopes of being helpful. It looks harder than it is... After you've done it twice it gets easy. 
```scala mdoc
import viz.vega.plots.LineChartLite
import viz.Utils
val addTitle : ujson.Value => Unit = 
    (spec:ujson.Value) => spec("title") = "A Timeseries"
LineChartLite(
    Seq(
        addTitle, 
        Utils.fixDefaultDataUrl 
    )
)
```

```scala mdoc:vegaspec:lineChartBadMods
LineChartLite(Seq(addTitle, Utils.fixDefaultDataUrl ))
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("lineChartBadMods", node,0)
```
But there are a couple of things which are messy about our modification;
1. We've hardcoded the title
2. the anonymous function display is very anonymous. 

Let's have another go. With a little more ceremony, we have something that looks reasonable afterwards.

## Better title modifier

```scala mdoc
def addTitleB(in:String): ujson.Value => Unit = new((ujson.Value => Unit)) {
    override def toString = s"set title to be $in"
    def apply(spec: ujson.Value) = spec("title") = in
 }
LineChartLite(Seq(addTitleB("Much better"), Utils.fixDefaultDataUrl ))
```

```scala mdoc:vegaspec:lineChartLiteWithUrl
LineChartLite(Seq(addTitleB("Much better"), Utils.fixDefaultDataUrl ))
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("lineChartLiteWithUrl", node,0)
```
At this point, i think it's clear how we're going to deal with piping in the data - the same way as we injected a title

## Piping in the data

```scala mdoc
def addData(in: TimeSeries) = new (ujson.Value => Unit) {
    override def toString = "pipe in data" 
    def apply(spec: ujson.Value) =    
        val data = in.series.sortBy(_._1)
            .map(
                point => 
                    ujson.Obj(
                        "date" -> point._1.toString(), 
                        "price" -> point._2
                    )
                )
        spec("data") = ujson.Obj("values" -> data)
        spec.obj.remove("transform")
}
LineChartLite(Seq(addTitleB("Now with data"), addData(ts) ))
```

```scala mdoc:vegaspec:lineChartWithData
LineChartLite(Seq(addTitleB("Now with data!"), addData(ts) ))
```
```scala mdoc:js:invisible
viz.doc.showJsDocs("lineChartWithData", node,0)
```

Generally, I find that the best "workflow", is to pump the data into the spec and plot it. It usually shows up blank. Open it up in the vega editor and fix it. It's then easy to backport the modification into scala. 

# Conclusion
There's nothing that says 

1. Your plot can't be a method defined on some Timeseries class itself. That's an obvious and trivial next step.
1. You have to own the data structure - have a look at the example on the homepage. That works through an extension method defined on ```Numeric[Iterable]```

Which means you can "interface" plotting on datatypes of interest to you. I found this to be a powerful idea