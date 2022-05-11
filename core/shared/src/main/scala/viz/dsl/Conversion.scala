package viz.dsl

import scala.language.implicitConversions
import viz.PlotTarget
import io.circe.syntax.*
import io.circe.Encoder
import viz.dsl.vega.VegaDsl
import viz.PlatformShow
import viz.dsl.vegaLite.VegaLiteDsl

object Conversion:
  extension [T](moreJson: T)(using enc: Encoder[T])
    def u: ujson.Value =
      val enc = summon[Encoder[T]]
      ujson.read(enc(moreJson).toString)

case class DslSpec(in: VegaDsl | VegaLiteDsl)(using PlotTarget) extends PlatformShow:
  override def spec =
    in match
      case v: VegaDsl      => v.asJson.deepDropNullValues.toString
      case vl: VegaLiteDsl => vl.asJson.deepDropNullValues.toString

object DslPlot:
  extension (vega: VegaDsl)
    def plot(using PlotTarget) =
      DslSpec(vega)
  extension (vega: VegaLiteDsl)
    def plot(using PlotTarget) =
      DslSpec(vega)

  extension (vega: VegaDsl | VegaLiteDsl)
    def plot(using PlotTarget) =
      DslSpec(vega)
