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

package viz.dsl

import scala.language.implicitConversions
import viz.PlotTarget
import io.circe.syntax.*
import io.circe.Encoder
import viz.dsl.vega.VegaDsl
import viz.PlatformShow
import viz.dsl.vegaLite.VegaLiteDsl
import viz.LowPriorityPlotTarget

object Conversion:
  extension [T](moreJson: T)(using enc: Encoder[T])
    def u: ujson.Value =
      val enc = summon[Encoder[T]]
      ujson.read(enc(moreJson).toString)
end Conversion

case class DslSpec(in: VegaDsl | VegaLiteDsl)(using LowPriorityPlotTarget) extends PlatformShow:
  override def spec =
    in match
      case v: VegaDsl      => v.asJson.deepDropNullValues.toString
      case vl: VegaLiteDsl => vl.asJson.deepDropNullValues.toString
end DslSpec

object DslPlot:
  extension (vega: VegaDsl)
    def plot(using PlotTarget) =
      DslSpec(vega)
  extension (vega: VegaLiteDsl)
    def plot(using PlotTarget) =
      DslSpec(vega)

  extension (vega: VegaDsl | VegaLiteDsl)
    def plot(using PlotTarget) =
      DslSpec(vega)
end DslPlot
