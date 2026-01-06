package viz

import scala.quoted.Expr
import scala.quoted.*

object VegaPlotJvm:
  def pwdImpl(fileNameE: Expr[String])(using Quotes): Expr[Any] = ???