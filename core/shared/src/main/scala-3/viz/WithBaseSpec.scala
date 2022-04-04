/*
 * Copyright 2022 quafadas
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

abstract class WithBaseSpec(val mods: Seq[ujson.Value => Unit] = List())(using PlotTarget) extends PlatformShow:

  lazy val baseSpec: ujson.Value = ???

  /*
    The idea - start from a base spec, "deep copy" it to prevent mutating "state" of any subclass.
    Modify the copy with the list of "modifiers"

    Ideally : validate the outcome against a Schema...
   */
  override def spec: String =
    val temp = ujson.read(baseSpec.toString)
    for m <- mods do m(temp)
    ujson.write(temp, 2)
