package viz.graph

import org.eclipse.elk.core.RecursiveGraphLayoutEngine
import org.eclipse.elk.core.options.CoreOptions
import org.eclipse.elk.core.options.Direction
import org.eclipse.elk.core.util.NullElkProgressMonitor
import org.eclipse.elk.graph.{ElkNode, ElkEdge, ElkLabel}
import org.eclipse.elk.graph.util.ElkGraphUtil

/** Layout engine using ELK (Eclipse Layout Kernel) */
class ELKLayoutEngine:

  private val layoutEngine = new RecursiveGraphLayoutEngine()

  /** Computes layout for a DAG and returns positioned nodes and edges */
  def computeLayout(
      dag: DAG,
      direction: LayoutDirection = LayoutDirection.Down,
      nodeSpacing: Double = 20.0,
      layerSpacing: Double = 50.0
  ): LayoutResult =
    // Create ELK graph
    val root = ElkGraphUtil.createGraph()

    // Set layout options
    root.setProperty(
      CoreOptions.ALGORITHM,
      "org.eclipse.elk.layered"
    )
    root.setProperty(
      CoreOptions.DIRECTION,
      direction match
        case LayoutDirection.Down  => Direction.DOWN
        case LayoutDirection.Up    => Direction.UP
        case LayoutDirection.Left  => Direction.LEFT
        case LayoutDirection.Right => Direction.RIGHT
    )
    root.setProperty(CoreOptions.SPACING_NODE_NODE, nodeSpacing)
    root.setProperty(CoreOptions.SPACING_EDGE_NODE, layerSpacing)

    // Create nodes
    val elkNodes = scala.collection.mutable.Map[String, ElkNode]()
    dag.nodes.foreach { node =>
      val elkNode = ElkGraphUtil.createNode(root)
      elkNode.setIdentifier(node.id)

      // Set node dimensions
      val (width, height) = computeNodeSize(node)
      elkNode.setWidth(width)
      elkNode.setHeight(height)

      // Add label
      val label = ElkGraphUtil.createLabel(node.label, elkNode)

      elkNodes(node.id) = elkNode
    }

    // Create edges
    dag.edges.foreach { edge =>
      val source = elkNodes.get(edge.source)
      val target = elkNodes.get(edge.target)

      (source, target) match
        case (Some(src), Some(tgt)) =>
          val elkEdge = ElkGraphUtil.createSimpleEdge(src, tgt)
          elkEdge.setIdentifier(edge.id)
        case _ =>
          throw new IllegalArgumentException(
            s"Edge ${edge.id} references non-existent nodes"
          )
      end match
    }

    // Run layout
    layoutEngine.layout(root, new NullElkProgressMonitor())

    // Extract positioned nodes
    val positionedNodes = dag.nodes.map { node =>
      val elkNode = elkNodes(node.id)
      PositionedNode(
        id = node.id,
        label = node.label,
        x = elkNode.getX,
        y = elkNode.getY,
        width = elkNode.getWidth,
        height = elkNode.getHeight,
        nodeType = node.nodeType
      )
    }

    // Extract edge routing (basic version - just endpoints)
    val positionedEdges = dag.edges.map { edge =>
      PositionedEdge(
        id = edge.id,
        source = edge.source,
        target = edge.target,
        routingPoints = Seq.empty // For now, let Sprotty handle routing
      )
    }

    LayoutResult(
      nodes = positionedNodes,
      edges = positionedEdges,
      width = root.getWidth,
      height = root.getHeight
    )
  end computeLayout

  private def computeNodeSize(node: GraphNode): (Double, Double) =
    node.nodeType match
      case NodeType.Simple =>
        val width = node.width.getOrElse(100.0)
        val height = node.height.getOrElse(50.0)
        (width, height)

      case NodeType.Table(rows, cols) =>
        val cellWidth = 80.0
        val cellHeight = 25.0
        val width = node.width.getOrElse(cols * cellWidth)
        val height = node.height.getOrElse(rows * cellHeight)
        (width, height)

end ELKLayoutEngine

/** Layout direction options */
enum LayoutDirection:
  case Down, Up, Left, Right
end LayoutDirection

/** Result of layout computation */
case class LayoutResult(
    nodes: Seq[PositionedNode],
    edges: Seq[PositionedEdge],
    width: Double,
    height: Double
)

/** A node with computed position and dimensions */
case class PositionedNode(
    id: String,
    label: String,
    x: Double,
    y: Double,
    width: Double,
    height: Double,
    nodeType: NodeType
)

/** An edge with optional routing points */
case class PositionedEdge(
    id: String,
    source: String,
    target: String,
    routingPoints: Seq[(Double, Double)]
)
