package viz.graph

import munit.FunSuite

class ELKLayoutEngineTest extends FunSuite:

  test("ELK layout computes positions") {
    val dag = DAG.simple(
      "A" -> "B",
      "B" -> "C"
    )

    val engine = new ELKLayoutEngine()
    val layout = engine.computeLayout(dag)

    assertEquals(layout.nodes.size, 3)
    assertEquals(layout.edges.size, 2)

    // Check that nodes have positions
    layout.nodes.foreach { node =>
      assert(node.x >= 0)
      assert(node.y >= 0)
      assert(node.width > 0)
      assert(node.height > 0)
    }
  }

  test("ELK layout respects direction") {
    val dag = DAG.simple("A" -> "B")

    val engine = new ELKLayoutEngine()

    // Test different directions don't crash
    val layoutDown = engine.computeLayout(dag, LayoutDirection.Down)
    val layoutRight = engine.computeLayout(dag, LayoutDirection.Right)

    assert(layoutDown.nodes.nonEmpty)
    assert(layoutRight.nodes.nonEmpty)
  }

  test("ELK layout handles disconnected nodes") {
    val nodes = Seq(
      GraphNode("A", "Node A"),
      GraphNode("B", "Node B")
    )
    val edges = Seq.empty[GraphEdge]
    val dag = DAG(nodes, edges)

    val engine = new ELKLayoutEngine()
    val layout = engine.computeLayout(dag)

    assertEquals(layout.nodes.size, 2)
    assertEquals(layout.edges.size, 0)
  }

  test("ELK layout computes table node sizes") {
    val nodes = Seq(
      GraphNode("table1", "Table", NodeType.Table(3, 2))
    )
    val dag = DAG(nodes, Seq.empty)

    val engine = new ELKLayoutEngine()
    val layout = engine.computeLayout(dag)

    val tableNode = layout.nodes.head
    // Table with 2 columns, 3 rows
    assert(tableNode.width > 0)
    assert(tableNode.height > 0)
  }

end ELKLayoutEngineTest
