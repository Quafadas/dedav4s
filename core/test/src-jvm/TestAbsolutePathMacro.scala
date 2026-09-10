package viz.macros

import scala.quoted.*

/** Computes an absolute path (as a compile-time literal) from a path relative to the project root, so that
  * `VegaPlot.absolutePath` can be exercised in tests with an actual absolute path regardless of the checkout location.
  */
transparent inline def testAbsolutePath(inline relative: String): String =
  ${ TestAbsolutePathImpl.impl('relative) }

private object TestAbsolutePathImpl:
  def impl(relativeE: Expr[String])(using Quotes): Expr[String] =
    val relative = relativeE.valueOrAbort
    val root = sys.env.getOrElse("MILL_WORKSPACE_ROOT", os.pwd.toString)
    Expr((os.Path(root) / os.RelPath(relative)).toString)
  end impl
end TestAbsolutePathImpl
