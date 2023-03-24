# Mixed typesafe / mutable

## Start mutable and add a little safety
The "mutuable" approach uses `ujson.Value` to represent a chart. We could use a "typesafe" part of the DSL, to mutate the part we want... then turn into ujson.

```scala mdoc:js
import viz.dsl.vega.*
import viz.vega.plots.BarChart
import viz.dsl.Conversion.u
import viz.doc.makePlotTarget

val axisOrient : TitleOrientEnum = "top"
val newAxis : Axis= Axis(orient = axisOrient, scale = "xscale")

// for demonstration purposes
val asUjson : ujson.Value = newAxis.u
// the .u is an hacky extension method to move from circe json to ujson.Value

BarChart(
    List(
        viz.Utils.removeXAxis, 
        viz.Utils.fillDiv,
        spec => spec("axes") = spec("axes").arr :+ newAxis.u
    )
)
```

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