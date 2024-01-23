package viz
// import viz.PlotTargets.websocket // for local testing


import com.microsoft.playwright.*;

import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import viz.PlotTargets.tempHtmlFile
import com.github.tarao.record4s.%
import upickle.default.writeJs
import com.github.tarao.record4s.upickle.Record.readWriter
import scala.annotation.nowarn
import viz.RecordsExtensions.plot

class RecordsTest extends munit.FunSuite:

  var pw: Playwright = _
  var browser: Browser = _
  var page: Page = _

  override def beforeAll(): Unit =

    System.setProperty("playwright.driver.impl", "jsnev.DriverJar")
    pw = Playwright.create()
    browser = pw.chromium().launch();
    page = browser.newPage();
  end beforeAll

  test("can build vega lite chart from records") {
    @nowarn
    type barD = %{val a : String ; val b : Double}

    val recordPlot = %(
      schema = "https://vega.github.io/schema/vega-lite/v5.json",
      description = "A simple bar chart with embedded data.",
      data = %(
        values = Array[barD](
          %(a = "A", b = 28.0),
          %(a = "B", b = 55.0),
        )
      ),
      mark = "bar",
      encoding = %(
        x = %(
          field = "a",
          `type` = "ordinal"
        ),
        y = %(
          field = "b",
          `type` = "quantitative"
        )
      )
    ).tag[VegaPlot]

    recordPlot.plot.tmpPath match
      case Some(path) =>
        val _ = page.navigate(s"file://$path")
      case None => fail("no path")

    assertThat(page.locator("svg.marks")).isVisible()
    assertThat(page.locator("g.mark-rect > path")).hasCount(2)
  }



  override def afterAll(): Unit = super.afterAll()

end RecordsTest
