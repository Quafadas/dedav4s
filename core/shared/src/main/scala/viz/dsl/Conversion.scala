package viz.dsl

import scala.language.implicitConversions
import io.circe.syntax.*
import io.circe.Encoder

object Conversion :
    extension [T](moreJson: T)(using enc: Encoder[T])
        def u: ujson.Value =
            val enc = summon[Encoder[T]]
            ujson.read(enc(moreJson).toString)