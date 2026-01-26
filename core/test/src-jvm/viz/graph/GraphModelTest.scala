package viz.graph

import munit.FunSuite

class GraphModelTest extends FunSuite:

  test("DAG creation with simple edges") {
    val dag = DAG.simple(
      "A" -> "B",
      "B" -> "C",
      "A" -> "C"
    )

    assertEquals(dag.nodes.size, 3)
    assertEquals(dag.edges.size, 3)
    assert(dag.validate().isRight)
  }

  test("DAG detects cycles") {
    val nodes = Seq(
      GraphNode("A", "Node A"),
      GraphNode("B", "Node B"),
      GraphNode("C", "Node C")
    )
    val edges = Seq(
      GraphEdge("e1", "A", "B"),
      GraphEdge("e2", "B", "C"),
      GraphEdge("e3", "C", "A") // Creates a cycle
    )
    val dag = DAG(nodes, edges)

    assert(dag.validate().isLeft)
  }

  test("DAG accepts acyclic graph") {
    val nodes = Seq(
      GraphNode("A", "Node A"),
      GraphNode("B", "Node B"),
      GraphNode("C", "Node C"),
      GraphNode("D", "Node D")
    )
    val edges = Seq(
      GraphEdge("e1", "A", "B"),
      GraphEdge("e2", "A", "C"),
      GraphEdge("e3", "B", "D"),
      GraphEdge("e4", "C", "D")
    )
    val dag = DAG(nodes, edges)

    assert(dag.validate().isRight)
  }

  test("GraphNode with table type") {
    val node = GraphNode(
      "table1",
      "My Table",
      NodeType.Table(3, 2),
      Some(160.0),
      Some(75.0)
    )

    assertEquals(node.nodeType, NodeType.Table(3, 2))
    assertEquals(node.width, Some(160.0))
    assertEquals(node.height, Some(75.0))
  }

end GraphModelTest
