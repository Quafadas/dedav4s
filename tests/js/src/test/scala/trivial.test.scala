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
import org.scalajs.dom

class MySuite extends munit.FunSuite:
  test("Createing an element") {
    val h1 = dom.document.createElement("h1")
    h1.textContent = "Hello World"
    dom.document.body.appendChild(h1)
    val h1s = dom.document.getElementsByTagName("h1")
    assertEquals(h1s.length, 1)
    assertEquals(h1s.item(0).textContent,"Hello World")
  }

end MySuite
