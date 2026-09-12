package viz.macros

import scala.quoted.*

/** Computes an absolute path (as a compile-time literal) from a path relative to the project root, so that
  * `VegaPlot.absolutePath` can be exercised in tests with an actual absolute path regardless of the checkout location.
  *
  * Delegates to the same root discovery `VegaPlot.projectRoot` uses, rather than guessing from an environment variable
  * or the compiler's working directory.
  */
transparent inline def testAbsolutePath(inline relative: String): String =
  ${ TestAbsolutePathImpl.impl('relative) }

private object TestAbsolutePathImpl:
  def impl(relativeE: Expr[String])(using Quotes): Expr[String] =
    Expr(viz.SourceAnchor.projectRoot(relativeE.valueOrAbort).absolutePath.toString)
  end impl
end TestAbsolutePathImpl
