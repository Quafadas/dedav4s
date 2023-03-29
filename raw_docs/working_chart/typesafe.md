# The DSL

The DSL is generated via [quicktype](https://quicktype.io), from the Vega Schema. 

This is a simple bar chart.

```scala mdoc:js
import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import viz.dsl.DslSpec
import viz.PlotTargets.doNothing
import io.circe._

val someData : InlineDataset = Seq(
    Map("a" -> Some(Json.fromString("A")), "b" -> Some(Json.fromInt(20))),
    Map("a" -> Some(Json.fromString("Next")), "b" -> Some(Json.fromInt(25))),
    Map("a" -> Some(Json.fromString("embiggen")), "b" -> Some(Json.fromInt(5)))
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
                    field = Some("a".asInstanceOf[Field]), 
                    `type` = Some("nominal".asInstanceOf[viz.dsl.vegaLite.Type]), 
                    axis = Some(Axis(labelAngle = Some(45.asInstanceOf[LabelAngle]))),
                )
            ),
            y = Some(
                YClass(
                    field = Some("b".asInstanceOf[Field]), 
                    `type` = Some("quantitative".asInstanceOf[viz.dsl.vegaLite.Type]),                     
                )
            )
        )
    )
)
viz.doc.showChartJs(DslSpec(theChart), node)
```

That is... a lot of work though. Writing this out by hand would be formidably hard. Honestly, no one is going to do that. New plan... 

Cheat! We have a strongly typed representation of the schema, so why not, once again... start from the examples. 

# Simpler strategy

And seeing as we have a case class... copy...

```scala mdoc:js
import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import viz.dsl.DslSpec
import viz.PlotTargets.doNothing
import viz.vega.plots.*
import io.circe._

val teehee = SpecUrl.SimpleBarChartLite.toDsl() // Cunningly parse JSON here
val asDsl = teehee.toOption.get.asInstanceOf[VegaLiteDsl]
val someData : InlineDataset = Seq(
    Map("a" -> Some(Json.fromString("A")), "b" -> Some(Json.fromInt(20))),
    Map("a" -> Some(Json.fromString("Next")), "b" -> Some(Json.fromInt(25))),
    Map("a" -> Some(Json.fromString("embiggen")), "b" -> Some(Json.fromInt(5)))
)

val cheatingCanBeGood = asDsl.copy(
    data = Some(URLData(values = Some(someData))),
    width = Some("container"), 
    height = Some("container")
)

viz.doc.showChartJs(DslSpec(cheatingCanBeGood), node)

```

To give an idea of the types, let's run this again in scala JVM

```scala mdoc
import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import viz.dsl.DslSpec
import viz.PlotTargets.doNothing
import viz.vega.plots.*
import io.circe._

val teehee = SpecUrl.SimpleBarChartLite.toDsl() // Cunningly parse JSON here
val asDsl = teehee.toOption.get.asInstanceOf[VegaLiteDsl]
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
Typesafety is nice to have in the sense that it removes entire categories of "unplottable" states. However, many charts that typecheck, will not make sense. Given the flexibility of the vega schema, typesafety has a high mental burden. 