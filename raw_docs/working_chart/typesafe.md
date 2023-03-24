# The DSL

The DSL is generated via [quicktype](https://quicktype.io).

```scala mdoc:js
import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import org.scalajs.dom.html.Div
import viz.doc.makePlotTarget
import io.circe._

val child : Div = makePlotTarget(node, 50)

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
```

if you were in a repl, the last line would be:

```
theChart.plot
```

That is... a lot of work though. Let's cheat.

# Simpler strategy

Parse the example spec from source... then copy the case class.

```scala mdoc:js
import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import org.scalajs.dom.html.Div
import viz.doc.makePlotTarget
import viz.vega.plots.*
import io.circe._

val teehee = SpecUrl.SimpleBarChartLite.toDsl()
val asDsl = teehee.toOption.get.asInstanceOf[VegaLiteDsl]
val someData : InlineDataset = Seq(
    Map("a" -> Some(Json.fromString("A")), "b" -> Some(Json.fromInt(20))),
    Map("a" -> Some(Json.fromString("Next")), "b" -> Some(Json.fromInt(25))),
    Map("a" -> Some(Json.fromString("embiggen")), "b" -> Some(Json.fromInt(5)))
)

asDsl.copy(
    data = Some(URLData(values = Some(someData))),
    width = Some("container")
)

```