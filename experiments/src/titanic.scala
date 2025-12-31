package experiments

import io.github.quafadas.table.{*, given}

@main def titanic =
  val data = CSV.resource("titanic.csv").toVector

  val ages = data.collect{
    case r if r.Age.isDefined => r.Age.get
  }

  println(ages.sum / ages.length.toDouble)



