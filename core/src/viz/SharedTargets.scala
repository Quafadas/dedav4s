package viz

//type VizReturn

trait LowPriorityPlotTarget:
  def show(spec: ujson.Value, library: ChartLibrary): VizReturn
end LowPriorityPlotTarget

private[viz] trait PlotTarget extends LowPriorityPlotTarget
//def show(spec: String): Unit

private[viz] trait UnitTarget extends PlotTarget:
  def show(spec: ujson.Value, library: ChartLibrary): Unit
end UnitTarget

trait SharedTargets:

  given doNothing: UnitTarget with
    override def show(spec: ujson.Value, library: ChartLibrary): Unit = ()
  end doNothing

  given printlnTarget: UnitTarget with
    override def show(spec: ujson.Value, library: ChartLibrary): Unit = println(spec)
  end printlnTarget
end SharedTargets
