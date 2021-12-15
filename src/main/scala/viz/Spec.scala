package viz

import viz.PlotTarget

trait Spec (using plotTarget : PlotTarget) {
  
    def spec : String = ???

    def show(using plotTarget : PlotTarget) : Unit = plotTarget.show(spec)

    show
}
