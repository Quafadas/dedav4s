package viz.macros

import scala.quoted.Expr
import scala.quoted.*
import io.circe.Json
import io.circe.JsonObject
import io.circe.parser.parse
import io.circe.optics.JsonPath
import io.circe.optics.JsonPath._
import monocle.Optional
import viz.*
import viz.Plottable.plot

/** Type alias for spec modifier functions using circe Json */
type SpecMod = Json => Json

// === Typed accessor classes - each only accepts appropriate types ===

/** Accessor for string fields */
class StringField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(s: String): SpecMod = optic.replace(Json.fromString(s))
  def apply(j: Json): SpecMod = optic.replace(j)
  def apply(obj: JsonObject): SpecMod = optic.replace(Json.fromJsonObject(obj))

/** Accessor for numeric fields */
class NumField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(n: Int): SpecMod = optic.replace(Json.fromInt(n))
  def apply(n: Double): SpecMod = optic.replace(Json.fromDoubleOrNull(n))
  def apply(j: Json): SpecMod = optic.replace(j)

/** Accessor for boolean fields */
class BoolField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(b: Boolean): SpecMod = optic.replace(Json.fromBoolean(b))
  def apply(j: Json): SpecMod = optic.replace(j)

/** Accessor for array fields */
class ArrField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(j: Json): SpecMod = optic.replace(j)
  def apply(arr: Vector[Json]): SpecMod = optic.replace(Json.fromValues(arr))

/** Accessor for null fields */
class NullField(path: List[String]):
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(j: Json): SpecMod = optic.replace(j)

/**
 * Base class for object field accessors.
 * Can replace the whole object, and provides typed access to nested fields via Selectable.
 */
class ObjField(path: List[String], fieldMap: Map[String, Any]) extends Selectable:
  private def optic = path.foldLeft(root: JsonPath)((p, f) => p.selectDynamic(f)).json
  def apply(j: Json): SpecMod = optic.replace(j)
  def apply(obj: JsonObject): SpecMod = optic.replace(Json.fromJsonObject(obj))
  def selectDynamic(name: String): Any = fieldMap(name)

/**
 * Vega spec with typed accessors inferred from the JSON structure.
 */
class VegaSpec[M](val rawSpec: Json, val mod: M):
  /** Apply modifications and return the modified spec */
  def build(mods: SpecMod*): Json =
    mods.foldLeft(rawSpec)((json, mod) => mod(json))

  def plot(mods: SpecMod*)(using plotTarget: LowPriorityPlotTarget): VizReturn =
    given ChartLibrary = ChartLibrary.Vega
    ujson.read(build(mods: _*).toString).plot

object VegaPlot:
  transparent inline def fromString(inline specContent: String): Any =
    ${ fromStringImpl('specContent) }

  private def fromStringImpl(specContentExpr: Expr[String])(using Quotes): Expr[Any] =
    import quotes.reflect.*

    val jsonString = specContentExpr.valueOrAbort
    val circeJson = parse(jsonString).getOrElse(Json.Null)

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

          if fields.isEmpty then
            (TypeRepr.of[ObjField], '{ new ObjField($pathExpr, Map.empty) })
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
        case _ =>
          (TypeRepr.of[NullField], '{ new NullField($pathExpr) })

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

      case _ =>
        report.errorAndAbort("VegaPlot.fromString requires a JSON object at the top level")



