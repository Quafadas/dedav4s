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


lazy object SetupVegaServer:
  export SetupVega.{*, given}
  export viz.PlotTargets.websocket

end SetupVegaServer


/**
 * Provides a convenient single-import setup for Vega visualization capabilities.
 *
 * This object aggregates and re-exports all essential types and implicits needed
 * to create Vega visualizations, but does not include a rendering target (e.g., browser, file).
 * Users should import from this object when they want to define Vega specifications
 * and will configure the output target separately.
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