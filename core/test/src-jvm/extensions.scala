import viz.extensions.*
// import viz.PlotTargets.websocket // for local testing
// import viz.PlotTargets.desktopBrowser // for local testing
import viz.PlotTargets.doNothing // for CI... as we don't have a port available...
import scala.util.Random
import viz.vegaFlavour

class ExtensionSuiteJVM extends munit.FunSuite:
  test("extension methods exist... ") {

    val sleepLenth = 10

    val randomNumbers1: IndexedSeq[Double] = (0 to 20).map(i => i * Random.nextDouble())
    val randomTuple_Int_Double: IndexedSeq[(Int, Double)] = (0 to 20).map(i => (i, i * Random.nextDouble()))
    val randomTuple_String_Int: IndexedSeq[(String, Int)] = (0 to 5).map(i => (Random.nextString(5), i))
    val randomMap_String_Int: Map[String, Int] = randomTuple_String_Int.toMap

    List(1, 2, 3, 4).plotBarChart
    Thread.sleep(sleepLenth)
    randomMap_String_Int.plotBarChart(List())
    Thread.sleep(sleepLenth)
    randomMap_String_Int.plotPieChart(List())

    Thread.sleep(sleepLenth)
    List(("hi", 1.5), ("boo", 2.5), ("baz", 3.0)).plotPieChart(List())

    Thread.sleep(sleepLenth)
    List(("hi", 1.5), ("boo", 2.5), ("baz", 3.0)).plotBarChart

    Thread.sleep(sleepLenth)
    List(("hi", 1.5), ("boo", 2.5), ("baz", 3.0)).plotLineChart

    Thread.sleep(sleepLenth)
    "interesting stuff stuff interesting".plotWordCloud()

    Thread.sleep(sleepLenth)
    List("interesting", "stuff", "interesting").plotWordcloud()

    Thread.sleep(sleepLenth)
    randomNumbers1.plotLineChart()

    Thread.sleep(sleepLenth)
    randomTuple_Int_Double.plotScatter()

    Thread.sleep(sleepLenth)
    randomTuple_Int_Double.plotRegression()

  }
end ExtensionSuiteJVM
