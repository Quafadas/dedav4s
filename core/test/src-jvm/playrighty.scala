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

import viz.extensions.*
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import viz.PlotTargets.tempHtmlFile
import viz.Plottable.*
import scala.compiletime.uninitialized
import viz.Macros.Implicits.given
import scala.concurrent.Future
import viz.*
import io.undertow.Undertow
import java.net.HttpURLConnection
import java.net.URL
import com.microsoft.playwright.options.WaitUntilState
// import scala.concurrent.ExecutionContext.Implicits.global

class PlaywrightTest extends munit.FunSuite:

  var pw: Playwright = uninitialized
  var browser: Browser = uninitialized
  var page: Page = uninitialized

  override def beforeAll(): Unit =

    // System.setProperty("playwright.driver.impl", "jsnev.DriverJar")
    pw = Playwright.create()
    browser = pw.chromium().launch();
    page = browser.newPage();
  end beforeAll

  private def navigateTo(toCheck: VizReturn, page: Page) =
    toCheck match
      case path: os.Path =>
        // println(path)
        val _ = page.navigate(s"file://$path")
      case _ =>
        fail("no path")
    end match
  end navigateTo

  test("can plot pie chart") {
    import viz.vegaFlavour
    import viz.PlotTargets.tempHtmlFile
    val aSeq = (1 to 5).map(i => (i.toDouble, i.toString()))
    val tmp = aSeq.plotPieChart(
      List(
        (spec: ujson.Value) => spec("height") = 500,
        (spec: ujson.Value) => spec("width") = 500
      )
    )

    navigateTo(tmp, page)

    val _ = page.waitForSelector("div#vis")
    assertThat(page.locator("div#vis")).isVisible()
    assertThat(page.locator("svg.marks")).isVisible()
    assertThat(page.locator("svg.marks")).hasAttribute("height", "500")
    assertThat(page.locator("svg.marks")).hasAttribute("width", "500")
  }

  test("title") {
    import viz.vegaFlavour
    import viz.PlotTargets.tempHtmlFile
    val aSeq = (1 to 5).map(i => (i.toDouble, i.toString()))
    val tmp = aSeq.plotBarChart(
      List((spec: ujson.Value) => spec("title") = ujson.Obj("text" -> "my title"))
    )
    navigateTo(tmp, page)
    val _ = page.waitForSelector("div#vis")
    assertThat(page.locator("svg.marks")).isVisible()
  }

  test("Plotting an echart") {
    import viz.echartsFlavour
    // import viz.PlotTargets.desktopBrowser
    import viz.PlotTargets.doNothing
    import viz.NamedTupleReadWriter.given

    val spec = (
      title = (
        text = "ECharts Example"
      ),
      xAxis = (
        data = Seq("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
      ),
      tooltip = (fake = ""),
      yAxis = (fake = ""),
      series = (
        name = "sales",
        `type` = "bar",
        data = Seq(120, 200, 150, 80, 70, 110, 130)
      )
    )

    // println("Plotting")
    spec.plot()

  }

  test("that we can plot a named tuple") {
    import viz.vegaFlavour
    
    import viz.PlotTargets.tempHtmlFile    

    navigateTo(
      barPlot.plot(),
      page
    )
    val _ = page.waitForSelector("div#vis")
    assertThat(page.locator("svg.marks")).isVisible()

    page.close()

  }

  test("that we can use the viz server") {
    import viz.vegaFlavour
    import viz.PlotTargets.websocket
    given port : Int = 8085
    
    
    val s: viz.websockets.WebsocketVizServer = new viz.websockets.WebsocketVizServer(port) {}
    val server = Undertow.builder
      .addHttpListener(port, "localhost")
      .setHandler(s.defaultHandler)
      .build
    server.start()

    // Future(s.main(Array.empty[String]))
    

    // if (!undertow) {
    //   Thread.sleep(1000)
    // }

    // def isServerReady(host: String, port: Int): Boolean =
    //   try
    //     val url = new URL(s"http://$host:$port")
    //     val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    //     connection.setRequestMethod("HEAD")
    //     connection.setConnectTimeout(2000) // 2 seconds timeout
    //     connection.connect()
    //     connection.getResponseCode == 200 // Check if the server responds with HTTP 200
    //   catch
    //     case _: Exception => false // If any exception occurs, the server is not ready
    // end isServerReady
    
    // while !(isServerReady("localhost", port)) do
    //   println("Waiting for server to start...")
    //   Thread.sleep(1000) // Wait for 1 second before checking again
    // end while

     // Give the server some time to start
    val url = s"http://localhost:$port/view/${barPlot.description}"
    println(url)
   
    println(requests.get(url).text() )
    

    barPlot.plot()        
    

    Thread.sleep(5000)
    
    assertThat(page.locator("svg.marks")).isVisible()
    page.close()

    println("plotted")
    Thread.sleep(10000)

  }
  

  val barPlot = (
      `$schema` = "https://vega.github.io/schema/vega-lite/v5.json",
      description = "BarChart",
      data = (
        values = Seq(
          (a = "A", b = 28),
          (a = "B", b = 55),
          (a = "C", b = 43),
          (a = "D", b = 91),
          (a = "E", b = 81),
          (a = "F", b = 53),
          (a = "G", b = 19),
          (a = "H", b = 87),
          (a = "I", b = 52)
        )
      ),
      mark = "bar",
      encoding = (
        x = (field = "a", `type` = "nominal", axis = (labelAngle = 0)),
        y = (field = "b", `type` = "quantitative")
      )
    )


  

  override def afterAll(): Unit =
    browser.close()
    pw.close()

    super.afterAll()
  end afterAll

end PlaywrightTest
