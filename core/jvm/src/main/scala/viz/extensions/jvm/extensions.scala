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

import ujson.Value
import math.Numeric.Implicits.infixNumericOps
import viz.LowPriorityPlotTarget

import viz.Plottable.given_PlatformPlot_ResourcePath
import viz.ChartLibrary

extension [N1: Numeric, N2: Numeric](
    l: Iterable[(N1, N2)]
)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
  def plotRegression(mods: Seq[ujson.Value => Unit] = List()) =
    val values = l.map { case (x, y) =>
      ujson.Obj("x" -> x.toDouble, "y" -> y.toDouble)
    }
    viz.vega.plots.CustomPlots.simpleRegression.plot(
      (
        List((spec: Value) => spec("data")(0)("values") = values) ++ mods
      )
    )
  end plotRegression
end extension
