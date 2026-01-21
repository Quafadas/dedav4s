package viz.graph

import munit.FunSuite
import io.circe.*
import io.circe.parser.*

class GraphVizTest extends FunSuite:

  test("GraphViz.simple creates visualization") {
    val viz = GraphViz.simple(
      "Start" -> "Middle",
      "Middle" -> "End"
    )

    val json = viz.toJson
    val typeField = json.hcursor.downField("type").as[String]
    val children = json.hcursor.downField("children").as[List[Json]]

    assertEquals(typeField, Right("graph"))
    assert(children.isRight && children.toOption.get.nonEmpty)
  }

  test("GraphViz.visualize validates DAG") {
    val nodes = Seq(
      GraphNode("A", "A"),
      GraphNode("B", "B")
    )
    val edges = Seq(
      GraphEdge("e1", "A", "B"),
      GraphEdge("e2", "B", "A") // Creates cycle
    )
    val dag = DAG(nodes, edges)

    intercept[IllegalArgumentException] {
      GraphViz.visualize(dag)
    }
  }

  test("GraphVisualization exports to HTML") {
    val viz = GraphViz.simple("A" -> "B")
    val html = viz.toHtml("Test Graph")

    assert(html.contains("<!DOCTYPE html>"))
    assert(html.contains("Test Graph"))
    assert(html.contains("sprotty"))
  }

  test("GraphVisualization exports to JSON string") {
    val viz = GraphViz.simple("X" -> "Y")
    val jsonStr = viz.toJsonString

    assert(jsonStr.contains("\"type\""))
    assert(jsonStr.contains("\"graph\""))
  }

  test("Complex DAG visualization") {
    val dag = DAG.simple(
      "Root" -> "Child1",
      "Root" -> "Child2",
      "Child1" -> "Leaf1",
      "Child1" -> "Leaf2",
      "Child2" -> "Leaf2",
      "Child2" -> "Leaf3"
    )

    val viz = GraphViz.visualize(dag, LayoutDirection.Down)
    val json = viz.toJson

    val cursor = json.hcursor
    val children = cursor.downField("children").as[List[Json]]

    assert(children.isRight)
    val childList = children.toOption.get

    // Count nodes (have position field) and edges (have type == "edge")
    val nodes = childList.filter(_.hcursor.downField("position").succeeded)
    val edges = childList.filter { child =>
      child.hcursor.downField("type").as[String].contains("edge")
    }

    assertEquals(nodes.size, 6) // Root, Child1, Child2, Leaf1, Leaf2, Leaf3
    assertEquals(edges.size, 6)
  }

end GraphVizTest
