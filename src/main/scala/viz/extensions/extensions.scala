/*
 * Copyright 2022 quafadas
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

package viz.extensions

import viz.vega.plots.BarChart
import viz.vega.plots.PieChart
import viz.PlotTarget
import scala.util.Random
import scala.annotation.targetName
import viz.vega.plots.WordCloud
import ujson.Value

import viz.vega.plots.JsonMod
import viz.vega.plots.LineChart
import viz.vega.plots.DotPlot
import math.Numeric.Implicits.infixNumericOps
import viz.vega.plots.ScatterPlot
import viz.vega.plots.Regression
import viz.vega.plots.SimpleRegression

extension [T: Numeric](l: Iterable[T])(using plotTarget: PlotTarget)
  def plotBarChart(mods: JsonMod = List()): BarChart =
    val labelToNotShow =
      for (number <- l)
        yield ujson.Obj(
          "category" -> Random.alphanumeric.take(8).mkString(""),
          "amount" -> number.toDouble
        )
    new BarChart(
      List(
        //viz.Utils.fillDiv,
        (spec: Value) => spec("data")(0)("values") = labelToNotShow,
        viz.Utils.removeXAxis
      ) ++ mods
    )

  def plotPieChart(mods: JsonMod = List()): PieChart =
    val labelToNotShow =
      for (number <- l)
        yield ujson.Obj(
          "id" -> Random.alphanumeric.take(8).mkString(""),
          "field" -> number.toDouble
        )
    new PieChart(
      List(
        //viz.Utils.fillDiv,
        (spec: Value) => spec("data")(0)("values") = labelToNotShow
      ) ++ mods
    )

  def plotLineChart(mods: JsonMod = List()): LineChart =
    val labelToNotShow =
      for ((number, i) <- l.zipWithIndex)
        yield ujson.Obj(
          "x" -> i,
          "y" -> number.toDouble
        )
    new LineChart(
      List((spec: Value) => spec("data")(0)("values") = labelToNotShow) ++ mods
    )

  def plotDotPlot(mods: JsonMod = List()): DotPlot =
    new DotPlot(
      List((spec: Value) => spec("data")(0)("values") = l.map(_.toDouble)) ++ mods
    )

extension [T: Numeric](l: Iterable[(String, T)])(using plotTarget: PlotTarget)
  @targetName("plotBarChartWithLabels")
  def plotBarChart(mods: JsonMod): BarChart =
    val labelled =
      for ((label, number) <- l)
        yield ujson.Obj(
          "category" -> label,
          "amount" -> number.toDouble
        )
    new BarChart(
      List((spec: Value) => spec("data")(0)("values") = labelled
      //viz.Utils.fillDiv
      ) ++ mods
    )

  @targetName("plotPieChartWithLabels")
  def plotPieChart(mods: JsonMod): PieChart =
    val labelled =
      for ((label, number) <- l)
        yield ujson.Obj(
          "id" -> label,
          "field" -> number.toDouble
        )
    new PieChart(
      List(
        (spec: Value) => spec("data")(0)("values") = labelled,
        (spec: Value) => spec("height") = 400,
        (spec: Value) => spec("width") = 550,
        (spec: Value) => spec("legends") = ujson.Arr(ujson.Obj("fill" -> "color", "orient" -> "top-right")),
        (spec: Value) => spec("marks")(0)("encode")("enter")("x") = ujson.Obj("signal" -> "height / 2"),
        (spec: Value) => spec("marks")(0)("encode")("update")("outerRadius") = ujson.Obj("signal" -> "height / 2")
      ) ++ mods
    )

extension [T: Numeric](l: Map[String, T])(using plotTarget: PlotTarget)
  @targetName("plotBarChartFromMapWithLabels")
  def plotBarChart(mods: JsonMod): BarChart = l.iterator.toSeq.plotBarChart(mods)

  def plotPieChart(mods: JsonMod): PieChart = l.iterator.toSeq.plotPieChart(mods)

extension [N1: Numeric, N2: Numeric](l: Iterable[(N1, N2)])(using plotTarget: PlotTarget)
  def plotScatter(mods: JsonMod = List()): ScatterPlot =
    val values = l.map { case (x, y) =>
      ujson.Obj("x" -> x.toDouble, "y" -> y.toDouble)
    }
    new ScatterPlot(
      List(
        (spec: Value) => spec("data") = ujson.Arr(ujson.Obj("name" -> "source", "values" -> values)),
        (spec: Value) =>
          spec.obj.remove("legends"); (),
        (spec: Value) => spec("axes")(0)("title") = "x",
        (spec: Value) => spec("axes")(1)("title") = "y",
        (spec: Value) => spec("marks")(0)("encode")("update")("x")("field") = "x",
        (spec: Value) => spec("scales")(0)("domain")("field") = "x",
        (spec: Value) => spec("scales")(1)("domain")("field") = "y",
        (spec: Value) =>
          spec("scales").arr.dropRightInPlace(1); (),
        (spec: Value) => spec("marks")(0)("encode")("update")("y")("field") = "y",
        (spec: Value) => spec("marks")(0)("encode")("update")("stroke")("value") = "black",
        (spec: Value) => spec("marks")(0)("encode")("update")("size") = ujson.Obj("value" -> 12),
        (spec: Value) => spec("marks")(0)("encode")("update")("opacity") = ujson.Obj("value" -> 1)
        //viz.Utils.fillDiv
      ) ++ mods
    )

  def plotRegression(mods: JsonMod = List()): SimpleRegression =
    val values = l.map { case (x, y) =>
      ujson.Obj("x" -> x.toDouble, "y" -> y.toDouble)
    }
    new SimpleRegression(
      List((spec: Value) => spec("data")(0)("values") = values) ++ mods
    )

extension (s: String)(using plotTarget: PlotTarget)
  def plotWordCloud(mods: JsonMod = List()): WordCloud = List(s).plotWordcloud(mods)

extension (s: Seq[String])(using plotTarget: PlotTarget)
  def plotWordcloud(mods: JsonMod = List()): WordCloud =
    new WordCloud(
      List(
        (spec: Value) => spec("data")(0)("values").arr.clear(),
        (spec: Value) => spec("data")(0)("values") = s
        //viz.Utils.fillDiv
      ) ++ mods
    )
