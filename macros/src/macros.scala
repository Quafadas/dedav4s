  package viz.macros

  import scala.quoted.Expr
  import scala.quoted.*

  object Macros:

    transparent inline def fromString(inline specContent: String, inline obj: Object): Any = ${ readSpecFromString('specContent, 'obj) }

    private def readSpecFromString(csvContentExpr: Expr[String], optsExpr: Expr[Object])(using Quotes) =
      import quotes.reflect.*

      ???



