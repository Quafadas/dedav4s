/*
 * Copyright 2024 quafadas
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

//type VizReturn

trait LowPriorityPlotTarget:
  def show(spec: String): VizReturn
end LowPriorityPlotTarget

trait PlotTarget extends LowPriorityPlotTarget
//def show(spec: String): Unit

trait UnitTarget extends PlotTarget:
  def show(spec: String): Unit
end UnitTarget

trait SharedTargets:

  given doNothing: UnitTarget with
    override def show(spec: String): Unit = ()
  end doNothing

  given printlnTarget: UnitTarget with
    override def show(spec: String): Unit = println(spec)
  end printlnTarget
end SharedTargets
