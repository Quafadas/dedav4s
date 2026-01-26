package experiments.examples

import viz.graph.*

/** Examples demonstrating graph visualization with ELK and Sprotty */
object GraphExamples:

  /** Simple linear graph */
  def simpleLinear(): GraphVisualization =
    GraphViz.simple(
      "Start" -> "Process",
      "Process" -> "End"
    )

  /** Tree structure */
  def tree(): GraphVisualization =
    val dag = DAG.simple(
      "Root" -> "Child1",
      "Root" -> "Child2",
      "Root" -> "Child3",
      "Child1" -> "GrandChild1",
      "Child1" -> "GrandChild2",
      "Child2" -> "GrandChild3"
    )
    GraphViz.visualize(dag, LayoutDirection.Down)
  end tree

  /** Diamond DAG pattern */
  def diamond(): GraphVisualization =
    val dag = DAG.simple(
      "Start" -> "Branch1",
      "Start" -> "Branch2",
      "Branch1" -> "Merge",
      "Branch2" -> "Merge",
      "Merge" -> "End"
    )
    GraphViz.visualize(dag)
  end diamond

  /** Complex workflow */
  def workflow(): GraphVisualization =
    val nodes = Seq(
      GraphNode("input", "Input Data"),
      GraphNode("validate", "Validate"),
      GraphNode("transform", "Transform"),
      GraphNode("analyze", "Analyze"),
      GraphNode("report", "Generate Report", NodeType.Table(3, 2)),
      GraphNode("output", "Output")
    )

    val edges = Seq(
      GraphEdge("e1", "input", "validate"),
      GraphEdge("e2", "validate", "transform"),
      GraphEdge("e3", "transform", "analyze"),
      GraphEdge("e4", "analyze", "report"),
      GraphEdge("e5", "report", "output")
    )

    val dag = DAG(nodes, edges)
    GraphViz.visualize(dag, LayoutDirection.Down, nodeSpacing = 30, layerSpacing = 60)
  end workflow

  /** Save examples to HTML files */
  def saveExamples(outputDir: os.Path): Unit =
    os.makeDir.all(outputDir)

    simpleLinear().saveHtml(outputDir / "simple.html", "Simple Linear Graph")
    tree().saveHtml(outputDir / "tree.html", "Tree Structure")
    diamond().saveHtml(outputDir / "diamond.html", "Diamond DAG")
    workflow().saveHtml(outputDir / "workflow.html", "Complex Workflow")

    println(s"Examples saved to $outputDir")
  end saveExamples

end GraphExamples
