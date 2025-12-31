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

package viz.websockets

import scala.collection.mutable
import scala.util.{Try, Success, Failure}

import io.undertow.websockets.WebSocketConnectionCallback
import io.undertow.websockets.core.{
  AbstractReceiveListener,
  BufferedTextMessage,
  WebSocketChannel,
  WebSockets,
  StreamSourceFrameChannel
}
import io.undertow.websockets.spi.WebSocketHttpExchange
import scalatags.Text.all.*
import java.awt.Desktop
import org.jsoup.Connection.Request
import io.undertow.Undertow

implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

@main
def serve(portIn: Int = 8085): Unit =
  val s = new WebsocketVizServer(portIn) {}
  // Start the server
  val server = Undertow.builder
    .addHttpListener(portIn, "localhost")
    .setHandler(s.defaultHandler)
    .build
  server.start()
  println(s"Server started on port $portIn")

  // Keep the JVM alive until the server is stopped
  while true do
    try Thread.sleep(3000)
    catch
      case _: InterruptedException =>
        // The sleep was interrupted, probably because the server is stopping
        // Log the interruption and stop the server
        println("Server interrupted, stopping...")
  end while
end serve

// lazy object WebsocketVizServer extends WebsocketVizServer(8085)

case class ChartSpec(chartType: String, spec: String)

trait WebsocketVizServer(portIn: Int) extends cask.MainRoutes:
  inline def LATEST_CHART_ID = "latest"

  val charts = mutable.Map[String, ChartSpec]()
  val connectedChannels = mutable.ListBuffer[WebSocketChannel]()

  var firstTime: Boolean = true

  def setFirstTime =
    firstTime = false
  override def port = portIn

  def openBrowserWindow() =
    if Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) then
      Desktop.getDesktop().browse(java.net.URI(s"http://localhost:$port"))
    else
      println(
        s"java.awt.Desktop claims the browse action is not supported in this environment. Consider opening a browser at https://localhost:$port , where you should find your plot"
      )

  private val headImports = head(
    meta(charset := "utf-8", name := "viewport", content := "width=device-width, initial-scale=1"),
    script(src := "https://cdn.jsdelivr.net/npm/vega@5"),
    script(src := "https://cdn.jsdelivr.net/npm/vega-lite@5"),
    script(src := "https://cdn.jsdelivr.net/npm/vega-embed@5"),
    script(src := "https://cdn.jsdelivr.net/npm/echarts@5.6.0/dist/echarts.min.js")
  )

  protected val supportedChartTypes = Map(
    "vega" -> vegaView,
    "echarts" -> eChartsView
  )

  protected def autoReloadPageOnChart(watchId: String = "") =
    // Always reload the page if watchId is empty.
    val conditional = if watchId != "" then s"if (info.id === '$watchId')" else ""
    script(raw"""
      |let socket = new WebSocket('/connect/viz');
      |socket.onmessage = function(event) {
      |  const info = JSON.parse(event.data)
      |  $conditional {
      |    location.reload();
      |  }
      |};
    """.stripMargin)
  end autoReloadPageOnChart

  protected def vegaView(chartId: String, spec: String) =
    div(
      span(`class` := "spec", display := "none", spec),
      div(`class` := "chart", height := "95vmin", width := "95vmin"),
      script(raw"""
        |let self = document.currentScript;
        |let specEl = self.parentNode.querySelector('.spec');
        |let chartEl = self.parentNode.querySelector('.chart');
        |const spec = JSON.parse(specEl.textContent);
        |vegaEmbed(chartEl, spec, {
        |  renderer: 'svg', // renderer (canvas or svg)
        |  hover: true, // enable hover processing
        |  actions: true
        |});
      """.stripMargin)
    )
  end vegaView

  protected def eChartsView(chartId: String, spec: String) =
    div(
      span(`class` := "spec", display := "none", spec),
      div(`class` := "chart", height := "95vmin", width := "95vmin"),
      script(raw"""
        |let self = document.currentScript;
        |let specEl = self.parentNode.querySelector('.spec');
        |let chartEl = self.parentNode.querySelector('.chart');
        |const spec = JSON.parse(specEl.textContent);
        |var chart = echarts.init(chartEl);
        |chart.setOption(spec);
      """.stripMargin)
    )

  @cask.get("/")
  def home() =
    val maybeChart = charts.get(LATEST_CHART_ID)
    html(
      lang := "en",
      headImports,
      body(
        autoReloadPageOnChart(),
        maybeChart
          .map { case ChartSpec(chartType, spec) =>
            val chartComponent = supportedChartTypes(chartType)
            chartComponent(LATEST_CHART_ID, spec)
          }
          .getOrElse(p("Connected"))
      )
    )
  end home

  @cask.get("/view/:chartId")
  def filerViz(chartId: String) =
    val maybeChart = charts.get(chartId)
    html(
      lang := "en",
      headImports,
      body(
        autoReloadPageOnChart(chartId),
        maybeChart
          .map { case ChartSpec(chartType, spec) =>
            val chartComponent = supportedChartTypes(chartType)
            chartComponent(LATEST_CHART_ID, spec)
          }
          .getOrElse(p("Connected"))
      )
    )
  end filerViz

  def updateOrCreateChart(chartId: String, chartType: String, spec: String) =
    val newSpec = ChartSpec(chartType, spec)
    charts ++= Seq(
      LATEST_CHART_ID -> newSpec,
      chartId -> newSpec
    )
  end updateOrCreateChart

  def notifyChangeToClients(chartId: String) =
    val jsonMessage = ujson.Obj("id" -> chartId).toString
    connectedChannels.foreach(c => if c.isOpen() then WebSockets.sendTextBlocking(jsonMessage, c))
  end notifyChangeToClients

  @cask.post("/viz/:chartType/:chartId")
  def receivedSpec(request: cask.Request, chartType: String, chartId: String) =
    // TODO: Improve response messages
    if !supportedChartTypes.contains(chartType) then
      val supportedTypes = supportedChartTypes.keys.reduce((a, b) => s"'$a', '$b'")
      cask.Response(s"Unsupported type '$chartType'. Supported types are [$supportedTypes].\n", 400)
    else
      val spec = request.text()

      Try(ujson.validate(spec)) match
        case Success(_) =>
          updateOrCreateChart(chartId, chartType, spec)
          notifyChangeToClients(chartId)
          cask.Response("Chart was successfully added.\n")

        case Failure(e: ujson.ParseException) =>
          cask.Response(formatErrorMessage(spec, e))

        case Failure(e) =>
          cask.Response(s"An unkown error ocurrered while validating the spec.\n", 400)
      end match
  end receivedSpec

  @cask.websocket("/connect/:viz")
  def setup(viz: String): cask.WebsocketResult =
    new WebSocketConnectionCallback():
      override def onConnect(exchange: WebSocketHttpExchange, channel: WebSocketChannel): Unit =
        connectedChannels += channel
        channel.getReceiveSetter.set(
          new AbstractReceiveListener():
            override def onFullTextMessage(channel: WebSocketChannel, message: BufferedTextMessage) =
              message.getData match
                case ""   => channel.close()
                case data => WebSockets.sendTextBlocking(viz + " " + data, channel)
            override def onClose(webSocketChannel: WebSocketChannel, _channel: StreamSourceFrameChannel) =
              connectedChannels -= webSocketChannel
        )
        channel.resumeReceives()
      end onConnect
  end setup

  private def formatErrorMessage(spec: String, e: ujson.ParseException): String =
    def findLine(text: String, index: Int) =
      var lineStartIdx = 0
      var lineEndIdx = 0
      var lineNum = 1
      while
        lineEndIdx = text.indexOf("\n", lineStartIdx)
        lineEndIdx + 1 < index && lineEndIdx != -1
      do
        lineNum += 1
        lineStartIdx = lineEndIdx + 1
      end while
      lineEndIdx = if lineEndIdx != -1 then lineEndIdx else text.length()

      (text.substring(lineStartIdx, lineEndIdx), lineStartIdx, lineNum)
    end findLine

    def leftPadded(relativeTo: String, upTo: Int, text: String) =
      relativeTo.take(upTo - text.length).foldLeft("") {
        case (pad, '\t') => pad + '\t'
        case (pad, _)    => pad + ' '
      } + text

    val (lineText, lineStartIdx, lineNum) = findLine(spec, e.index)
    val column = e.index - lineStartIdx
    s"""|[$lineNum:${e.index - lineStartIdx}] Failed to parse json:
        |$lineText
        |${leftPadded(lineText, column, "^")}
        |${leftPadded(lineText, column, e.clue)}
        |""".stripMargin
  end formatErrorMessage

  initialize()

end WebsocketVizServer
