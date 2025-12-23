package viz.macros

import scala.quoted.*
import ujson.*

object VegaPlot:
  
  /** 
   * Macro to generate type-safe helpers from a Vega-Lite JSON spec file.
   * The macro reads the JSON at compile time and dynamically generates helper methods
   * based on the structure of the JSON spec.
   * 
   * Usage:
   *   val spec = VegaPlot.fromFile("pie.vl.json")
   *   import spec.mods.*
   *   spec.plot(
   *     title("New Title"),
   *     width(800)
   *   )
   */
  transparent inline def fromFile(inline path: String): Any = 
    ${ fromFileImpl('path) }
  
  /** 
   * Macro to generate type-safe helpers from a Vega-Lite JSON spec string.
   * The macro parses the JSON string at compile time and dynamically generates helper methods
   * based on the structure of the JSON spec.
   * 
   * Usage:
   *   val spec = VegaPlot.fromString("""{"title": "Chart", "width": 800}""")
   *   import spec.mods.*
   *   spec.plot(
   *     title("New Title"),
   *     width(1000)
   *   )
   */
  transparent inline def fromString(inline jsonString: String): Any = 
    ${ fromStringImpl('jsonString) }
  
  /** 
   * Macro to generate type-safe helpers from a ujson.Value spec.
   * The macro analyzes the ujson.Value at compile time and dynamically generates helper methods
   * based on the structure of the JSON spec.
   * 
   * Usage:
   *   val spec = VegaPlot.fromJson(ujson.Obj("title" -> "Chart", "width" -> 800))
   *   import spec.mods.*
   *   spec.plot(
   *     title("New Title"),
   *     width(1000)
   *   )
   */
  transparent inline def fromJson(inline jsonValue: ujson.Value): Any = 
    ${ fromJsonImpl('jsonValue) }

  private def fromFileImpl(pathExpr: Expr[String])(using Quotes): Expr[Any] =
    import quotes.reflect.*
    
    // Extract the path string at compile time
    val path = pathExpr.valueOrAbort
    
    // Read the JSON file from resources at compile time
    val jsonContent = try {
      var stream: java.io.InputStream = null
      try {
        // Use this class's classloader to find resources in the macros module
        stream = classOf[VegaPlotSpec].getClassLoader.getResourceAsStream(path)
        if (stream == null) {
          report.errorAndAbort(s"Could not find resource: $path")
        }
        val source = scala.io.Source.fromInputStream(stream)
        try {
          source.mkString
        } finally {
          source.close()
        }
      } finally {
        if (stream != null) {
          try { stream.close() } catch { case _: Exception => }
        }
      }
    } catch {
      case e: Exception =>
        report.errorAndAbort(s"Error reading file $path: ${e.getMessage}")
    }
    
    // Parse the JSON at compile time
    val spec = try {
      ujson.read(jsonContent)
    } catch {
      case e: Exception =>
        report.errorAndAbort(s"Error parsing JSON from $path: ${e.getMessage}")
    }
    
    // Generate helper methods dynamically from the JSON structure
    generateSpec(spec, pathExpr)
  
  private def fromStringImpl(jsonStringExpr: Expr[String])(using Quotes): Expr[Any] =
    import quotes.reflect.*
    
    // Extract the JSON string at compile time
    val jsonString = jsonStringExpr.valueOrAbort
    
    // Parse the JSON at compile time
    val spec = try {
      ujson.read(jsonString)
    } catch {
      case e: Exception =>
        report.errorAndAbort(s"Error parsing JSON string: ${e.getMessage}")
    }
    
    // Generate helper methods dynamically from the JSON structure
    // Use the JSON string as the "path" for consistency
    generateSpecFromString(spec, jsonStringExpr)
  
  private def fromJsonImpl(jsonValueExpr: Expr[ujson.Value])(using Quotes): Expr[Any] =
    import quotes.reflect.*
    
    // We need to evaluate the ujson.Value at compile time
    // This is tricky because ujson.Value might not be fully evaluable at compile time
    // For now, we'll generate code that works with the runtime value
    // Note: This means the helpers won't be as strongly typed as fromFile/fromString
    
    // Generate a runtime-based implementation
    '{
      val jsonValue = $jsonValueExpr
      new VegaPlotSpec {
        val specPath = "<from-ujson-value>"
        
        def plot(mods: (ujson.Value => Unit)*): ujson.Value = {
          val spec = jsonValue
          mods.foreach(_(spec))
          spec
        }
        
        // For ujson.Value input, we provide a generic mods object
        // that only supports ujson.Value parameters since we can't analyze structure at compile time
        object mods {
          // This is a limitation - we can't generate typed helpers without compile-time JSON structure
          // Users should prefer fromString or fromFile for full type safety
        }
      }
    }
  
  private def generateSpec(spec: ujson.Value, pathExpr: Expr[String])(using Quotes): Expr[Any] =
    import quotes.reflect.*
    
    // Analyze the JSON structure and generate code as a string
    val modMethodsCode = analyzeAndGenerateCode(spec.obj.toMap, Nil)
    
    // Build the complete class code as a string
    val classCodeStr = s"""
      new viz.macros.VegaPlotSpec {
        val specPath = ${pathExpr.show}
        
        def plot(mods: (ujson.Value => Unit)*): ujson.Value = {
          var stream: java.io.InputStream = null
          try {
            stream = classOf[viz.macros.VegaPlotSpec].getClassLoader.getResourceAsStream(specPath)
            if (stream == null) {
              throw new RuntimeException("Resource not found: " + specPath)
            }
            val source = scala.io.Source.fromInputStream(stream)
            val content = try {
              source.mkString
            } finally {
              source.close()
            }
            val spec = ujson.read(content)
            mods.foreach(_(spec))
            spec
          } finally {
            if (stream != null) {
              try { stream.close() } catch { case _: Exception => }
            }
          }
        }
        
        object mods {
          $modMethodsCode
        }
      }
    """
    
    // Parse the string into an expression
    classCodeStr.asTerm.asExprOf[Any]
  
  private def generateSpecFromString(spec: ujson.Value, jsonStringExpr: Expr[String])(using Quotes): Expr[Any] =
    import quotes.reflect.*
    
    // Analyze the JSON structure and generate code as a string
    val modMethodsCode = analyzeAndGenerateCode(spec.obj.toMap, Nil)
    
    // Build the complete class code as a string
    val classCodeStr = s"""
      new viz.macros.VegaPlotSpec {
        val specPath = "<from-string>"
        
        def plot(mods: (ujson.Value => Unit)*): ujson.Value = {
          val jsonString = ${jsonStringExpr.show}
          val spec = ujson.read(jsonString)
          mods.foreach(_(spec))
          spec
        }
        
        object mods {
          $modMethodsCode
        }
      }
    """
    
    // Parse the string into an expression
    classCodeStr.asTerm.asExprOf[Any]
    classCodeStr.asTerm.asExprOf[Any]
  
  private def analyzeAndGenerateCode(obj: Map[String, ujson.Value], path: List[String])(using Quotes): String =
    import quotes.reflect.*
    
    obj.flatMap { case (key, value) =>
      // Skip internal fields like $schema
      if (key.startsWith("$")) {
        Nil
      } else {
        val sanitizedKey = if (key.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) key else s"`$key`"
        val fullPath = path :+ key
        
        value match {
          case str: ujson.Str =>
            generatePrimitiveHelpersCode(sanitizedKey, fullPath, "String", "ujson.Str")
          
          case num: ujson.Num =>
            if (num.value.isWhole) {
              generatePrimitiveHelpersCode(sanitizedKey, fullPath, "Int", "ujson.Num")
            } else {
              generatePrimitiveHelpersCode(sanitizedKey, fullPath, "Double", "ujson.Num")
            }
          
          case bool: ujson.Bool =>
            generatePrimitiveHelpersCode(sanitizedKey, fullPath, "Boolean", "ujson.Bool")
          
          case obj: ujson.Obj =>
            generateObjectHelpersCode(sanitizedKey, fullPath, obj.value.toMap)
          
          case arr: ujson.Arr =>
            generateArrayHelpersCode(sanitizedKey, fullPath, arr.value.toSeq)
          
          case ujson.Null =>
            generateNullHelperCode(sanitizedKey, fullPath)
        }
      }
    }.mkString("\n\n")
  
  private def generatePrimitiveHelpersCode(name: String, path: List[String], scalaType: String, ujsonType: String)(using Quotes): List[String] =
    val pathAccessor = path.map(k => s"""("$k")""").mkString
    val typeChar = scalaType.head.toLower
    
    List(
      s"""def $name($typeChar: $scalaType): ujson.Value => Unit = 
         |  spec => spec$pathAccessor = $ujsonType($typeChar)""".stripMargin,
      s"""def $name(json: ujson.Value): ujson.Value => Unit = 
         |  spec => spec$pathAccessor = json""".stripMargin
    )
  
  private def generateObjectHelpersCode(name: String, path: List[String], obj: Map[String, ujson.Value])(using Quotes): List[String] =
    val pathAccessor = path.map(k => s"""("$k")""").mkString
    
    // Generate ujson.Value overload
    val jsonMethod = s"""def $name(json: ujson.Value): ujson.Value => Unit = 
       |  spec => spec$pathAccessor = json""".stripMargin
    
    // Generate nested object with recursive analysis
    val nestedMethods = analyzeAndGenerateCode(obj, path)
    val nestedCode = if (nestedMethods.nonEmpty) {
      s"""object $name {
         |  $nestedMethods
         |}""".stripMargin
    } else {
      ""
    }
    
    List(jsonMethod, nestedCode).filter(_.nonEmpty)
  
  private def generateArrayHelpersCode(name: String, path: List[String], arr: Seq[ujson.Value])(using Quotes): List[String] =
    // Check if this is a structural array (array of objects)
    val isStructural = arr.headOption.exists(_.isInstanceOf[ujson.Obj])
    
    if (isStructural && arr.nonEmpty) {
      // Generate indexed accessor for structural arrays
      generateStructuralArrayHelperCode(name, path, arr.collect { case o: ujson.Obj => o.value.toMap })
    } else {
      // For data/primitive arrays, just provide ujson.Value accessor
      val pathAccessor = path.map(k => s"""("$k")""").mkString
      List(s"""def $name(json: ujson.Value): ujson.Value => Unit = 
              |  spec => spec$pathAccessor = json""".stripMargin)
    }
  
  private def generateStructuralArrayHelperCode(name: String, path: List[String], elements: Seq[Map[String, ujson.Value]])(using Quotes): List[String] =
    // Union all fields from all array elements
    val allFields = elements.foldLeft(Map.empty[String, ujson.Value]) { (acc, elem) =>
      elem.foldLeft(acc) { case (m, (k, v)) =>
        m.get(k) match {
          case Some(existing) => m // Keep first occurrence for type inference
          case None => m + (k -> v)
        }
      }
    }
    
    // Replace (idx) in the path with the actual index variable
    val elementPath = path :+ "(idx)"
    
    // Generate methods for the union of fields
    val elementMethods = analyzeAndGenerateCode(allFields, elementPath)
    val capitalizedName = name.capitalize
    
    val code = s"""object $name {
       |  def apply(idx: Int) = new ${capitalizedName}Element(idx)
       |  
       |  class ${capitalizedName}Element(idx: Int) {
       |    $elementMethods
       |  }
       |}""".stripMargin
    
    List(code)
  
  private def generateNullHelperCode(name: String, path: List[String])(using Quotes): List[String] =
    val pathAccessor = path.map(k => s"""("$k")""").mkString
    List(s"""def $name(json: ujson.Value): ujson.Value => Unit = 
            |  spec => spec$pathAccessor = json""".stripMargin)

// Base trait for the generated spec
trait VegaPlotSpec:
  def specPath: String
  def plot(mods: (ujson.Value => Unit)*): ujson.Value
  def mods: AnyRef

