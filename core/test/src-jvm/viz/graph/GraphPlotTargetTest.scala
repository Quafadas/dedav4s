package viz.graph

import munit.FunSuite

class GraphPlotTargetTest extends FunSuite:

  test("GraphVisualization can use plot targets") {
    // Create a simple graph
    val graphViz = GraphViz.simple("A" -> "B", "B" -> "C")

    // Use tempHtmlFile target
    given viz.PlotTarget = viz.PlotTargets.tempHtmlFile

    // Plot should return a path
    val result = graphViz.plot("Test Graph")

    // Result should be a path (os.Path)
    assert(result != null)
    assert(result.isInstanceOf[os.Path])

    // Clean up
    val path = result.asInstanceOf[os.Path]
    if os.exists(path) then os.remove(path)
    end if
  }

  test("GraphVisualization plot creates valid HTML") {
    val graphViz = GraphViz.simple("Start" -> "End")

    given viz.PlotTarget = viz.PlotTargets.tempHtmlFile

    val result = graphViz.plot("Integration Test")
    val path = result.asInstanceOf[os.Path]

    // Verify file exists and contains expected content
    assert(os.exists(path))
    val content = os.read(path)
    assert(content.contains("<!DOCTYPE html>"))
    assert(content.contains("Integration Test"))
    assert(content.contains("sprotty"))

    // Clean up
    os.remove(path)
  }

end GraphPlotTargetTest
