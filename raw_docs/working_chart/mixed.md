# Mixed typesafe / mutable

## Start mutable and add a little safety
The "mutuable" approach combines the previous two ideas.We could use a "typesafe" part of the DSL, to create the part we want, then pickle it to JSON and put it in our spec...

```scala mdoc:js
import viz.dsl.vega.*
import viz.vega.plots.{BarChart, given}
import viz.dsl.Conversion.u
import viz.js.makePlotTarget

val axisOrient : TitleOrientEnum = "top"
val newAxis : Axis= Axis(orient = axisOrient, scale = "xscale")

// for demonstration purposes
val asUjson : ujson.Value = newAxis.u
// the .u is an hacky extension method to move from circe json to ujson.Value that woin't be neede in future.

val chart = BarChart(
    List(
        viz.Utils.removeXAxis, 
        viz.Utils.fillDiv,
        spec => spec("axes") = spec("axes").arr :+ newAxis.u
    )
)
viz.js.showChartJs(chart, node)
```

Note that we get some typechecking.

```scala mdoc:fail
import viz.dsl.vega.*

val axisOrient : TitleOrientEnum = "Not an orientation" // This can one of the TitleOrientEnum values... visit in IDE for help.

```

We can also take this to greater extremes, and in a sort of magpie style, parse more complex parts of the spec.

## Shortcuts
```scala mdoc
import viz.dsl.vega.*
import io.circe._, io.circe.parser._

// Steal a part of the spec you want from an example. 
val copyPastedAxisFromExample = """
{ "orient": "top", "scale": "xscale" }
"""
val parsed :  Either[io.circe.Error, viz.dsl.vega.Axis] = decode[Axis](copyPastedAxisFromExample)

```

```scala mdoc
val parsedU : ujson.Value = ujson.read(copyPastedAxisFromExample)
```

You could then use either strategy to insert into a spec, depending on whether you're starting from the "mutable" or "typesafe" approach. 

```scala mdoc:js
import viz.dsl.vega.*
import viz.vega.plots.{BarChart, given}
import viz.dsl.Conversion.u
import viz.js.makePlotTarget

val copyPastedAxisFromExample = """{ "orient": "top", "scale": "xscale" }"""
val parsedU = ujson.read(copyPastedAxisFromExample)

val chart = BarChart(
    List(        
        viz.Utils.fillDiv,
        spec => spec("axes") = parsedU // overwrites the entire exes property with our single weird top axis.
    )
)
viz.js.showChartJs(chart, node)
```

# Discussion
This is my clearly favoured strategy. Start with a chart which works, construct "typesafe" modifier for the parts I want to change, and apply them to the chart. I have found this to be effective. 

