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
// import viz.PlotTargets.websocket // for local testing

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat

import scala.compiletime.uninitialized

import java.net.HttpURLConnection
import java.net.URL
import com.microsoft.playwright.options.WaitUntilState
import viz.laminar.*
import io.github.quafadas.sjsls.*
import com.comcast.ip4s.Port
import cats.effect.ExitCode
import cats.effect.unsafe.implicits.global
import cats.effect.{IO, SyncIO}
import munit.CatsEffectSuite
import com.microsoft.playwright.options.AriaRole

class PwTest extends CatsEffectSuite:

  val port = 3001

  var pw: Playwright = uninitialized
  var browser: Browser = uninitialized
  var page: Page = uninitialized

  override def munitFixtures = List(startServer)

  def startServer = ResourceSuiteLocalFixture(
    "server", {
      val lsc = LiveServer.LiveServerConfig(
        baseDir = Some(BuildInfo.rootDir),
        port = Port.fromInt(port).get,
        indexHtmlTemplate = Some(BuildInfo.pathToIndex),
        openBrowserAt = "/",
        preventBrowserOpen = true,
        buildTool = io.github.quafadas.sjsls.None(),
        outDir = Some(BuildInfo.pathToJS),
        millModuleName = Some("laminar.integration.app")
      )
      println(lsc)
      LiveServer
        .main(lsc)
        .evalTap(_ =>
          IO {
            page.navigate(s"http://localhost:$port")
          }
        )
    }
  )

  end startServer

  override def beforeAll(): Unit =

    // System.setProperty("playwright.driver.impl", "jsnev.DriverJar")
    pw = Playwright.create()
    browser = pw.chromium().launch();
    page = browser.newPage();

  end beforeAll

  test("hover over third path under g.mark-rect.role-mark updates h1") {
    val titleP = page.locator("h1")
    val text = titleP.textContent()
    assert(text.contains("Viz Play"))

    val rectGroup = page.locator("g.mark-rect.role-mark")
    val thirdPath = rectGroup.locator("path")

    page.setViewportSize(1920, 1080)

    thirdPath.nth(2).hover()
    // Wait for the h1 to update
    page.waitForTimeout(100)
    val parentDiv = page.locator("div.parent-div")

    assert(parentDiv.textContent().contains("""{"category":"C","amount":43}"""))

    titleP.hover()
    page.waitForTimeout(100)
    assert(parentDiv.textContent().contains("""{}"""))
  }

  test("vega lite data click") {
    val button = page.getByRole(AriaRole.BUTTON, Page.GetByRoleOptions().setName("Vega Lite Bar"))
    button.click()
    page.waitForTimeout(200)

    val parentDiv = page.locator("div.parent-div p")
    assert(parentDiv.count() == 0)

    val rectGroup = page.locator("g.mark-rect.role-mark")
    val thirdPath = rectGroup.locator("path")

    page.setViewportSize(1920, 1080)

    thirdPath.nth(2).click()
    assert(parentDiv.count() == 1)
    assert(parentDiv.textContent().contains("""{"name"""))

  }

  override def afterAll(): Unit =
    page.close()
    browser.close()
    pw.close()

    super.afterAll()
  end afterAll
end PwTest
