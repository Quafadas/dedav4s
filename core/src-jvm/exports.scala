package io.github.quafadas.plots

/** Provides Vega-Lite plotting setup utilities for REPL environments.
  *
  * This object contains configurations and given instances needed to render Vega-Lite visualizations in Scala REPL
  * sessions.
  *
  * Usage:
  * {{{
  *  import io.github.quafadas.plots.SetupVegaBrowser.{*, given}
  * }}}
  *
  * @see
  *   [[https://vega.github.io/vega-lite/ Vega-Lite documentation]]
  */
object SetupVegaBrowser:
  export SetupVega.{*, given}
  export viz.PlotTargets.desktopBrowser

end SetupVegaBrowser

/** Sends a Vega-Lite specification to a local WebSocket server for visualization.
  *
  * Before calling this method, you must start the WebSocket server by running the following command in a separate
  * terminal:
  *
  * {{{
  * cs launch io.github.quafadas:dedav4s_3:0.10.0 -M viz.websockets.serve -- 8085
  * }}}
  *
  * After the server is running, visualizations will be displayed at http://localhost:8085 and are updated via a
  * websocket message every time the plot method is called, with eith the `websocket` or `publishToPort` plot target
  * given in scope.
  */
lazy object SetupVegaServer:
  export SetupVega.{*, given}
  export viz.PlotTargets.websocket

end SetupVegaServer

/** Provides a convenient single-import setup for Vega visualization capabilities.
  *
  * This object aggregates and re-exports all essential types and implicits needed to create Vega visualizations, but
  * does not include a rendering target (e.g., browser, file). Users should import from this object when they want to
  * define Vega specifications and will configure the output target separately.
  *
  * Exports include:
  *   - `Plottable` instances for converting data types to visualizations
  *   - `vegaFlavour` for specifying Vega vs Vega-Lite
  *   - Circe JSON given instances for serialization
  *   - `VegaPlot` macro for compile-time spec validation
  *   - `VegaSpec` type for representing Vega specifications
  *   - Circe `Json` type and `json` string interpolator for JSON construction
  */

object SetupVega:
  export viz.Plottable.*
  export viz.vegaFlavour
  export viz.NtCirce.given
  export viz.macros.VegaPlot
  export viz.vega.VegaSpec
  export io.circe.Json
  export io.circe.literal.json
end SetupVega

object SetupElkGraph:
  export viz.Plottable.*
  export viz.graphFlavour
  export viz.NtCirce.given
end SetupElkGraph
