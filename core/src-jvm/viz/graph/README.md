# Graph Visualization with ELK and Sprotty

This module provides static directed acyclic graph (DAG) visualization using ELK (Eclipse Layout Kernel) for layout computation and Sprotty-compatible output for rendering.

## Features

- ✅ **DAG Model** with cycle detection
- ✅ **ELK Layout Engine** with configurable direction (Down, Up, Left, Right)
- ✅ **Sprotty JSON Export** for browser rendering
- ✅ **HTML Generation** with embedded SVG visualization
- ✅ **Node Types**: Simple nodes and table nodes
- ✅ **Customizable Spacing** for nodes and layers

## Quick Start

### Simple Graph

```scala
import viz.graph.*

// Create a simple graph
val viz = GraphViz.simple(
  "Start" -> "Process",
  "Process" -> "End"
)

// Export to HTML
viz.saveHtml(os.pwd / "graph.html", "My Graph")

// Or get JSON
val json = viz.toJson
```

### Custom DAG

```scala
import viz.graph.*

// Define nodes
val nodes = Seq(
  GraphNode("input", "Input"),
  GraphNode("process", "Process"),
  GraphNode("output", "Output")
)

// Define edges
val edges = Seq(
  GraphEdge("e1", "input", "process"),
  GraphEdge("e2", "process", "output")
)

// Create DAG
val dag = DAG(nodes, edges)

// Validate (checks for cycles)
dag.validate() match
  case Left(error) => println(s"Invalid DAG: $error")
  case Right(_) => println("Valid DAG")

// Visualize
val viz = GraphViz.visualize(
  dag,
  direction = LayoutDirection.Down,
  nodeSpacing = 20.0,
  layerSpacing = 50.0
)
```

### Table Nodes

```scala
import viz.graph.*

val nodes = Seq(
  GraphNode(
    "data",
    "Data Table",
    NodeType.Table(rows = 3, columns = 2),
    width = Some(160.0),
    height = Some(75.0)
  )
)

val dag = DAG(nodes, Seq.empty)
val viz = GraphViz.visualize(dag)
```

## Layout Directions

```scala
// Vertical (default)
GraphViz.visualize(dag, LayoutDirection.Down)

// Horizontal
GraphViz.visualize(dag, LayoutDirection.Right)

// Other options
LayoutDirection.Up
LayoutDirection.Left
```

## Output Formats

### HTML with Embedded SVG

```scala
val html = viz.toHtml("My Graph")
os.write.over(os.pwd / "graph.html", html)
```

### JSON (Sprotty-compatible)

```scala
val json = viz.toJson
val jsonString = ujson.write(json, indent = 2)
```

## Architecture

1. **Backend (Scala/JVM)**
   - Construct DAG model
   - Compute layout using ELK
   - Export to Sprotty-compatible JSON

2. **Frontend (HTML/JS)**
   - Load pre-positioned graph
   - Render as SVG
   - Static visualization (no interactivity required)

## Dependencies

- ELK 0.11.0 (Eclipse Layout Kernel)
- Xtext libraries (for ELK)
- upickle (for JSON handling)

## Examples

See `viz.graph.examples.GraphExamples` for complete examples including:
- Simple linear graphs
- Tree structures
- Diamond DAG patterns
- Complex workflows with table nodes

Run examples:

```scala
import viz.graph.examples.GraphExamples

// Generate example HTML files
GraphExamples.saveExamples(os.pwd / "graph-examples")
```

## Limitations

- **DAGs only**: Graphs must be acyclic (cycles are detected and rejected)
- **Static rendering**: No client-side layout computation or interactivity
- **JVM only**: Layout computation requires JVM (uses native Java ELK libraries)

## Future Enhancements

- [ ] Custom node rendering styles
- [ ] Edge labels
- [ ] Edge routing with bend points
- [ ] Interactive pan/zoom
- [ ] Export to other formats (PNG, PDF)
- [ ] More layout algorithms beyond layered
