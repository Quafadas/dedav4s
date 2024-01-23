/*
 * Copyright 2024 quafadas
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

import viz.extensions.*
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import viz.PlotTargets.tempHtmlFile

class PlaywrightTest extends munit.FunSuite:

  var pw: Playwright = _
  var browser: Browser = _
  var page: Page = _

  override def beforeAll(): Unit =

    System.setProperty("playwright.driver.impl", "jsnev.DriverJar")
    pw = Playwright.create()
    browser = pw.chromium().launch();
    page = browser.newPage();
  end beforeAll

  test("can plot pie chart") {
    case class PieD(field: Double, id: String)
    val aSeq = (1 to 5)
    val tmp = aSeq.plotPieChart(i => PieD(i, i.toString()))(
      List(
        (spec: ujson.Value) => spec("height") = 500,
        (spec: ujson.Value) => spec("width") = 500
      )
    )
    tmp.tmpPath match
      case Some(path) =>
        println(path)
        val _ = page.navigate(s"file://$path")
      case None =>
        println("no path")
    end match
    val _ = page.waitForSelector("div#vis")
    assertThat(page.locator("div#vis")).isVisible()
    assertThat(page.locator("svg.marks")).isVisible()
    assertThat(page.locator("svg.marks")).hasAttribute("height", "500")
    assertThat(page.locator("svg.marks")).hasAttribute("width", "500")
  }

  test("title") {
    case class BarD(amount: Double, category: String, extra: String)
    val aSeq = (1 to 5)
    val tmp = aSeq.plotBarChart(i => BarD(i, i.toString(), "hi"))(
      List((spec: ujson.Value) => spec("title") = ujson.Obj("text" -> "my title"))
    )
    tmp.tmpPath match
      case Some(path) =>
        println(path)
        val _ = page.navigate(s"file://$path")
      case None =>
        println("no path")
    end match
    val _ = page.waitForSelector("div#vis")
    assertThat(page.locator("svg.marks")).isVisible()
  }

  override def afterAll(): Unit = super.afterAll()

end PlaywrightTest
