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

import io.undertow.websockets.WebSocketConnectionCallback
import io.undertow.websockets.core.{AbstractReceiveListener, BufferedTextMessage, WebSocketChannel, WebSockets}
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

trait WebsocketVizServer(portIn: Int) extends cask.MainRoutes:
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

  private def divId = div(id := "vis", height := "95vmin", width := "95vmin")

  @cask.get("/view/:description")
  def filerViz(description: String) =
    html(
      lang := "en",
      headImports,
      body(
        // h1("viz"),
        divId,
        script(raw"""
        let socket = new WebSocket('ws://localhost:$port/connect/viz');
        socket.onopen = function(e) {
          document.getElementById('vis').innerHTML = 'connected and waiting'
        };
        socket.onmessage = function(event) {
          console.log(event.data)
          const spec = JSON.parse(event.data)
          if (spec.description === '$description') {
            vegaEmbed('#vis', spec, {
              renderer: 'svg', // renderer (canvas or svg)
              container: '#vis', // parent DOM container
              hover: true, // enable hover processing
              actions: true
            })
          }
        };

        socket.onclose = function(event) {
          if (event.wasClean) {
            console.log(`[close] Connection closed cleanly, code=$${event.code} reason=$${event.reason}`);
          } else {
            console.error('[close] Connection died');
          }
        };
        socket.onerror = function(error) {
          console.error(`[error] $${error.message}`);
        };

        """)
      )
    )

  @cask.get("/echart/:title")
  def echart(title: String) =
    html(
      lang := "en",
      headImports,
      body(
        // h1("viz"),
        divId,
        script(raw"""
        let socket = new WebSocket('ws://localhost:$port/connect/viz');
        socket.onopen = function(e) {
          document.getElementById('vis').innerHTML = 'connected and waiting'
        };
        socket.onmessage = function(event) {
          console.log(event.data)
          const spec = JSON.parse(event.data)
          if (spec.title.text === '$title') {
            var myChart = echarts.init(document.getElementById('vis'));
            myChart.setOption(spec);
          }
        };

        socket.onclose = function(event) {
          if (event.wasClean) {
            console.log(`[close] Connection closed cleanly, code=$${event.code} reason=$${event.reason}`);
          } else {
            console.error('[close] Connection died');
          }
        };
        socket.onerror = function(error) {
          console.error(`[error] $${error.message}`);
        };

        """)
      )
    )

  @cask.get("/")
  def home() =
    html(
      lang := "en",
      headImports,
      body(
        // h1("viz"),
        divId,
        script(raw"""
        let socket = new WebSocket('ws://localhost:$port/connect/viz');
        socket.onopen = function(e) {
          document.getElementById('vis').innerHTML = 'connected and waiting'
        };
        socket.onmessage = function(event) {
          console.log(event.data)
          const spec = JSON.parse(event.data)
          vegaEmbed('#vis', spec, {
            renderer: 'svg', // renderer (canvas or svg)
            container: '#vis', // parent DOM container
            hover: true, // enable hover processing
            actions: true
        }).then(function(result) {
            console.log(`rendered spec`);
          })
        console.log(`Data received from server`);
        };

        socket.onclose = function(event) {
          if (event.wasClean) {
            console.log(`[close] Connection closed cleanly, code=$${event.code} reason=$${event.reason}`);
          } else {
            console.error('[close] Connection died');
          }
        };
        socket.onerror = function(error) {
          console.error(`[error] $${error.message}`);
        };

        """)
      )
    )

  var channelCheat: List[WebSocketChannel] = List.empty

  @cask.post("/viz")
  def recievedSpec(request: cask.Request) =
    channelCheat match
      case Nil => cask.Response("no client is listening", statusCode = 418)
      case channels: List[WebSocketChannel] =>
        channels.foreach { c =>
          if c.isOpen() then WebSockets.sendTextBlocking(request.text(), c)
        }
        cask.Response("you should be looking at new viz")
    end match
  end recievedSpec

  @cask.websocket("/connect/:viz")
  def setup(viz: String): cask.WebsocketResult =
    new WebSocketConnectionCallback():
      override def onConnect(exchange: WebSocketHttpExchange, channel: WebSocketChannel): Unit =
        channelCheat :+= channel
        channel.getReceiveSetter.set(
          new AbstractReceiveListener():
            override def onFullTextMessage(channel: WebSocketChannel, message: BufferedTextMessage) =
              message.getData match
                case ""   => channel.close()
                case data => WebSockets.sendTextBlocking(viz + " " + data, channel)
        )
        channel.resumeReceives()
      end onConnect
  end setup

  initialize()

end WebsocketVizServer
