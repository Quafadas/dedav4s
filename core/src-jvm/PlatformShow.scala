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

import viz.vega.plots.SpecUrl
import os.ResourcePath
import NamedTuple.AnyNamedTuple
import NamedTuple.NamedTuple
import upickle.default.Writer

type VizReturn = Unit | os.Path

private[viz] trait NtPlatformPlot[AnyNamedTuple]:
  extension [N <: Tuple, V <: Tuple, T <: NamedTuple[N, V]](plottable: T)
    def plot()(using w: Writer[T], plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary): VizReturn
  end extension
end NtPlatformPlot

private[viz] trait PlatformPlot[T]:
  extension (plottable: T)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plot: VizReturn
    def plot(mods: Seq[ujson.Value => Unit]): VizReturn
  end extension
end PlatformPlot

object Plottable:

  private inline def applyMods(spec: ujson.Value, mods: Seq[ujson.Value => Unit]): ujson.Value =
    val temp = spec
    for m <- mods do m(temp)
    end for
    temp
  end applyMods

  /** This assumes the string is a valid specification for your charting library and plots it on a hail mary
    */
  // given ppString: PlatformPlot[String] = new PlatformPlot[String]:
  extension (plottable: String)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plot(
        mods: Seq[ujson.Value => Unit]
    ): VizReturn =
      val spec = ujson.read(plottable)
      val modifiedSpec = applyMods(spec, mods)
      plotTarget.show(modifiedSpec.toString, chartLibrary)
    end plot

    def plot: VizReturn =
      plotTarget.show(plottable, chartLibrary)

    end plot
  end extension

  /** This assumes the path is a file which contains a valid specification for your charting library
    */
  // given ppOsJson: PlatformPlot[os.Path] = new PlatformPlot[os.Path]:
  extension (plottable: os.Path)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plot(
        mods: Seq[ujson.Value => Unit]
    ): VizReturn =
      val spec = os.read(plottable)
      val modifiedSpec = applyMods(ujson.read(spec), mods)
      plotTarget.show(modifiedSpec.toString, chartLibrary)

    end plot

    def plot: VizReturn =
      val spec = os.read(plottable)
      plotTarget.show(spec, chartLibrary)

    end plot

  end extension

  /** This assumes the value is a valid specification for your charting library
    */
  // given ppujson: PlatformPlot[ujson.Value] = new PlatformPlot[ujson.Value]:
  extension (plottable: ujson.Value)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plot(
        mods: Seq[ujson.Value => Unit]
    ): VizReturn =
      val modifiedSpec = applyMods(plottable, mods)
      plotTarget.show(modifiedSpec.toString, chartLibrary)

    end plot

    def plot: VizReturn =
      plotTarget.show(plottable.toString, chartLibrary)

    end plot

  end extension

  /** This assumes the value is a valid specification for your charting library
    */
  // given ppSpecUrl: PlatformPlot[SpecUrl] = new PlatformPlot[SpecUrl]:
  extension (plottable: SpecUrl)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plot(
        mods: Seq[ujson.Value => Unit]
    ): VizReturn =
      val spec = plottable.jsonSpec
      val modifiedSpec = applyMods(spec, mods)
      plotTarget.show(modifiedSpec.toString, chartLibrary)

    end plot

    def plot: VizReturn =
      val spec = plottable.jsonSpec
      plotTarget.show(spec.toString(), chartLibrary)

    end plot

  end extension

  // given pprp: PlatformPlot[ResourcePath] = new PlatformPlot[ResourcePath]:
  extension (plottable: ResourcePath)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plot(
        mods: Seq[ujson.Value => Unit]
    ): VizReturn =
      val spec = os.read(plottable)
      val modifiedSpec = applyMods(ujson.read(spec), mods)
      plotTarget.show(modifiedSpec.toString, chartLibrary)

    end plot

    def plot: VizReturn =
      val spec = os.read(plottable)
      plotTarget.show(spec, chartLibrary)

    end plot

  end extension

  // given ppnt: NtPlatformPlot[AnyNamedTuple] = new NtPlatformPlot[AnyNamedTuple]:

  extension [N <: Tuple, V <: Tuple, T <: NamedTuple[N, V]](plottable: T)
    def plot()(using w: Writer[T], plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary): VizReturn =
      println(plotTarget.toString())
      val spec = upickle.default.writeJs(plottable)
      val modifiedSpec = applyMods(spec, List.empty)
      plotTarget.show(modifiedSpec.toString, chartLibrary)

    end plot
  end extension

end Plottable
