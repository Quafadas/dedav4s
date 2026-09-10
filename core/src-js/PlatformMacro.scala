package viz

import scala.quoted.Expr
import scala.quoted.*

object VegaPlotJvm:
  def pwdImpl(fileNameE: Expr[String])(using Quotes): Expr[Any] = ???
  def absolutePathImpl(fileNameE: Expr[String])(using Quotes): Expr[Any] = ???
  def relativeToSourceImpl(fileNameE: Expr[String])(using Quotes): Expr[Any] = ???
  def projectRootImpl(fileNameE: Expr[String])(using Quotes): Expr[Any] = ???
end VegaPlotJvm
