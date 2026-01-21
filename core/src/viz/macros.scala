package viz.macros

import scala.quoted.Expr
import scala.quoted.*
import io.circe.Json
import io.circe.JsonObject
import io.circe.parser.parse
import io.circe.optics.JsonPath
import io.circe.optics.JsonPath.*
import monocle.Optional
import viz.*
import viz.vega.VegaSpec

/** Type alias for spec modifier functions using circe Json */
type SpecMod = Json => Json

/** Accessor for string fields in a Vega/Vega-Lite spec.
  *
  * Provides type-safe modification of string-valued JSON fields.
  *
  * @example
  *   {{{spec.build(_.title := "New Title")}}}
  *
  * @param path
  *   The JSON path to this field as a list of field names
  */
class StringField(path: List[String]):
  private def optic = path
    .foldLeft(root: JsonPath) { (p, f) =>
      if f.forall(_.isDigit) then p.index(f.toInt)
      else p.selectDynamic(f)
    }
    .json
  def apply(s: String): SpecMod = optic.replace(Json.fromString(s))
  def apply(j: Json): SpecMod = optic.replace(j)
  def apply(obj: JsonObject): SpecMod = optic.replace(Json.fromJsonObject(obj))

  /** Replace the field value with a string */
  def :=(s: String): SpecMod = apply(s)

  /** Replace the field value with arbitrary JSON */
  def :=(j: Json): SpecMod = apply(j)

  /** Replace the field value with a JSON object */
  def :=(obj: JsonObject): SpecMod = apply(obj)

  /** Deep merge JSON into this field. For string fields, this effectively replaces the value. */
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))

  /** Deep merge a JSON object into this field */
  def +=(obj: JsonObject): SpecMod = optic.modify(existing => existing.deepMerge(Json.fromJsonObject(obj)))
end StringField

/** Accessor for numeric fields in a Vega/Vega-Lite spec.
  *
  * Provides type-safe modification of number-valued JSON fields.
  *
  * @example
  *   {{{spec.build(_.width := 800, _.height := 600.5)}}}
  *
  * @param path
  *   The JSON path to this field as a list of field names
  */
class NumField(path: List[String]):
  private def optic = path
    .foldLeft(root: JsonPath) { (p, f) =>
      if f.forall(_.isDigit) then p.index(f.toInt)
      else p.selectDynamic(f)
    }
    .json
  def apply(n: Int): SpecMod = optic.replace(Json.fromInt(n))
  def apply(n: Double): SpecMod = optic.replace(Json.fromDoubleOrNull(n))
  def apply(j: Json): SpecMod = optic.replace(j)

  /** Replace the field value with an integer */
  def :=(n: Int): SpecMod = apply(n)

  /** Replace the field value with a double */
  def :=(n: Double): SpecMod = apply(n)

  /** Replace the field value with arbitrary JSON */
  def :=(j: Json): SpecMod = apply(j)

  /** Deep merge JSON into this field. For numeric fields, this effectively replaces the value. */
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))

  /** Deep merge a JSON object into this field */
  def +=(obj: JsonObject): SpecMod = optic.modify(existing => existing.deepMerge(Json.fromJsonObject(obj)))
end NumField

/** Accessor for boolean fields in a Vega/Vega-Lite spec.
  *
  * Provides type-safe modification of boolean-valued JSON fields.
  *
  * @example
  *   {{{spec.build(_.autosize := false)}}}
  *
  * @param path
  *   The JSON path to this field as a list of field names
  */
class BoolField(path: List[String]):
  private def optic = path
    .foldLeft(root: JsonPath) { (p, f) =>
      if f.forall(_.isDigit) then p.index(f.toInt)
      else p.selectDynamic(f)
    }
    .json
  def apply(b: Boolean): SpecMod = optic.replace(Json.fromBoolean(b))
  def apply(j: Json): SpecMod = optic.replace(j)

  /** Replace the field value with a boolean */
  def :=(b: Boolean): SpecMod = apply(b)

  /** Replace the field value with arbitrary JSON */
  def :=(j: Json): SpecMod = apply(j)

  /** Deep merge JSON into this field. For boolean fields, this effectively replaces the value. */
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))

  /** Deep merge a JSON object into this field */
  def +=(obj: JsonObject): SpecMod = optic.modify(existing => existing.deepMerge(Json.fromJsonObject(obj)))
end BoolField

/** Accessor for array fields in a Vega/Vega-Lite spec.
  *
  * Provides type-safe modification of array-valued JSON fields. Unlike other field types, the `+=` operator on arrays
  * appends elements rather than deep merging.
  *
  * @example
  *   {{{// Replace the entire array spec.build(_.data.values := json"[{\"a\": 1}, {\"a\": 2}]")
  *
  * // Append a single element spec.build(_.data.values += json"{\"a\": 3}")
  *
  * // Append multiple elements spec.build(_.signals += Vector(json"{\"name\": \"sig1\"}", json"{\"name\": \"sig2\"}"))
  *
  * // Access first element's nested field spec.build(_.data.head.values := json"[{\"a\": 1}]") }}}
  *
  * @param path
  *   The JSON path to this field as a list of field names
  * @param headAccessor
  *   Optional accessor for the first element of the array (if available)
  */
/** Accessor for array fields in a Vega/Vega-Lite spec.
  *
  * Provides type-safe modification of array-valued JSON fields. Unlike other field types, the `+=` operator on arrays
  * appends elements rather than deep merging.
  *
  * @example
  *   {{{// Replace the entire array spec.build(_.data.values := json"[{\"a\": 1}, {\"a\": 2}]")
  *
  * // Append a single element spec.build(_.data.values += json"{\"a\": 3}")
  *
  * // Append multiple elements spec.build(_.signals += Vector(json"{\"name\": \"sig1\"}", json"{\"name\": \"sig2\"}"))
  *
  * // Access first element's nested field spec.build(_.data.head.values := json"[{\"a\": 1}]")
  *
  * // Access element at index 2 spec.build(_.data(2).values := json"[{\"a\": 1}]") }}}
  *
  * @param path
  *   The JSON path to this field as a list of field names
  * @param headAccessor
  *   Optional accessor for the first element of the array (if available)
  * @param elementAccessorFactory
  *   Optional factory function to create accessors for array elements at any index
  */
class ArrField(
    path: List[String],
    headAccessor: Option[Any] = None,
    elementAccessorFactory: Option[Int => Any] = None
) extends Selectable:
  private def optic = path
    .foldLeft(root: JsonPath) { (p, f) =>
      if f.forall(_.isDigit) then p.index(f.toInt)
      else p.selectDynamic(f)
    }
    .json

  /** Access an element at a specific index in the array.
    *
    * Returns an accessor for the element at the given index. The element type is determined by the first element in the
    * array at compile time, so all elements are assumed to have the same structure.
    *
    * @param index
    *   The zero-based index of the element to access
    * @return
    *   An accessor for the element at the specified index with the same type as `.head`
    * @example
    *   {{{spec.build(_.data(1).name := "updated")}}}
    */
  def apply(index: Int): Any =
    elementAccessorFactory
      .map(_(index))
      .getOrElse(
        throw new NoSuchElementException(
          "Array element accessor not available - array is empty or contains non-object elements"
        )
      )

  def apply(j: Json): SpecMod = optic.replace(j)
  def apply(arr: Vector[Json]): SpecMod = optic.replace(Json.fromValues(arr))

  /** Replace the entire array with arbitrary JSON */
  def :=(j: Json): SpecMod = apply(j)

  /** Replace the entire array with a Vector of JSON values */
  def :=(arr: Vector[Json]): SpecMod = apply(arr)

  /** Append a single JSON element to the array.
    *
    * If the current value is not an array, creates a new array with this element.
    */
  def +=(j: Json): SpecMod = optic.modify { existing =>
    existing.asArray match
      case Some(arr) => Json.fromValues(arr :+ j)
      case None      => Json.arr(j) // If not an array, create new array with this element
  }

  /** Append multiple JSON elements to the array */
  def +=(arr: Vector[Json]): SpecMod = optic.modify { existing =>
    existing.asArray match
      case Some(existingArr) => Json.fromValues(existingArr ++ arr)
      case None              => Json.fromValues(arr)
  }

  /** Append a JSON object element to the array */
  def +=(obj: JsonObject): SpecMod = +=(Json.fromJsonObject(obj))

  def selectDynamic(name: String): Any =
    if name == "head" then
      headAccessor.getOrElse(
        throw new NoSuchElementException(
          "Array element accessor not available - array is empty or contains non-object elements"
        )
      )
    else throw new NoSuchElementException(s"No such field: $name")
end ArrField

/** Accessor for null-valued fields in a Vega/Vega-Lite spec.
  *
  * Provides modification of fields that are initially null in the spec.
  *
  * @param path
  *   The JSON path to this field as a list of field names
  */
class NullField(path: List[String]):
  private def optic = path
    .foldLeft(root: JsonPath) { (p, f) =>
      if f.forall(_.isDigit) then p.index(f.toInt)
      else p.selectDynamic(f)
    }
    .json
  def apply(j: Json): SpecMod = optic.replace(j)

  /** Replace the null value with arbitrary JSON */
  def :=(j: Json): SpecMod = apply(j)

  /** Deep merge JSON into this field */
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))

  /** Deep merge a JSON object into this field */
  def +=(obj: JsonObject): SpecMod = optic.modify(existing => existing.deepMerge(Json.fromJsonObject(obj)))
end NullField

/** Accessor for object fields in a Vega/Vega-Lite spec.
  *
  * Provides type-safe modification of object-valued JSON fields. Extends `Selectable` to allow compile-time checked
  * access to nested fields via dot notation.
  *
  * The `:=` operator replaces the entire object, while `+=` performs a deep merge preserving existing fields.
  *
  * @example
  *   {{{// Replace entire object spec.build(_.title := json"{\"text\": \"New\", \"fontSize\": 20}")
  *
  * // Deep merge - adds/updates fields while preserving others spec.build(_.title += json"{\"color\": \"red\"}")
  *
  * // Access nested fields with compile-time checking spec.build(_.title.text := "New Title", _.title.fontSize := 20)
  * }}}
  *
  * @param path
  *   The JSON path to this field as a list of field names
  * @param fieldMap
  *   Map of nested field names to their accessor objects (used by `selectDynamic`)
  */
class ObjField(path: List[String], fieldMap: Map[String, Any]) extends Selectable:
  private def optic = path
    .foldLeft(root: JsonPath) { (p, f) =>
      if f.forall(_.isDigit) then p.index(f.toInt)
      else p.selectDynamic(f)
    }
    .json
  def apply(j: Json): SpecMod = optic.replace(j)
  def apply(obj: JsonObject): SpecMod = optic.replace(Json.fromJsonObject(obj))

  /** Replace the entire object with arbitrary JSON */
  def :=(j: Json): SpecMod = apply(j)

  /** Replace the entire object with a JSON object */
  def :=(obj: JsonObject): SpecMod = apply(obj)

  /** Deep merge a JSON value into this object.
    *
    * Existing fields not present in `j` are preserved. Fields present in both are overwritten by `j`.
    */
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))

  /** Deep merge a JSON object into this object.
    *
    * Existing fields not present in `obj` are preserved. Fields present in both are overwritten by `obj`.
    */
  def +=(obj: JsonObject): SpecMod = optic.modify(existing => existing.deepMerge(Json.fromJsonObject(obj)))
  def selectDynamic(name: String): Any = fieldMap(name)
end ObjField

object VegaPlot:

  transparent inline def pwd(inline fileName: String): Any =
    ${ VegaPlotJvm.pwdImpl('fileName) }

  transparent inline def fromResource(inline resourcePath: String): Any =
    ${ fromResourceImpl('resourcePath) }

  private def fromResourceImpl(resourcePathE: Expr[String])(using Quotes): Expr[Any] =
    import quotes.reflect.*
    val resourcePath = resourcePathE.valueOrAbort
    val specContent = scala.io.Source.fromResource(resourcePath).mkString
    VegaPlotMacroImpl.fromStringImpl(Expr(specContent))
  end fromResourceImpl

  transparent inline def fromString(inline specContent: String): Any =
    ${ VegaPlotMacroImpl.fromStringImpl('specContent) }
end VegaPlot

object VegaPlotMacroImpl:
  // Index for accessing the first element in an array
  private val FirstElementIndex = "0"

  def fromStringImpl(specContentExpr: Expr[String])(using Quotes): Expr[Any] =
    import quotes.reflect.*

    val jsonString = specContentExpr.valueOrAbort
    val parseResult = parse(jsonString)

    // Parse JSON and abort macro expansion on failure
    val circeJson = parseResult match
      case Left(error) =>
        // Provide detailed error message including location
        val errorMsg = s"Invalid JSON: ${error.message}"
        report.errorAndAbort(errorMsg)
      case Right(json) =>
        json

    // Helper function to build an accessor for an array element at a runtime-specified index
    def buildAccessorForIndex(json: Json, basePath: List[String], indexExpr: Expr[Int])(using
        Quotes
    ): Expr[Any] =
      import quotes.reflect.*
      // Build the path with the runtime index
      val pathWithIndexExpr = '{ ${ Expr(basePath) } :+ $indexExpr.toString }

      // Recursively build the accessor structure based on the JSON element
      json match
        case j if j.isString =>
          '{ new StringField($pathWithIndexExpr) }
        case j if j.isNumber =>
          '{ new NumField($pathWithIndexExpr) }
        case j if j.isBoolean =>
          '{ new BoolField($pathWithIndexExpr) }
        case j if j.isArray =>
          '{ new ArrField($pathWithIndexExpr, None, None) }
        case j if j.isNull =>
          '{ new NullField($pathWithIndexExpr) }
        case j if j.isObject =>
          val obj = j.asObject.get
          val fields = obj.toList

          if fields.isEmpty then '{ new ObjField($pathWithIndexExpr, Map.empty) }
          else
            // Build nested accessors for each field
            val nestedAccessors: List[(String, Expr[Any])] = fields.map { case (name, value) =>
              val nestedPath = '{ $pathWithIndexExpr :+ ${ Expr(name) } }
              val nestedExpr = value match
                case v if v.isString  => '{ new StringField($nestedPath) }
                case v if v.isNumber  => '{ new NumField($nestedPath) }
                case v if v.isBoolean => '{ new BoolField($nestedPath) }
                case v if v.isArray   => '{ new ArrField($nestedPath, None, None) }
                case v if v.isNull    => '{ new NullField($nestedPath) }
                case v if v.isObject  =>
                  // For nested objects, we need to recurse, but we'll keep it simple for now
                  '{ new ObjField($nestedPath, Map.empty) }
                case _ => '{ new NullField($nestedPath) }
              (name, nestedExpr)
            }

            // Build the field map expression
            val mapEntries: List[Expr[(String, Any)]] = nestedAccessors.map { case (name, expr) =>
              val nameExpr = Expr(name)
              '{ ($nameExpr, $expr) }
            }
            val mapExpr = '{ Map(${ Varargs(mapEntries) }*) }

            '{ new ObjField($pathWithIndexExpr, $mapExpr) }
          end if
        case _ =>
          '{ new NullField($pathWithIndexExpr) }
      end match
    end buildAccessorForIndex

    // Recursively build accessor type and expression for a JSON value at a given path
    def buildAccessor(json: Json, path: List[String]): (TypeRepr, Expr[Any]) =
      val pathExpr = Expr(path)
      json match
        case j if j.isString =>
          (TypeRepr.of[StringField], '{ new StringField($pathExpr) })
        case j if j.isNumber =>
          (TypeRepr.of[NumField], '{ new NumField($pathExpr) })
        case j if j.isBoolean =>
          (TypeRepr.of[BoolField], '{ new BoolField($pathExpr) })
        case j if j.isArray =>
          val arr = j.asArray.get
          if arr.isEmpty || !arr.head.isObject then
            // Empty array or non-object elements - no accessor
            (TypeRepr.of[ArrField], '{ new ArrField($pathExpr, None, None) })
          else
            // Build accessor for the first element (at index 0)
            val firstElement = arr.head
            val (headType, headExpr) = buildAccessor(firstElement, path :+ FirstElementIndex)

            // Build a factory function that creates accessors for any index
            // The factory will create the same accessor structure but with a different index
            val factoryExpr = '{ (index: Int) =>
              ${ buildAccessorForIndex(firstElement, path, 'index) }
            }

            // Build refinement type: ArrField { def head: HeadType; def apply(Int): HeadType }
            val refinedType = Refinement(
              Refinement(TypeRepr.of[ArrField], "head", headType),
              "apply",
              MethodType(List("index"))(
                _ => List(TypeRepr.of[Int]),
                _ => headType
              )
            )

            refinedType.asType match
              case '[t] =>
                val arrExpr =
                  '{ new ArrField($pathExpr, Some($headExpr), Some($factoryExpr)).asInstanceOf[t] }
                (refinedType, arrExpr)
            end match
          end if
        case j if j.isNull =>
          (TypeRepr.of[NullField], '{ new NullField($pathExpr) })
        case j if j.isObject =>
          val obj = j.asObject.get
          val fields = obj.toList

          if fields.isEmpty then (TypeRepr.of[ObjField], '{ new ObjField($pathExpr, Map.empty) })
          else
            // Build nested accessors for each field
            val nestedAccessors: List[(String, TypeRepr, Expr[Any])] = fields.map { case (name, value) =>
              val (tpe, expr) = buildAccessor(value, path :+ name)
              (name, tpe, expr)
            }

            // Build refinement type: ObjField { def fieldName: FieldType; ... }
            val refinedType = nestedAccessors.foldLeft(TypeRepr.of[ObjField]) { case (acc, (name, tpe, _)) =>
              Refinement(acc, name, tpe)
            }

            // Build the field map expression
            val mapEntries: List[Expr[(String, Any)]] = nestedAccessors.map { case (name, _, expr) =>
              val nameExpr = Expr(name)
              '{ ($nameExpr, $expr) }
            }
            val mapExpr = '{ Map(${ Varargs(mapEntries) }*) }

            refinedType.asType match
              case '[t] =>
                val objExpr = '{ new ObjField($pathExpr, $mapExpr).asInstanceOf[t] }
                (refinedType, objExpr)
            end match
          end if
        case _ =>
          (TypeRepr.of[NullField], '{ new NullField($pathExpr) })
      end match
    end buildAccessor

    circeJson match
      case obj if obj.isObject =>
        val fields = obj.asObject.get.toList

        if fields.isEmpty then
          '{ new VegaSpec(parse($specContentExpr).getOrElse(Json.Null), new ObjField(Nil, Map.empty)) }
        else
          // Build accessors for all top-level fields
          val accessors: List[(String, TypeRepr, Expr[Any])] = fields.map { case (name, value) =>
            val (tpe, expr) = buildAccessor(value, List(name))
            (name, tpe, expr)
          }

          // Build refinement type for the mod object
          val modType = accessors.foldLeft(TypeRepr.of[ObjField]) { case (acc, (name, tpe, _)) =>
            Refinement(acc, name, tpe)
          }

          // Build the field map
          val mapEntries: List[Expr[(String, Any)]] = accessors.map { case (name, _, expr) =>
            val nameExpr = Expr(name)
            '{ ($nameExpr, $expr) }
          }
          val mapExpr = '{ Map(${ Varargs(mapEntries) }*) }

          modType.asType match
            case '[modT] =>
              '{
                val rawSpec = parse($specContentExpr).getOrElse(Json.Null)
                val mod = new ObjField(Nil, $mapExpr).asInstanceOf[modT]
                new VegaSpec[modT](rawSpec, mod)
              }
          end match
        end if

      case _ =>
        // JSON is valid but not an object
        val jsonType = circeJson match
          case j if j.isArray   => "array"
          case j if j.isString  => "string"
          case j if j.isNumber  => "number"
          case j if j.isBoolean => "boolean"
          case j if j.isNull    => "null"
          case _                => "unknown type"
        report.errorAndAbort(s"VegaPlot.fromString requires JSON object but got $jsonType")
    end match
  end fromStringImpl
end VegaPlotMacroImpl
