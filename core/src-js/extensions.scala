/*
 * Copyright 2023 quafadas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viz.js

import scala.util.Random
import scala.annotation.targetName
import ujson.Value

import math.Numeric.Implicits.infixNumericOps
import viz.vega.plots.*
import upickle.default.Writer
import NamedTuple.NamedTuple
import viz.mod
import scala.deriving.Mirror

package object extensions:

  // SpecUrl.values

  extension [K <: Tuple, V <: Tuple](
      data: Seq[NamedTuple[K, V]]
  )
    @targetName("recordPlotBarChart")
    def barSpec(mods: Seq[ujson.Value => Unit])(using rw: Writer[Seq[NamedTuple[K, V]]]) =
      SpecUrl.BarChart.jsonSpec.mod(
        List((spec: Value) => spec("data")(0)("values") = upickle.default.writeJs(data)) ++ mods
      )

  end extension

  extension [D <: BarPlotDataEntry: Writer](data: Seq[D])
    def barSpec(mods: Seq[ujson.Value => Unit]) =
      SpecUrl.BarChart.jsonSpec.mod(
        List((spec: Value) => spec("data")(0)("values") = upickle.default.writeJs(data)) ++ mods
      )

  end extension

  extension [D <: PiePlotDataEntry: Writer](
      data: Seq[D]
  )
    def pieSpec(mods: Seq[ujson.Value => Unit] = List()) =
      val mods2 = List((spec: Value) => spec("data")(0)("values") = upickle.default.writeJs(data)) ++ mods
      SpecUrl.PieChart.jsonSpec.mod(mods2)

  end extension

  extension [A](data: Seq[A])

    def barSpec(fct: A => BarPlotDataEntry)(mods: Seq[ujson.Value => Unit]) =
      val chartData = data.map(d =>
        val tmp = fct(d)
        ujson.Obj("category" -> tmp.category, "amount" -> tmp.amount)
      )
      SpecUrl.BarChart.jsonSpec.mod(
        List((spec: Value) => spec("data")(0)("values") = chartData) ++ mods
      )
    end barSpec

    def pieSpec(fct: A => PiePlotDataEntry)(mods: Seq[ujson.Value => Unit]) =
      val chartData = data.map(d =>
        val tmp = fct(d)
        ujson.Obj("id" -> tmp.id, "field" -> tmp.field)
      )
      SpecUrl.PieChart.jsonSpec.mod(
        List((spec: Value) => spec("data")(0)("values") = chartData) ++ mods
      )
    end pieSpec

  end extension

  extension [T: Numeric](l: Seq[T])
    def barSpec(mods: Seq[ujson.Value => Unit] = List()) =
      val labelToNotShow =
        for number <- l
        yield ujson.Obj(
          "category" -> Random.alphanumeric.take(8).mkString(""),
          "amount" -> number.toDouble
        )
      SpecUrl.BarChart.jsonSpec.mod(
        List(
          // viz.Utils.fillDiv,
          (spec: Value) => spec("data")(0)("values") = labelToNotShow,
          viz.Utils.removeXAxis
        ) ++ mods
      )
    end barSpec

    def pieSpec(mods: Seq[ujson.Value => Unit]) =
      val labelToNotShow =
        for number <- l
        yield ujson.Obj(
          "id" -> Random.alphanumeric.take(8).mkString(""),
          "field" -> number.toDouble
        )
      SpecUrl.PieChart.jsonSpec.mod(
        List(
          // viz.Utils.fillDiv,
          (spec: Value) => spec("data")(0)("values") = labelToNotShow
        ) ++ mods
      )
    end pieSpec

    def lineChartSpec(mods: Seq[ujson.Value => Unit] = List()) =
      val labelToNotShow =
        for (number, i) <- l.zipWithIndex
        yield ujson.Obj(
          "x" -> i,
          "y" -> number.toDouble
        )
      SpecUrl.LineChart.jsonSpec.mod(
        List((spec: Value) => spec("data")(0)("values") = labelToNotShow) ++ mods
      )
    end lineChartSpec
  end extension

  extension [T: Numeric](
      l: Iterable[(String, T)]
  )
    @targetName("plotBarChartWithLabels")
    def lineChartSpec(mods: Seq[ujson.Value => Unit]) =
      val labelled =
        for (label, number) <- l
        yield ujson.Obj(
          "x" -> label,
          "y" -> number.toDouble
        )
      SpecUrl.LineChart.jsonSpec.mod(
        List((spec: Value) =>
          spec("data")(0)("values") = labelled
          // viz.Utils.fillDiv
        ) ++ mods
      )
    end lineChartSpec

    @targetName("plotLineChartWithLabels")
    def pieSpec(mods: Seq[ujson.Value => Unit]) =
      val labelled =
        for (label, number) <- l
        yield ujson.Obj(
          "category" -> label,
          "amount" -> number.toDouble
        )
      SpecUrl.BarChart.jsonSpec.mod(
        List((spec: Value) => spec("data")(0)("values") = labelled) ++ mods
      )
    end pieSpec

    @targetName("plotPieChartWithLabels")
    def pieSpec(mods: Seq[ujson.Value => Unit]) =
      val labelled =
        for (label, number) <- l
        yield ujson.Obj(
          "id" -> label,
          "field" -> number.toDouble
        )
      SpecUrl.PieChart.jsonSpec.mod(
        List(
          (spec: Value) => spec("data")(0)("values") = labelled,
          (spec: Value) => spec("height") = 400,
          (spec: Value) => spec("width") = 550,
          (spec: Value) => spec("legends") = ujson.Arr(ujson.Obj("fill" -> "color", "orient" -> "top-right")),
          (spec: Value) => spec("marks")(0)("encode")("enter")("x") = ujson.Obj("signal" -> "height / 2"),
          (spec: Value) => spec("marks")(0)("encode")("update")("outerRadius") = ujson.Obj("signal" -> "height / 2")
        ) ++ mods
      )
    end pieSpec

  end extension

  // extension [T: Numeric](l: Map[String, T])
  //   @targetName("plotBarChartFromMapWithLabels")
  //   def plotBarChart(mods: Seq[ujson.Value => Unit])(using rw: Writer[T]): ujson.Value = l.iterator.toSeq.barSpec(mods)

  //   def plotLineChart(mods: Seq[ujson.Value => Unit]): ujson.Value = l.iterator.toSeq.lineChartSpec(mods)

  //   def plotPieChart(mods: Seq[ujson.Value => Unit]): ujson.Value = l.iterator.toSeq.pieSpec(mods)
  // end extension

  extension [N1: Numeric, N2: Numeric](
      l: Iterable[(N1, N2)]
  )
    def scatterSpec(mods: Seq[ujson.Value => Unit] = List()): ujson.Value =
      val values = l.map { case (x, y) =>
        ujson.Obj("x" -> x.toDouble, "y" -> y.toDouble)
      }
      SpecUrl.ScatterPlot.jsonSpec.mod(
        List(
          (spec: Value) => spec("data") = ujson.Arr(ujson.Obj("name" -> "source", "values" -> values)),
          (spec: Value) =>
            spec.obj.remove("legends"); ()
          ,
          (spec: Value) => spec("axes")(0)("title") = "x",
          (spec: Value) => spec("axes")(1)("title") = "y",
          (spec: Value) => spec("marks")(0)("encode")("update")("x")("field") = "x",
          (spec: Value) => spec("scales")(0)("domain")("field") = "x",
          (spec: Value) => spec("scales")(1)("domain")("field") = "y",
          (spec: Value) =>
            spec("scales").arr.dropRightInPlace(1); ()
          ,
          (spec: Value) => spec("marks")(0)("encode")("update")("y")("field") = "y",
          (spec: Value) => spec("marks")(0)("encode")("update")("stroke")("value") = "black",
          (spec: Value) => spec("marks")(0)("encode")("update")("size") = ujson.Obj("value" -> 12),
          (spec: Value) => spec("marks")(0)("encode")("update")("opacity") = ujson.Obj("value" -> 1)
        ) ++ mods
      )
  end extension

end extensions
