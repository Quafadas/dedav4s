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

package viz

import scala.util.Random
import scala.annotation.targetName
import ujson.Value

import math.Numeric.Implicits.infixNumericOps
import viz.vega.plots.*
import upickle.default.Writer
import viz.Plottable.*
import NamedTuple.NamedTuple

package object extensions:

  extension [N1: Numeric, N2: Numeric](
      l: Iterable[(N1, N2)]
  )(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plotRegression(mods: Seq[ujson.Value => Unit] = List()) =
      val values = l.map { case (x, y) =>
        ujson.Obj("x" -> x.toDouble, "y" -> y.toDouble)
      }
      CustomPlots.simpleRegression.plot(
        (
          List((spec: Value) => spec("data")(0)("values") = values) ++ mods
        )
      )
    end plotRegression
  end extension

  object nt:
    extension [K <: Tuple, V <: Tuple](
        data: Seq[NamedTuple[K, V]]
    )(using
        plotTarget: LowPriorityPlotTarget,
        rw: Writer[Seq[NamedTuple[K, V]]],
        chartLibrary: ChartLibrary
    )
      @targetName("recordPlotBarChart")
      def plotBarChart(mods: Seq[ujson.Value => Unit]): VizReturn =
        SpecUrl.BarChart.plot(
          List((spec: Value) => spec("data")(0)("values") = upickle.default.writeJs(data)) ++ mods
        )
      end plotBarChart
    end extension
  end nt

  extension [D <: BarPlotDataEntry: Writer](
      data: Seq[D]
  )(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plotBarChart(mods: Seq[ujson.Value => Unit]) =
      SpecUrl.BarChart.plot(
        List((spec: Value) => spec("data")(0)("values") = upickle.default.writeJs(data)) ++ mods
      )
    end plotBarChart
  end extension

  extension [D <: PiePlotDataEntry: Writer](
      data: Seq[D]
  )(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plotPieChart(mods: Seq[ujson.Value => Unit] = List()) =
      val mods2 = List((spec: Value) => spec("data")(0)("values") = upickle.default.writeJs(data)) ++ mods
      SpecUrl.PieChart.plot(mods2)

    end plotPieChart
  end extension

  // type BarRecordData =

  // extension [A](data: Seq[A])(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)

  //   def plotBarChart(fct: A => BarPlotDataEntry)(mods: Seq[ujson.Value => Unit]) =
  //     val chartData = data.map(d =>
  //       val tmp = fct(d)
  //       ujson.Obj("category" -> tmp.category, "amount" -> tmp.amount)
  //     )
  //     SpecUrl.BarChart.plot(
  //       List((spec: Value) => spec("data")(0)("values") = chartData) ++ mods
  //     )
  //   end plotBarChart

  //   def plotPieChart(fct: A => PiePlotDataEntry)(mods: Seq[ujson.Value => Unit]) =
  //     val chartData = data.map(d =>
  //       val tmp = fct(d)
  //       ujson.Obj("id" -> tmp.id, "field" -> tmp.field)
  //     )
  //     SpecUrl.PieChart.plot(
  //       List((spec: Value) => spec("data")(0)("values") = chartData) ++ mods
  //     )
  //   end plotPieChart
  // end extension

  extension [T: Numeric](l: Iterable[T])(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plotBarChart(mods: Seq[ujson.Value => Unit] = List()) =
      val labelToNotShow =
        for number <- l
        yield ujson.Obj(
          "category" -> Random.alphanumeric.take(8).mkString(""),
          "amount" -> number.toDouble
        )
      SpecUrl.BarChart.plot(
        List(
          // viz.Utils.fillDiv,
          (spec: Value) => spec("data")(0)("values") = labelToNotShow,
          viz.Utils.removeXAxis
        ) ++ mods
      )
    end plotBarChart

    def plotPieChart(mods: Seq[ujson.Value => Unit]) =
      val labelToNotShow =
        for number <- l
        yield ujson.Obj(
          "id" -> Random.alphanumeric.take(8).mkString(""),
          "field" -> number.toDouble
        )
      SpecUrl.PieChart.plot(
        List(
          // viz.Utils.fillDiv,
          (spec: Value) => spec("data")(0)("values") = labelToNotShow
        ) ++ mods
      )
    end plotPieChart

    def plotLineChart(mods: Seq[ujson.Value => Unit] = List()) =
      val labelToNotShow =
        for (number, i) <- l.zipWithIndex
        yield ujson.Obj(
          "x" -> i,
          "y" -> number.toDouble
        )
      SpecUrl.LineChart.plot(
        List((spec: Value) => spec("data")(0)("values") = labelToNotShow) ++ mods
      )
    end plotLineChart

    def plotDotPlot(mods: Seq[ujson.Value => Unit] = List()) =
      SpecUrl.DotPlot.plot(
        List((spec: Value) => spec("data")(0)("values") = l.map(_.toDouble)) ++ mods
      )
  end extension
  extension [T: Numeric](
      l: Iterable[(String, T)]
  )(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    @targetName("plotBarChartWithLabels")
    def plotLineChart(mods: Seq[ujson.Value => Unit]) =
      val labelled =
        for (label, number) <- l
        yield ujson.Obj(
          "x" -> label,
          "y" -> number.toDouble
        )
      SpecUrl.LineChart.plot(
        List((spec: Value) =>
          spec("data")(0)("values") = labelled
          // viz.Utils.fillDiv
        ) ++ mods
      )
    end plotLineChart

    @targetName("plotLineChartWithLabels")
    def plotBarChart(mods: Seq[ujson.Value => Unit]) =
      val labelled =
        for (label, number) <- l
        yield ujson.Obj(
          "category" -> label,
          "amount" -> number.toDouble
        )
      SpecUrl.BarChart.plot(
        List((spec: Value) => spec("data")(0)("values") = labelled) ++ mods
      )
    end plotBarChart

    @targetName("plotPieChartWithLabels")
    def plotPieChart(mods: Seq[ujson.Value => Unit]) =
      val labelled =
        for (label, number) <- l
        yield ujson.Obj(
          "id" -> label,
          "field" -> number.toDouble
        )
      SpecUrl.PieChart.plot(
        List(
          (spec: Value) => spec("data")(0)("values") = labelled,
          (spec: Value) => spec("height") = 400,
          (spec: Value) => spec("width") = 550,
          (spec: Value) => spec("legends") = ujson.Arr(ujson.Obj("fill" -> "color", "orient" -> "top-right")),
          (spec: Value) => spec("marks")(0)("encode")("enter")("x") = ujson.Obj("signal" -> "height / 2"),
          (spec: Value) => spec("marks")(0)("encode")("update")("outerRadius") = ujson.Obj("signal" -> "height / 2")
        ) ++ mods
      )
    end plotPieChart
  end extension

  // extension [T: Numeric](l: Map[String, T])(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
  //   @targetName("plotBarChartFromMapWithLabels")
  //   def plotBarChart(mods: Seq[ujson.Value => Unit]): VizReturn = l.iterator.toSeq.plotBarChart(mods)

  //   def plotLineChart(mods: Seq[ujson.Value => Unit]): VizReturn = l.iterator.toSeq.plotLineChart(mods)

  //   def plotPieChart(mods: Seq[ujson.Value => Unit]): VizReturn = l.iterator.toSeq.plotPieChart(mods)
  // end extension

  extension [N1: Numeric, N2: Numeric](
      l: Iterable[(N1, N2)]
  )(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plotScatter(mods: Seq[ujson.Value => Unit] = List()): VizReturn =
      val values = l.map { case (x, y) =>
        ujson.Obj("x" -> x.toDouble, "y" -> y.toDouble)
      }
      SpecUrl.ScatterPlot.plot(
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
          // viz.Utils.fillDiv
        ) ++ mods
      )
  end extension

  extension (s: String)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plotWordCloud(mods: Seq[ujson.Value => Unit] = List()) = List(s).plotWordcloud(mods)
  end extension

  extension (s: Seq[String])(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plotWordcloud(mods: Seq[ujson.Value => Unit] = List()) =
      SpecUrl.WordCloud.plot(
        List(
          (spec: Value) => spec("data")(0)("values").arr.clear(),
          (spec: Value) => spec("data")(0)("values") = s
          // viz.Utils.fillDiv
        ) ++ mods
      )
  end extension
end extensions
