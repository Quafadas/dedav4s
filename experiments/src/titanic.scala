package experiments

import io.github.quafadas.table.{*, given}
import io.github.quafadas.plots.SetupVega.{*, given}
import io.circe.syntax.*
import viz.PlotTargets.websocket

@main def titanic =
  given port:Int = 8085
  val data = CSV.resource("titanic.csv").toVector

  val filterSex = "female"

  val ages = data.collect {
    case r if r.Age.isDefined && r.Sex == filterSex => (age = r.Age.get)
  }

  val histogram = VegaPlot.fromResource("histogram.vl.json")

  histogram.plot(
    _.data.values := ages.asJson,
    _.title := s"Age Distribution of $filterSex passengers",
    _.encoding.x.field := "age",
    _.encoding.x.bin.step := 5
  )



end titanic
