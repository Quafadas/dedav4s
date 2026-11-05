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
import com.microsoft.playwright.options.WaitForSelectorState
import com.google.gson.JsonObject

class PwTest extends CatsEffectSuite:

  val port = 3001

  var pw: Playwright = uninitialized
  var browser: Browser = uninitialized
  var page: Page = uninitialized

  override def munitFixtures = List(startServer)

  def startServer = ResourceSuiteLocalFixture(
    "server", {
      val lsc = LiveServerConfig(
        baseDir = Some(BuildInfo.rootDir),
        port = Port.fromInt(port).get,
        indexHtmlTemplate = Some(BuildInfo.pathToIndex),
        openBrowserAt = "/",
        preventBrowserOpen = true,
        buildTool = NoBuildTool(),
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

  def selectTab(name: String): Unit =
    page.getByRole(AriaRole.BUTTON, Page.GetByRoleOptions().setName(name).setExact(true)).click()
    // The old chart is removed synchronously on click, so the first mark we find belongs to the new chart.
    page.locator("g.mark-rect.role-mark path").first().waitFor()
  end selectTab

  /** Counts the listeners of `eventType` registered on a global object ("window" or "document"), using the Chrome
    * DevTools protocol.
    */
  def listenerCount(target: String, eventType: String): Int =
    val cdp = page.context().newCDPSession(page)
    try
      val evalArgs = new JsonObject()
      evalArgs.addProperty("expression", target)
      val objectId = cdp.send("Runtime.evaluate", evalArgs).getAsJsonObject("result").get("objectId").getAsString

      val listenerArgs = new JsonObject()
      listenerArgs.addProperty("objectId", objectId)
      val listeners = cdp.send("DOMDebugger.getEventListeners", listenerArgs).getAsJsonArray("listeners")
      (0 until listeners.size()).count(i => listeners.get(i).getAsJsonObject.get("type").getAsString == eventType)
    finally cdp.detach()
    end try
  end listenerCount

  def chartListenerCounts: (Int, Int) =
    (listenerCount("window", "resize"), listenerCount("document", "click"))

  test("switching charts does not leak window:resize or document click listeners") {
    page.setViewportSize(1920, 1080)
    selectTab("Vega Bar")
    selectTab("Vega Lite Bar")
    val afterOneSwitch = chartListenerCounts
    assert(afterOneSwitch._1 > 0, "expected the width: \"container\" chart to listen for window:resize")

    for _ <- 1 to 5 do
      selectTab("Vega Bar")
      selectTab("Vega Lite Bar")
    end for

    assertEquals(chartListenerCounts, afterOneSwitch)
  }

  test("a chart that is unmounted and mounted again is still live") {
    page.setViewportSize(1920, 1080)
    selectTab("Vega Bar")
    val withoutReusedChart = chartListenerCounts

    selectTab("Reused chart")
    val bars = page.locator("g.mark-rect.role-mark path")
    val clicked = page.locator("div.parent-div p")
    bars.nth(0).click()
    val firstClick = clicked.textContent()
    assert(firstClick.contains("""{"name"""), firstClick)
    val withReusedChart = chartListenerCounts

    selectTab("Vega Bar")
    assertEquals(chartListenerCounts, withoutReusedChart)

    selectTab("Reused chart")
    assertEquals(chartListenerCounts, withReusedChart)
    // Only a freshly embedded view responds to clicks; the finalized one has had its listeners removed.
    bars.nth(2).click()
    val secondClick = clicked.textContent()
    assert(secondClick.contains("""{"name"""), secondClick)
    assertNotEquals(secondClick, firstClick)
  }

  test("a chart mounted again before its first embed finishes shows only one chart") {
    page.setViewportSize(1920, 1080)
    selectTab("Vega Bar")
    selectTab("Reused chart")
    val withReusedChart = chartListenerCounts
    selectTab("Vega Bar")

    // Mount, unmount and mount again in one tick, so the second embed starts while the first is still in flight.
    page.evaluate("""() => {
      const tab = name => [...document.querySelectorAll("button")].find(b => b.textContent === name);
      tab("Reused chart").click();
      tab("Vega Bar").click();
      tab("Reused chart").click();
    }""")

    // vega-embed adds its actions menu only after the view has rendered, so give a late first embed time to land.
    page
      .locator("div.parent-div details")
      .first()
      .waitFor(Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED))
    page.waitForTimeout(1000)
    assertEquals(page.locator("div.parent-div details").count(), 1)
    assertEquals(page.locator("div.parent-div .chart-wrapper").count(), 1)
    assertEquals(chartListenerCounts, withReusedChart)
  }

  override def afterAll(): Unit =
    page.close()
    browser.close()
    pw.close()

    super.afterAll()
  end afterAll
end PwTest
