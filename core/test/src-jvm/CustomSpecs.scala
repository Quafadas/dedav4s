import viz.PlotTargets.doNothing
import viz.Plottable.*
import viz.vegaFlavour
import viz.CustomPlots

class CustomSpecs extends munit.FunSuite:

  test("Compiles custom plots") {

    CustomPlots.sankey.plot
    CustomPlots.simpleRegression.plot
    CustomPlots.seriesScatter.plot

  }

end CustomSpecs
