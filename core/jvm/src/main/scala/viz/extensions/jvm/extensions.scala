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

import viz.PlotTarget

import ujson.Value

import viz.vega.plots.JsonMod
import math.Numeric.Implicits.infixNumericOps
import viz.vega.plots.SimpleRegression
import viz.LowPriorityPlotTarget
import viz.WithBaseSpec

object plotFromFile:
  def apply(path: os.Path, mods: Seq[ujson.Value => Unit] = List())(using
      plotTarget: LowPriorityPlotTarget
  ): WithBaseSpec =
    println("read file")
    new WithBaseSpec(mods):
      override lazy val baseSpec: ujson.Value = ujson.read(os.read(path))
end plotFromFile

extension [N1: Numeric, N2: Numeric](l: Iterable[(N1, N2)])(using plotTarget: PlotTarget)
  def plotRegression(mods: JsonMod = List()): SimpleRegression =
    val values = l.map { case (x, y) =>
      ujson.Obj("x" -> x.toDouble, "y" -> y.toDouble)
    }
    SimpleRegression(
      List((spec: Value) => spec("data")(0)("values") = values) ++ mods
    )
  end plotRegression
end extension
