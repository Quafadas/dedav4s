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

import viz.Macros.Implicits.given_Writer_T

class NTTest extends munit.FunSuite:
  test("That we can serilaise a NamedTuple") {

    val spec = (
      `$schema` = "https://vega.github.io/schema/vega-lite/v6.json",
      width = "container",
      height = 250,
      data = (url = "https://raw.githubusercontent.com/vega/vega/refs/heads/main/docs/data/cars.json"),
      mark = "bar",
      encoding = (
        x = (`field` = "Origin"),
        y = (
          aggregate = "count",
          title = "Number of Cars"
        )
      )
    )

    // If this compiles we're doing well.
    upickle.default.writeJs(spec)

  }
end NTTest
