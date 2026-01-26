package viz.dsl

import scala.language.implicitConversions
import io.circe.syntax.*
import io.circe.Encoder
import viz.dsl.vega.VegaDsl
import viz.PlatformShow
import viz.dsl.vegaLite.VegaLiteDsl
import viz.LowPriorityPlotTarget

object Conversion:
  extension [T](moreJson: T)(using enc: Encoder[T])
    def u: ujson.Value =
      val enc = summon[Encoder[T]]
      ujson.read(enc(moreJson).toString)
  end extension
end Conversion

case class DslSpec(in: VegaDsl | VegaLiteDsl)(using LowPriorityPlotTarget) extends PlatformShow:
  override def spec =
    in match
      case v: VegaDsl      => v.asJson.deepDropNullValues.toString
      case vl: VegaLiteDsl => vl.asJson.deepDropNullValues.toString
end DslSpec

object DslPlot:
  extension (vega: VegaDsl)
    def plot(using LowPriorityPlotTarget) =
      DslSpec(vega)
  end extension
  extension (vega: VegaLiteDsl)
    def plot(using LowPriorityPlotTarget) =
      DslSpec(vega)
  end extension

  extension (vega: VegaDsl | VegaLiteDsl)
    def plot(using LowPriorityPlotTarget) =
      DslSpec(vega)
  end extension
end DslPlot
