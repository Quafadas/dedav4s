package viz

import viz.PlotTarget

trait Spec(using plotTarget: PlotTarget):

  def spec: String = ???

  def show(using plotTarget: PlotTarget): Unit | os.Path = plotTarget.show(spec)

  val out: Unit | os.Path = show
