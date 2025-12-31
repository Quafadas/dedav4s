package experiments

import io.github.quafadas.table.{*, given}

import viz.vega.VegaSpec
import viz.vega.plots.*
import viz.NtCirce.given
import viz.macros.*
import viz.Plottable.*
import viz.PlotTargets.desktopBrowser
import io.circe.syntax.*
import io.circe.Json


@main def titanic =
  val data = CSV.resource("titanic.csv").toVector

  val ages = data.collect{
    case r if r.Age.isDefined && r.Sex == "female" => (age = r.Age.get)
  }



  val histogram = VegaPlot.fromResource("histogram.vl.json")

  histogram.plot(
      _.data.values := ages.asJson,
      _.title := "Age Distribution of female passengers",
      _.encoding.x.field := "age",
      _.encoding.x.bin.step := 5
    )



