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

package viz.extensions.jvm

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

extension [N1: Numeric, N2: Numeric](l: Iterable[(N1, N2)])(using plotTarget: PlotTarget)
  def plotRegression(mods: JsonMod = List()): SimpleRegression =
    val values = l.map { case (x, y) =>
      ujson.Obj("x" -> x.toDouble, "y" -> y.toDouble)
    }
    SimpleRegression(
      List((spec: Value) => spec("data")(0)("values") = values) ++ mods
    )
