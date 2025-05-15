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

enum ChartLibrary:
  case Vega, Echarts
end ChartLibrary

given vegaFlavour: ChartLibrary = ChartLibrary.Vega
given echartsFlavour: ChartLibrary = ChartLibrary.Echarts

inline def applyMods(spec: ujson.Value, mods: Seq[ujson.Value => Unit]): ujson.Value =
  val temp = spec
  for m <- mods do m(temp)
  end for
  temp
end applyMods