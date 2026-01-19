package viz.graph

/** Represents a node in the directed graph */
case class GraphNode(
    id: String,
    label: String,
    nodeType: NodeType = NodeType.Simple,
    width: Option[Double] = None,
    height: Option[Double] = None
)

/** Represents different types of nodes */
enum NodeType:
  case Simple
  case Table(rows: Int, columns: Int)
end NodeType

/** Represents an edge in the directed graph */
case class GraphEdge(
    id: String,
    source: String,
    target: String
)

/** Represents a directed acyclic graph */
case class DAG(
    nodes: Seq[GraphNode],
    edges: Seq[GraphEdge]
):
  /** Validates that the graph is acyclic */
  def validate(): Either[String, Unit] =
    if hasCycle() then Left("Graph contains a cycle")
    else Right(())

  private def hasCycle(): Boolean =
    val adjacencyList = edges.groupBy(_.source).map { case (k, v) =>
      k -> v.map(_.target)
    }

    def dfs(
        node: String,
        visited: Set[String],
        recStack: Set[String]
    ): Boolean =
      if recStack.contains(node) then true
      else if visited.contains(node) then false
      else
        val newVisited = visited + node
        val newRecStack = recStack + node

        adjacencyList.get(node) match
          case Some(neighbors) =>
            neighbors.exists(neighbor => dfs(neighbor, newVisited, newRecStack))
          case None => false
        end match
      end if
    end dfs

    nodes.map(_.id).exists(nodeId => dfs(nodeId, Set.empty, Set.empty))
  end hasCycle
end DAG

object DAG:
  /** Creates a simple DAG with basic nodes */
  def simple(edges: (String, String)*): DAG =
    val nodeIds = edges.flatMap { case (src, tgt) => Seq(src, tgt) }.distinct
    val nodes = nodeIds.map(id => GraphNode(id, id))
    val graphEdges = edges.zipWithIndex.map { case ((src, tgt), idx) =>
      GraphEdge(s"edge_$idx", src, tgt)
    }
    DAG(nodes, graphEdges)
  end simple
end DAG
