package viz

import viz.vega.plots.SpecUrl
import os.ResourcePath
import NamedTuple.AnyNamedTuple
import NamedTuple.NamedTuple
import upickle.default.Writer
import viz.vega.VegaSpec
import viz.macros.SpecMod
import io.circe.Codec
import io.circe.syntax.*

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

  /** This assumes the string is a valid specification for your charting library and plots it on a hail mary
    */
  // given ppString: PlatformPlot[String] = new PlatformPlot[String]:
  extension (plottable: String)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plot(
        mods: Seq[ujson.Value => Unit]
    ): VizReturn =
      val spec = ujson.read(plottable)
      val modifiedSpec = spec.applyMods(mods)
      plotTarget.show(modifiedSpec, chartLibrary)
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
      val modifiedSpec = ujson.read(spec).applyMods(mods)
      plotTarget.show(modifiedSpec, chartLibrary)

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
      val modifiedSpec = plottable.applyMods(mods)
      plotTarget.show(modifiedSpec, chartLibrary)

    end plot

    def plot: VizReturn =
      plotTarget.show(plottable, chartLibrary)

    end plot

  end extension

  /** This assumes the value is a valid specification for your charting library
    */
  // given ppujson: PlatformPlot[ujson.Value] = new PlatformPlot[ujson.Value]:
  extension (plottable: io.circe.Json)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plot(
        mods: viz.macros.SpecMod*
    ): VizReturn =
      val finalSpec = mods.foldLeft(plottable)((json, modFn) => modFn(json))
      val ujsonSpec = ujson.read(finalSpec.toString)
      plotTarget.show(ujsonSpec, chartLibrary)

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
      val modifiedSpec = spec.applyMods(mods)
      plotTarget.show(modifiedSpec, chartLibrary)

    end plot

    def plot: VizReturn =
      val spec = plottable.jsonSpec
      plotTarget.show(spec, chartLibrary)

    end plot

  end extension

  extension [A](plottable: VegaSpec[A])(using plotTarget: LowPriorityPlotTarget)

    /** Plot with lambda syntax: spec.plot(_.title := "New") */
    def plot(mods: (A => SpecMod)*): VizReturn =
      given ChartLibrary = ChartLibrary.Vega
      ujson.read(plottable.build(mods*).toString).plot(using plotTarget, ChartLibrary.Vega)
    end plot

    /** Plot with pre-built SpecMod sequence */
    def plotWith(mods: Seq[SpecMod]): VizReturn =
      given ChartLibrary = ChartLibrary.Vega
      ujson.read(plottable.buildWith(mods*).toString).plot(using plotTarget, ChartLibrary.Vega)
    end plotWith

  end extension

  // given pprp: PlatformPlot[ResourcePath] = new PlatformPlot[ResourcePath]:
  extension (plottable: ResourcePath)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
    def plot(
        mods: Seq[ujson.Value => Unit]
    ): VizReturn =
      val spec = os.read(plottable)
      val modifiedSpec = ujson.read(spec).applyMods(mods)
      plotTarget.show(modifiedSpec, chartLibrary)

    end plot

    def plot: VizReturn =
      val spec = os.read(plottable)
      plotTarget.show(spec, chartLibrary)

    end plot

  end extension

  // given ppnt: NtPlatformPlot[AnyNamedTuple] = new NtPlatformPlot[AnyNamedTuple]:

  extension [T <: NamedTuple.AnyNamedTuple](plottable: T)
    def plot()(using w: Codec[T], plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary): VizReturn =
      // println(plotTarget.toString())
      val spec = ujson.read(plottable.asJson.toString)
      val modifiedSpec = spec.applyMods(List.empty)
      plotTarget.show(modifiedSpec, chartLibrary)

    end plot
  end extension

end Plottable
