package viz

import scala.quoted.Expr
import scala.quoted.*

object VegaPlotJvm:
  def absolutePathImpl(filePathE: Expr[String])(using Quotes): Expr[Any] = ???
  def relativeToSourceImpl(pathE: Expr[String])(using Quotes): Expr[Any] = ???
  def projectRootImpl(pathE: Expr[String])(using Quotes): Expr[Any] = ???
end VegaPlotJvm
