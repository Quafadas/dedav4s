import com.microsoft.playwright.*
import com.microsoft.playwright.options.AriaRole
import com.google.gson.JsonObject

import scala.compiletime.uninitialized

import viz.calico.*
import io.github.quafadas.sjsls.*
import com.comcast.ip4s.Port
import cats.effect.IO
import munit.CatsEffectSuite

class CalicoPwTest extends CatsEffectSuite:

  val port = 3002

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
        millModuleName = Some("calico.integration.app")
      )
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
    pw = Playwright.create()
    browser = pw.chromium().launch()
    page = browser.newPage()
  end beforeAll

  def chartRendered(n: Int): Unit =
    page.locator(s"#chart-$n g.mark-rect.role-mark path").first().waitFor()

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

  test("re-rendering a chart does not leak window:resize or document click listeners") {
    page.setViewportSize(1920, 1080)
    chartRendered(0)
    val withOneChart = chartListenerCounts
    assert(withOneChart._1 > 0, "expected the chart sized to its div to listen for window:resize")

    for n <- 1 to 5 do
      page.getByRole(AriaRole.BUTTON, Page.GetByRoleOptions().setName("Re-render")).click()
      chartRendered(n)
    end for

    // Old charts are released on a cats-effect fiber after the new one is swapped in, so allow them a moment.
    val deadline = System.currentTimeMillis() + 5000
    var counts = chartListenerCounts
    while counts != withOneChart && System.currentTimeMillis() < deadline do
      page.waitForTimeout(100)
      counts = chartListenerCounts
    end while
    assertEquals(counts, withOneChart)
  }

  override def afterAll(): Unit =
    page.close()
    browser.close()
    pw.close()
    super.afterAll()
  end afterAll
end CalicoPwTest
