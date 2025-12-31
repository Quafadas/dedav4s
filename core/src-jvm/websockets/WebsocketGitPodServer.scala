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
import scala.concurrent.Future

object WebsocketGitPodServer extends cask.MainRoutes:

  lazy val conf = org.ekrich.config.ConfigFactory.load()
  var firstTime: Boolean = true
  lazy val randomPort: Int =
    Future {
      initialize()
      main(Array())
    }
    firstTime = false
    val portIsConfigured: Boolean = conf.hasPath("gitpod_port")
    if portIsConfigured then conf.getInt("gitpod_port") else 48485
    end if
  end randomPort

  override def port = randomPort
  lazy val gitpod_address: String = s"${sys.env("GITPOD_WORKSPACE_ID")}.${sys.env("GITPOD_WORKSPACE_CLUSTER_HOST")}"
  lazy val gitpod_postTo: String = s"https://$port-$gitpod_address:443/viz"

  // def openBrowserWindow() = Desktop.getDesktop().browse(java.net.URI(s"http://localhost:$port"))

  @cask.get("/")
  def home() =
    html(
      head(
        script(src := "https://cdn.jsdelivr.net/npm/vega@5"),
        script(src := "https://cdn.jsdelivr.net/npm/vega-lite@5"),
        script(src := "https://cdn.jsdelivr.net/npm/vega-embed@5")
      ),
      body(
        // h1("viz"),
        div(id := "vis", height := "95vmin", width := "95vmin"),
        script(raw"""
        let socket = new WebSocket('wss://$port-$gitpod_address:443/connect/viz');
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
      case None        => cask.Response("no client is listening", statusCode = 418)
      case Some(value) =>
        WebSockets.sendTextBlocking(ujson.write(theBody), value)
        cask.Response("you should be looking at new viz")
    end match
  end recievedSpec

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
      end onConnect
end WebsocketGitPodServer
