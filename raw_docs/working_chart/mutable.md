# Mutable Style

Our aim here is to start from some working example. Usually an example vega chart. 

We'll want to manipulate the underlaying JSON specification, to get what we want. To this we'll take a bunch of ```Seq[ujson.Value => Unit]```, and feed them to the example chart. In the example below, a vega-lite example line chart. 

As always... lean into vega;

![The Vega Editor](../assets/vegaEditor.png)

Ultimately, our aim might be to plot a line chart with our own data.

## Adding a title
To start simple, let's add a title modifier. 

I'm writing out the types here in the hopes of being helpful. It looks harder than it is... After you've done it twice it gets easy. REPL is your friend :-).

```scala mdoc:js
import viz.vega.plots.{LineChartLite, given}
import org.scalajs.dom.html.Div
import viz.doc.makePlotTarget

val child : Div = makePlotTarget(node, 50)

val addTitle : ujson.Value => Unit = 
    (spec:ujson.Value) => spec("title") = "A Timeseries"

/*LineChartLite(
    Seq(
        addTitle, 
        viz.Utils.fixDefaultDataUrl, 
        viz.Utils.fillDiv 
    )
)*/
```

But there are a couple of things which are messy about our modification;
1. We've hardcoded the title
2. the anonymous function display is very anonymous. 

Let's have another go. With a little more ceremony, we have something that looks easier afterwards.

## Better title modifier

```scala mdoc:js
import viz.vega.plots.LineChartLite
import org.scalajs.dom.html.Div
import viz.doc.makePlotTarget

val child : Div = makePlotTarget(node, 50)

def addTitleB(in:String): ujson.Value => Unit = new((ujson.Value => Unit)) {
    override def toString = s"set title to be $in"
    def apply(spec: ujson.Value) = spec("title") = in
 }
LineChartLite(
    Seq(
        addTitleB("Much better"), 
        viz.Utils.fixDefaultDataUrl,
        viz.Utils.fillDiv 
    )
)(using viz.PlotTargets.doNothing)
```

At this point, i think it's clear how we're going to deal with piping in the data - the same way as we injected a title

## Piping in the data

```scala mdoc:js
import viz.vega.plots.LineChartLite
import org.scalajs.dom.html.Div
import viz.doc.makePlotTarget
import java.time.LocalDate
case class TimeSeries(series: Seq[(LocalDate, Double)])
val ts = TimeSeries(
        Seq(            
            (LocalDate.of(2021,1,1), 0.2), 
            (LocalDate.of(2021,6,1), 20),
            (LocalDate.now(), 5.5), 
        )
    )
val child : Div = makePlotTarget(node, 50)

def addTitleB(in:String): ujson.Value => Unit = new((ujson.Value => Unit)) {
    override def toString = s"set title to be $in"
    def apply(spec: ujson.Value) = spec("title") = in
 }

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
LineChartLite(Seq(addTitleB("Now with data"), addData(ts),viz.Utils.fillDiv ))(using viz.PlotTargets.doNothing)
```

Generally, I find that the best "workflow", is to pump the data into the spec and plot it. It usually shows up blank. Open it up in the vega editor and fix it. It's then easy to backport the modification into scala. 

In the first instance, this looks like quite a bit of ceremony. What's important to remember, is that you can compose the modifiers... and ultimately, end up with your own library of them!

# Conclusion
Reading this back the example is weirdly contrived. Mostly when I'm working with an existing datatype, it already has a JSON serialisation strategy in place. You ought to be able to imagine how one could easily pipe it into the chart. 

I've found this style to be rather convenient in practise. 

There's nothing that says 

1. Your plot can't be a method defined on some Timeseries class itself. That's an obvious and trivial next step.
1. You have to own the data structure - have a look at the extension methods. The homepage works through an extension method defined on ```Numeric[Iterable]```

Which means you can "interface" plotting on datatypes of interest to you. I found this to be a powerful idea