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

import ujson.Value
import viz.vega.plots.SpecUrl
import java.net.URI
import viz.vega.Framework
import viz.dsl.vega.VegaDsl
import io.circe.parser.decode
import viz.dsl.*
import viz.dsl.vegaLite.VegaLiteDsl

abstract class FromUrl(val location: SpecUrl)(using LowPriorityPlotTarget) extends WithBaseSpec:
  override lazy val baseSpec = location.jsonSpec
end FromUrl

trait PlotHasVegaDsl(using LowPriorityPlotTarget) extends WithBaseSpec:
  def toDsl(): VegaDsl = decode[VegaDsl](baseSpec.toString()).fold(throw _, identity)
end PlotHasVegaDsl

trait PlotHasVegaLiteDsl(using LowPriorityPlotTarget) extends WithBaseSpec:
  def toDsl(): VegaLiteDsl = decode[VegaLiteDsl](baseSpec.toString()).fold(throw _, identity)
end PlotHasVegaLiteDsl
