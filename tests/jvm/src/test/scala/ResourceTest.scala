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
// import viz.PlotTargets.websocket // for local testing
import viz.PlotTargets.doNothing // for CI... as we don't have a port available...
import scala.io.Source
import scala.language.implicitConversions
/*
case class PlotFromString(s:String, override val mods: Seq[ujson.Value => Unit] = List()) extends WithBaseSpec {

  override lazy val baseSpec: ujson.Value = s

}
 */
class ResourceTest extends munit.FunSuite:
  test("Check the default library settings") {
    // (1 to 5).map(i => (scala.util.Random.nextString(5), 1)).plotPieChart(List())
    // This shouldn't crash
    lazy val conf = org.ekrich.config.ConfigFactory.load()

    // by default, these are not set
    assertEquals(conf.hasPath("dedavOutPath"), false)
    assertEquals(conf.hasPath("gitpod_port"), false)

  }

end ResourceTest
