package viz

import io.circe.*
import io.circe.derivation.{
  Configuration,
  ConfiguredCodec,
  Default,
  SumOrProduct
}

import scala.compiletime.*

object NtCirce: 
  inline given namedTupleCodec[X <: NamedTuple.AnyNamedTuple]: Codec[X] = {
    type V = NamedTuple.DropNames[X]

    val labels =
      constValueTuple[NamedTuple.Names[X]].toList.asInstanceOf[List[String]]
    val encoders =
      summonAll[Tuple.Map[V, Encoder]].toList.asInstanceOf[List[Encoder[?]]]
    val decoders =
      summonAll[Tuple.Map[V, Decoder]].toList.asInstanceOf[List[Decoder[?]]]

    given conf: Configuration = Configuration.default.withDefaults

    namedTupleCodecImpl[NamedTuple.Names[X], V](labels, encoders, decoders)
      .asInstanceOf[Codec[X]]
  }

  private def namedTupleCodecImpl[K <: Tuple, V <: Tuple](
      labels: List[String],
      encoders: List[Encoder[?]],
      decoders: List[Decoder[?]]
  )(using Configuration): ConfiguredCodec[NamedTuple.NamedTuple[K, V]] =
    new ConfiguredCodec[NamedTuple.NamedTuple[K, V]] {
      val name = "NotUsed"
      lazy val elemDecoders = decoders
      lazy val elemEncoders = encoders
      lazy val elemLabels = labels

      lazy val elemDefaults =
        emptyDefaults.asInstanceOf[Default[NamedTuple.NamedTuple[K, V]]]

      def apply(c: HCursor) = decodeProduct(c, x => x.asInstanceOf[V])
      def encodeObject(a: NamedTuple.NamedTuple[K, V]) = encodeProduct(a)
      override def decodeAccumulating(c: HCursor) =
        decodeProductAccumulating(c, x => x.asInstanceOf[V])
    }

  private val emptyDefaults: Default[Nothing] = new Default[Nothing] {
    type Out = EmptyTuple.type
    def defaults = EmptyTuple
  }