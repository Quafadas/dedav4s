package experiments

import io.circe.syntax.*
import viz.graph.*
import viz.PlotTargets.desktopBrowser

@main def graph =
  // no local import here; use fully-qualified PlotTargets when needed

  val dag = DAG.simple(
    "Root" -> "Child1",
    "Root" -> "Child2",
    "Root" -> "Child3",
    "Child1" -> "GrandChild1",
    "Child1" -> "GrandChild2",
    "Child2" -> "GrandChild3"
  )
  val layout = GraphViz.visualize(dag, LayoutDirection.Right, nodeSpacing = 30, layerSpacing = 60)
  layout.plot("My Graph")
end graph
