/*
 * Copyright 2023 quafadas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viz

import ujson.Value

import upickle.default.*
import NamedTuple.AnyNamedTuple
import NamedTuple.Names
import NamedTuple.DropNames
import NamedTuple.NamedTuple
import NamedTuple.withNames
import scala.reflect.ClassTag
import upickle.core.Visitor
import upickle.core.ObjVisitor

object NamedTupleReadWriter:
  inline given [N <: Tuple, V <: Tuple, T <: NamedTuple[N, V]]: ReadWriter[T] =
    val names = scala.compiletime.constValueTuple[N].toList.asInstanceOf[List[String]]
    val readWriters = scala.compiletime.summonAll[Tuple.Map[V, ReadWriter]].toList.asInstanceOf[List[ReadWriter[?]]]

    def toMap(t: T): Map[String, Value] =
      val values = t.toTuple.productIterator.toList
      (0.until(names.length))
        .foldLeft(Map.empty[String, Value]): (map, i) =>
          val readWriter = readWriters(i).asInstanceOf[ReadWriter[Any]]
          map + (names(i) -> transform(values(i))(using readWriter).to(ujson.Value))
    end toMap

    def fromMap(map: Map[String, Value]): T =
      val namesAndRWs = names.zip(readWriters)
      val valueList = namesAndRWs.map: (name, rw) =>
        map.get(name) match
          case None => throw new Exception(s"Missing field: $name")
          case Some(v) =>
            read(v)(using rw.asInstanceOf[ReadWriter[Any]])

      val tuple = valueList.foldRight(EmptyTuple: Tuple): (v, tuple) =>
        v *: tuple

      tuple.asInstanceOf[T]
    end fromMap

    summon[ReadWriter[Map[String, Value]]].bimap(toMap, fromMap)
  end given
end NamedTupleReadWriter

object Macros:

  object Implicits:
    inline given [T <: AnyNamedTuple]: Writer[T] = Macros.derivedWriter[T]
    inline given [T <: AnyNamedTuple]: Reader[T] = Macros.derivedReader[T]
  end Implicits

  inline def derivedWriter[T <: AnyNamedTuple]: Writer[T] =
    NTObjWriter[Names[T], DropNames[T]](
      fieldNames = compiletime.constValueTuple[Names[T]],
      fieldWriters = compiletime.summonAll[Tuple.Map[DropNames[T], Writer]]
    ).asInstanceOf[ObjectWriter[T]]

  inline def derivedReader[T <: AnyNamedTuple]: Reader[T] =
    NTObjReader[Names[T], DropNames[T]](
      paramCount = compiletime.constValue[NamedTuple.Size[T]],
      fieldNames = compiletime.constValueTuple[Names[T]],
      fieldReaders = compiletime.summonAll[Tuple.Map[DropNames[T], Reader]]
    ).asInstanceOf[Reader[T]]

  final class NTObjWriter[N <: Tuple, V <: Tuple](
      fieldNames: => Tuple,
      fieldWriters: => Tuple
  ) extends ObjectWriter[NamedTuple[N, V]]:
    private lazy val fW = fieldWriters
    private lazy val fN = fieldNames

    override def length(v: NamedTuple[N, V]): Int = fN.size

    override def writeToObject[R](
        ctx: ObjVisitor[?, R],
        v: NamedTuple[N, V]
    ): Unit =
      val iN = fN.productIterator.asInstanceOf[Iterator[String]]
      val iW = fW.productIterator.asInstanceOf[Iterator[Writer[Any]]]
      val iV = v.toTuple.productIterator.asInstanceOf[Iterator[Any]]
      iN.zip(iW).zip(iV).foreach { case ((n, w), v) =>
        val keyVisitor = ctx.visitKey(-1)
        ctx.visitKeyValue(
          keyVisitor.visitString(n, -1)
        )
        ctx.narrow.visitValue(w.write(ctx.subVisitor, v), -1)
      }
    end writeToObject

    override def write0[V1](out: Visitor[?, V1], v: NamedTuple[N, V]): V1 =
      val oVisitor = out.visitObject(fN.size, jsonableKeys = true, -1)
      writeToObject(oVisitor, v)
      oVisitor.visitEnd(-1)
    end write0
  end NTObjWriter

  final class NTObjReader[N <: Tuple, V <: Tuple](
      paramCount: Int,
      fieldNames: => Tuple,
      fieldReaders: => Tuple
  ) extends CaseClassReader3V2[NamedTuple[N, V]](
        paramCount,
        if paramCount <= 64 then if paramCount == 64 then -1 else (1L << paramCount) - 1
        else paramCount,
        allowUnknownKeys = true,
        (params, _) => Tuple.fromArray(params).asInstanceOf[V].withNames[N]
      ):
    lazy val fR = fieldReaders.toArray
    lazy val fN = fieldNames.toArray.map(_.asInstanceOf[String])
    override def visitors0 = (null, fR)
    override def keyToIndex(x: String): Int = fN.indexOf(x)
    override def allKeysArray = fN
    override def storeDefaults(
        x: upickle.implicits.BaseCaseObjectContext
    ): Unit = ()
  end NTObjReader
end Macros
