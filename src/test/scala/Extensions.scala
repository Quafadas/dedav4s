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

import viz.extensions.*
import viz.PlotTargets.websocket
import scala.util.Random

class ExtensionSuite extends munit.FunSuite:
  test("extension methods exist... ") {

      val randomNumbers1: IndexedSeq[Double] = (0 to 20).map(i => i * Random.nextDouble() )
      val randomTuple_Int_Double: IndexedSeq[(Int, Double)] = (0 to 20).map(i => (i, i * Random.nextDouble() ))
      val randomTuple_String_Int: IndexedSeq[(String, Int)] = (0 to 5).map(i => (Random.nextString(5), i ))
      val randomMap_String_Int: Map[String, Int] = randomTuple_String_Int.toMap

        List(1,2,3,4).plotBarChart()

        randomMap_String_Int.plotBarChart(List())
        randomMap_String_Int.plotPieChart(List())
        
        List(("hi", 1.5),("boo", 2.5),("baz", 3.0)).plotPieChart(List())
        
        List(("hi", 1.5),("boo", 2.5),("baz", 3.0)).plotBarChart(List())

        "interesting stuff stuff interesting".plotWordCloud()
        
        List("interesting", "stuff", "interesting").plotWordcloud()

        randomNumbers1.plotLineChart()

        randomTuple_Int_Double.plotScatter()

        randomTuple_Int_Double.plotRegression()

  }
