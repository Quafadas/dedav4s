package viz.macros

import scala.quoted.*
import ujson.*

object VegaPlot:
  
  /** 
   * Macro to generate type-safe helpers from a Vega-Lite JSON spec file.
   * The macro reads the JSON at compile time and generates helper methods.
   * 
   * Usage:
   *   val spec = VegaPlot.fromFile("pie.vl.json")
   *   import spec.mods.*
   *   spec.plot(
   *     title("New Title"),
   *     width("500")
   *   )
   */
  transparent inline def fromFile(inline path: String): VegaPlotSpec = 
    ${ fromFileImpl('path) }

  private def fromFileImpl(pathExpr: Expr[String])(using Quotes): Expr[VegaPlotSpec] =
    import quotes.reflect.*
    
    // Extract the path string at compile time
    val path = pathExpr.valueOrAbort
    
    // Read the JSON file from resources at compile time
    val jsonContent = try {
      var stream: java.io.InputStream = null
      try {
        stream = getClass.getResourceAsStream(s"/$path")
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
    
    // For the POC, return a basic implementation
    // In a full implementation, we would generate the mods methods dynamically
    '{
      new VegaPlotSpec {
        val specPath = $pathExpr
        
        def plot(mods: (ujson.Value => Unit)*): ujson.Value = {
          var stream: java.io.InputStream = null
          try {
            stream = getClass.getResourceAsStream("/" + specPath)
            if (stream == null) {
              throw new RuntimeException(s"Resource not found: $specPath")
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
          // Simple helpers for common fields
          def title(s: String): ujson.Value => Unit = 
            spec => spec("title") = ujson.Str(s)
          
          def title(json: ujson.Value): ujson.Value => Unit = 
            spec => spec("title") = json
          
          def width(i: Int): ujson.Value => Unit = 
            spec => spec("width") = ujson.Num(i)
          
          def width(json: ujson.Value): ujson.Value => Unit = 
            spec => spec("width") = json
          
          def height(i: Int): ujson.Value => Unit = 
            spec => spec("height") = ujson.Num(i)
          
          def height(json: ujson.Value): ujson.Value => Unit = 
            spec => spec("height") = json
          
          def description(s: String): ujson.Value => Unit = 
            spec => spec("description") = ujson.Str(s)
          
          def description(json: ujson.Value): ujson.Value => Unit = 
            spec => spec("description") = json
          
          // Nested object example: encoding
          object encoding {
            def apply(json: ujson.Value): ujson.Value => Unit = 
              spec => spec("encoding") = json
            
            object theta {
              def apply(json: ujson.Value): ujson.Value => Unit = 
                spec => spec("encoding")("theta") = json
              
              def field(s: String): ujson.Value => Unit = 
                spec => spec("encoding")("theta")("field") = ujson.Str(s)
              
              def `type`(s: String): ujson.Value => Unit = 
                spec => spec("encoding")("theta")("type") = ujson.Str(s)
              
              def stack(b: Boolean): ujson.Value => Unit = 
                spec => spec("encoding")("theta")("stack") = ujson.Bool(b)
            }
            
            object color {
              def apply(json: ujson.Value): ujson.Value => Unit = 
                spec => spec("encoding")("color") = json
              
              def field(s: String): ujson.Value => Unit = 
                spec => spec("encoding")("color")("field") = ujson.Str(s)
              
              def `type`(s: String): ujson.Value => Unit = 
                spec => spec("encoding")("color")("type") = ujson.Str(s)
              
              def legend(json: ujson.Value): ujson.Value => Unit = 
                spec => spec("encoding")("color")("legend") = json
            }
          }
          
          // Structural array example: layer
          object layer {
            def apply(idx: Int) = new LayerElement(idx)
            
            class LayerElement(idx: Int) {
              def mark(json: ujson.Value): ujson.Value => Unit = 
                spec => spec("layer")(idx)("mark") = json
              
              object mark {
                def apply(json: ujson.Value): ujson.Value => Unit = 
                  spec => spec("layer")(idx)("mark") = json
                
                def `type`(s: String): ujson.Value => Unit = 
                  spec => spec("layer")(idx)("mark")("type") = ujson.Str(s)
                
                def outerRadius(json: ujson.Value): ujson.Value => Unit = 
                  spec => spec("layer")(idx)("mark")("outerRadius") = json
                
                def tooltip(b: Boolean): ujson.Value => Unit = 
                  spec => spec("layer")(idx)("mark")("tooltip") = ujson.Bool(b)
                
                def fontSize(json: ujson.Value): ujson.Value => Unit = 
                  spec => spec("layer")(idx)("mark")("fontSize") = json
                
                def radius(json: ujson.Value): ujson.Value => Unit = 
                  spec => spec("layer")(idx)("mark")("radius") = json
              }
              
              def encoding(json: ujson.Value): ujson.Value => Unit = 
                spec => spec("layer")(idx)("encoding") = json
              
              object encoding {
                def apply(json: ujson.Value): ujson.Value => Unit = 
                  spec => spec("layer")(idx)("encoding") = json
                
                object text {
                  def apply(json: ujson.Value): ujson.Value => Unit = 
                    spec => spec("layer")(idx)("encoding")("text") = json
                  
                  def field(s: String): ujson.Value => Unit = 
                    spec => spec("layer")(idx)("encoding")("text")("field") = ujson.Str(s)
                  
                  def `type`(s: String): ujson.Value => Unit = 
                    spec => spec("layer")(idx)("encoding")("text")("type") = ujson.Str(s)
                }
              }
            }
          }
          
          // Data helper
          object data {
            def apply(json: ujson.Value): ujson.Value => Unit = 
              spec => spec("data") = json
            
            def values(json: ujson.Value): ujson.Value => Unit = 
              spec => spec("data")("values") = json
          }
        }
      }
    }

// Base trait for the generated spec
trait VegaPlotSpec:
  def specPath: String
  def plot(mods: (ujson.Value => Unit)*): ujson.Value
  def mods: AnyRef

