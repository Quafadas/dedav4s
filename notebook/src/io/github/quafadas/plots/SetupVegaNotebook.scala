package io.github.quafadas.plots

/** Vega setup for Jupyter notebooks running the almond kernel.
  *
  * {{{
  *  import io.github.quafadas.plots.SetupVegaNotebook.{*, given}
  * }}}
  *
  * @see
  *   [[viz.notebook.NotebookTargets.almond]]
  */
object SetupVegaNotebook:
  export SetupVega.{*, given}
  export viz.notebook.NotebookTargets.almond
end SetupVegaNotebook
