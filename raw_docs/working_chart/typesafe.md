# The DSL

The DSL is generated via [quicktype](https://quicktype.io), from the Vega Schema.

This is a simple bar chart.

```scala mdoc:js
import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import viz.vega.plots.given
import io.circe._
import cats.syntax.option.*

  val someData: InlineDataset = Seq(
    Map("x" -> Some(Json.fromString("A")), "y" -> Some(Json.fromInt(20))),
    Map("x" -> Some(Json.fromString("Next")), "y" -> Some(Json.fromInt(25))),
    Map("x" -> Some(Json.fromString("embiggen")), "y" -> Some(Json.fromInt(5)))
  )

  val theChart = VegaLiteDsl(
    `$schema` = Some("https://vega.github.io/schema/vega-lite/v5.json"),
    description = Some("A simple bar chart that is kinda statically typed"),
    data = Some(
      URLData(
        values = Some(
          someData
        )
      )
    ),
    height = Some("container"),
    width = Some("container"),
    mark = Some("bar".asInstanceOf[AnyMark]),
    encoding = Some(
      EdEncoding(
        x = Some(
          XClass(
            field = Some("x"),
            `type` = Some(Type.nominal),
            axis = Some(Axis(labelAngle = 45.0.some))
          )
        ),
        y = Some(
          YClass(
            field = Some("y"),
            `type` = Some(Type.quantitative),
          )
        )
      )
    )
  )
viz.js.showChartJs(theChart.plot, node, 50)
```

That is... a lot of work though. Writing this out by hand would be formidably hard. Honestly, no one is going to do that. New plan...

Cheat! We have a strongly typed representation of the schema, so why not, once again... start from the examples.

# Simpler strategy

And seeing as we have a case class... copy... this uses Vega instead of VegaLite.

```scala mdoc:js
import viz.dsl.DslSpec
import viz.PlotTargets.doNothing
import viz.vega.plots.*
import io.circe.syntax.*
import io.circe.*
import viz.dsl.vega.*
import cats.syntax.option.*

// Let's fetch the example... and parse it.
val asDsl : VegaDsl = io.circe.parser.decode[VegaDsl](viz.vega.plots.BarChart().spec).fold(throw _, identity)

// We make up a case class that happens to have category and amount fields
case class BarPlotData(category: String, amount: Double) derives Encoder.AsObject, Decoder

val myData = Data(
    name = "table",
    values = Seq(
      BarPlotData("a", 28),
      BarPlotData("b", 55),
      BarPlotData("c", 43),
    ).asJson.some
  )

// And now, this plot is "typesafe"
val cheatingCanBeGood = asDsl.copy(
    data = Seq(myData).some
)

viz.js.showChartJs(DslSpec(cheatingCanBeGood), node, 50)

```

To give an idea of the types, let's run this again in scala JVM

```scala mdoc
import viz.dsl.vegaLite.*
import viz.PlotTargets.doNothing
import viz.vega.plots.*
import io.circe.*

val asDsl : VegaLiteDsl = io.circe.parser.decode[VegaLiteDsl](viz.vega.plots.SimpleBarChartLite().spec).fold(throw _, identity)

val someData : InlineDataset = Seq(
    Map("a" -> Some(Json.fromString("A")), "b" -> Some(Json.fromInt(20))),
    Map("a" -> Some(Json.fromString("Next")), "b" -> Some(Json.fromInt(25))),
    Map("a" -> Some(Json.fromString("embiggen")), "b" -> Some(Json.fromInt(5)))
)

asDsl.copy(
    data = Some(URLData(values = Some(someData))),
    width = Some("container"),
    height = Some("container")
)
```

# Discussion
Typesafety is nice to have in the sense that it removes entire categories of "unplottable" states.

However, many charts that typecheck, will not b _visually correct_. Given the flexibility of the vega schema, typesafety has a high mental burden and imposes an terrific maintenance and legibility burden.

My view has evolved to be that plotting is a domain similar to CSS - in which it is the _visual_ outcome which matters.

Typesafety not only doesn't help, it actively impedes the experience by imposing a heavy mental burden surrounding the "types". I do not recommend this approach. It is seperated into a module, which is essentially "deprecated" and unmaintained.