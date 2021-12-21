# dedav4s
Declarative Data Viz 4 Scala. This wraps Vega and Vega Lite

# In a (scala 3) ammonite terminal 

    import $ivy.`io.github.quafadas::dedav4s:0.0.5-SNAPSHOT`
    import viz.PlotTargets.desktopBrowser
    import viz.vega.extensions.*

    (1 to 10).plotBarCart()

Will also work in any repl environment, as long as... 

# What just happened? What are the constraints? 
plotBarChart is an [extension method](https://dotty.epfl.ch/docs/reference/contextual/extension-methods.html) on numeric iterables. It deals with some light admin before setting up a (Bar Chart)[https://vega.github.io/vega/examples/bar-chart/]. 

The "setup" assumes one is able to access this url - it reads the spec from;

    https://vega.github.io/vega/examples/bar-chart.vg.json

You could instantiate this case class directly by calling

    viz.vega.plots.BarChart()

Which would use the default data. Assuming you have the "viz.PlotTargets.desktopBrowser" target in scope.

The library writes a (temporary) file, assuming that

    java.io.File.createTempFile() 

Is available. That file assumes that you have an internet connection, and can resolve 

    <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>

Finally, we assume the existence of a 

    java.awt.Desktop

Which has a browser available... 

    java.awt.Desktop.browse()

Basically, a great big giant hack :-)! In exchange for that, you get convienient access to the expressive plotting power of Vega, and Vega lite. 

# Other targets
See the PlotTarget file. The ones I use are implemented - but more could be added easily enough. The websockets one is cool.

# Which plots are available?
The library exposes the (vega examples)[https://vega.github.io/vega/examples/] and (vega lite examples)[https://vega.github.io/vega-lite/examples/]. The class names correspond to the title of the charts with some special characters removed. 

A tiny number of "custom plots" are included in the Custom.scala file.

# But how do I? 
The idea of the library is to wrap vega by simply treating the spec as a JSON object.  

As "spec" is a JSON object, we can easily manipulate JSON objects using [ujson](https://www.lihaoyi.com/post/uJsonfastflexibleandintuitiveJSONforScala.html). Each "plot" is a case class which accepts a list of modifiers with the signature; 

    Seq[ujson.Value => Unit]

To add a title

    SimpleBarChartLite(List(spec => spec("title") = "Got Viz?"))

I use a small number of helpers enough that they are included; 

    SimpleBarChartLite(List(viz.Utils.fillDiv, spec => spec("title") = "Got Viz?" ))

I work with this library in 3 ways
1. I want to see viz on some "self owned" datatype. Define an extension method on it...
2. I want to visualise some fairly raw dataset in a fashion which looks similar to one of the vega examples. Use scala to obtain data and pipe it directly into the spec
3. Spec has been modified enough that a list of modifiers is confusing. Extend the base spec class direct via a file or resource (see "Custom.scala").

In general, lean into Vega. Debug your specs using vega, then pipe the data you want into it through scala.

Video to follow... maybe... 

# Prior Art
If you are interested in plotting things in scala, you may find any or all of the below libraries of interest. I obviously felt strongly enough to attempt to create my own version... but YMMV and here are other attempts at similar paradigms. In each case I either found they were unmaintained or didn't suit my way of working. They are none the less valuable resources;

- https://github.com/alexarchambault/plotly-scala
- https://github.com/vegas-viz/Vegas
- https://index.scala-lang.org/coxautomotivedatasolutions/vegalite4s/vegalite4s/0.4?target=_2.12
- https://index.scala-lang.org/quantifind/wisp/wisp/0.0.4?target=_2.10
- https://cibotech.github.io/evilplot/
- https://github.com/scalanlp/breeze-viz
