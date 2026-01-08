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

/** Accessor for string fields */
class StringField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(s: String): SpecMod = optic.replace(Json.fromString(s))
  def apply(j: Json): SpecMod = optic.replace(j)
  def apply(obj: JsonObject): SpecMod = optic.replace(Json.fromJsonObject(obj))
  def :=(s: String): SpecMod = apply(s)
  def :=(j: Json): SpecMod = apply(j)
  def :=(obj: JsonObject): SpecMod = apply(obj)
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))
  def +=(obj: JsonObject): SpecMod = optic.modify(existing => existing.deepMerge(Json.fromJsonObject(obj)))
end StringField

/** Accessor for numeric fields */
class NumField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(n: Int): SpecMod = optic.replace(Json.fromInt(n))
  def apply(n: Double): SpecMod = optic.replace(Json.fromDoubleOrNull(n))
  def apply(j: Json): SpecMod = optic.replace(j)
  def :=(n: Int): SpecMod = apply(n)
  def :=(n: Double): SpecMod = apply(n)
  def :=(j: Json): SpecMod = apply(j)
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))
  def +=(obj: JsonObject): SpecMod = optic.modify(existing => existing.deepMerge(Json.fromJsonObject(obj)))
end NumField

/** Accessor for boolean fields */
class BoolField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(b: Boolean): SpecMod = optic.replace(Json.fromBoolean(b))
  def apply(j: Json): SpecMod = optic.replace(j)
  def :=(b: Boolean): SpecMod = apply(b)
  def :=(j: Json): SpecMod = apply(j)
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))
  def +=(obj: JsonObject): SpecMod = optic.modify(existing => existing.deepMerge(Json.fromJsonObject(obj)))
end BoolField

/** Accessor for array fields */
class ArrField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(j: Json): SpecMod = optic.replace(j)
  def apply(arr: Vector[Json]): SpecMod = optic.replace(Json.fromValues(arr))
  def :=(j: Json): SpecMod = apply(j)
  def :=(arr: Vector[Json]): SpecMod = apply(arr)

  /** Append a single JSON element to the array */
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
end ArrField

/** Accessor for null fields */
class NullField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(j: Json): SpecMod = optic.replace(j)
  def :=(j: Json): SpecMod = apply(j)
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))
  def +=(obj: JsonObject): SpecMod = optic.modify(existing => existing.deepMerge(Json.fromJsonObject(obj)))
end NullField

/** Base class for object field accessors. Can replace the whole object, and provides typed access to nested fields via
  * Selectable.
  */
class ObjField(path: List[String], fieldMap: Map[String, Any]) extends Selectable:
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(j: Json): SpecMod = optic.replace(j)
  def apply(obj: JsonObject): SpecMod = optic.replace(Json.fromJsonObject(obj))
  def :=(j: Json): SpecMod = apply(j)
  def :=(obj: JsonObject): SpecMod = apply(obj)

  /** Deep merge a JSON value into this object */
  def +=(j: Json): SpecMod = optic.modify(existing => existing.deepMerge(j))

  /** Deep merge a JSON object into this object */
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
          (TypeRepr.of[ArrField], '{ new ArrField($pathExpr) })
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
