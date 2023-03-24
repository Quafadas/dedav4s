package viz

import scala.util.NotGiven

trait LowPriorityPlotTarget

trait PlotTarget extends LowPriorityPlotTarget
  //def show(spec: String): Unit

trait UnitTarget extends PlotTarget:
  def show(spec: String): Unit

trait SharedTargets {  

  given doNothing : UnitTarget with
    override def show(spec: String): Unit = ()

  given printlnTarget: UnitTarget with
    override def show(spec: String): Unit = println(spec)
  
}