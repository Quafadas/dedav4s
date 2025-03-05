/*
 * Copyright 2025 quafadas
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

import upickle.default.*
import scala.NamedTuple.NamedTuple

import ujson.Value

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
