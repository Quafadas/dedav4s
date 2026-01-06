package viz

import viz.LowPriorityPlotTarget
import viz.ChartLibrary

// Want a way to instantiate this as a default, but out the way of the user
given dontPlot: LowPriorityPlotTarget = new LowPriorityPlotTarget:
  override def show(spec: ujson.Value, lib: ChartLibrary): Unit = ()
