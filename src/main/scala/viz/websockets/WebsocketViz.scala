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

package viz.websockets

import io.undertow.websockets.WebSocketConnectionCallback
import io.undertow.websockets.core.{AbstractReceiveListener, BufferedTextMessage, WebSocketChannel, WebSockets}
import io.undertow.websockets.spi.WebSocketHttpExchange
import scalatags.Text.all.*
import java.awt.Desktop
import io.undertow.websockets.core.WebSocketUtils
import scala.concurrent.Future

implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

object WebsocketVizServer extends cask.MainRoutes:

  var firstTime: Boolean = true
  lazy val randomPort: Int =
    Future {
      initialize()
      main(Array())
    }
    firstTime = false
    8080 + scala.util.Random.nextInt(
      40000
    ) // hope this doesn't generate a port clash! Probably there is a good way to do this?

  override def port = randomPort

  def openBrowserWindow() =
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) then
      Desktop.getDesktop().browse(java.net.URI(s"http://localhost:$port"))
    else
      println(
        s"java.awt.Desktop claims the browse action is not supported in this environment. Consider opening a browser at https://localhost:$port , where you should find your plot"
      )

  @cask.get("/")
  def home() =
    html(
      head(
        script(src := "https://cdn.jsdelivr.net/npm/vega@5"),
        script(src := "https://cdn.jsdelivr.net/npm/vega-lite@5"),
        script(src := "https://cdn.jsdelivr.net/npm/vega-embed@5")
      ),
      body(
        //h1("viz"),
        div(id := "vis", height := "95vmin", width := "95vmin"),
        script(raw"""        
        let socket = new WebSocket('ws://localhost:$port/connect/viz');
        socket.onopen = function(e) {
          document.getElementById('vis').innerHTML = 'connected and waiting'
        };
        socket.onmessage = function(event) {
          console.log(event.data)
          const spec = JSON.parse(event.data)
          vegaEmbed('#vis', spec, {
            renderer: 'canvas', // renderer (canvas or svg)
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

  var channelCheat: Option[WebSocketChannel] = None

  @cask.post("/viz")
  def recievedSpec(request: cask.Request) =
    val theBody = ujson.read(request.text())
    channelCheat match
      case None => cask.Response("no client is listening", statusCode = 418)
      case Some(value) =>
        WebSockets.sendTextBlocking(ujson.write(theBody), value)
        cask.Response("you should be looking at new viz")

  @cask.websocket("/connect/:viz")
  def setup(viz: String): cask.WebsocketResult =
    new WebSocketConnectionCallback():
      override def onConnect(exchange: WebSocketHttpExchange, channel: WebSocketChannel): Unit =
        channelCheat = Some(channel)
        channel.getReceiveSetter.set(
          new AbstractReceiveListener():
            override def onFullTextMessage(channel: WebSocketChannel, message: BufferedTextMessage) =
              message.getData match
                case ""   => channel.close()
                case data => WebSockets.sendTextBlocking(viz + " " + data, channel)
        )
        channel.resumeReceives()
