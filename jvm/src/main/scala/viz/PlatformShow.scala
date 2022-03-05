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

package viz

import viz.PlotTarget

trait PlatformShow(using plotTarget: PlotTarget) extends Spec:
  def show(using plotTarget: PlotTarget): Unit | os.Path = plotTarget.show(spec)

  // This is the line, which actually triggers plotting the chart
  val out: Unit | os.Path = show
