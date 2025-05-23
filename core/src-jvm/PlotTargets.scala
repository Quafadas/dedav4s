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

package viz

import java.awt.Desktop
import almond.api.JupyterApi
import almond.interpreter.api.DisplayData
import almond.api.JupyterAPIHolder.value
import viz.websockets.WebsocketVizServer
import viz.websockets.WebsocketGitPodServer
import os.Path

implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

private[viz] sealed trait Extension(val ext: String)

private[viz] case object Png extends Extension(".png")
private[viz] case object Svg extends Extension(".svg")
private[viz] case object Pdf extends Extension(".pdf")
private[viz] case object Html extends Extension(".html")
private[viz] case object Txt extends Extension(".txt")

private[viz] trait TempFileTarget(val ext: Extension) extends PlotTarget:
  def showWithTempFile(spec: String, path: os.Path, lib: ChartLibrary): Unit
end TempFileTarget

object PlotTargets extends SharedTargets:

  def outPathRoot: Option[String] =
    val pathIsSet: Boolean = conf.hasPath("dedavOutPath")
    if pathIsSet then Some(conf.getString("dedavOutPath"))
    else None
    end if
  end outPathRoot

  def openBrowserWindow(uri: java.net.URI): Unit =
    println(s"opening browser window at $uri")
    if Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) then
      Desktop.getDesktop().browse(uri)
    else
      /* Hail Mary...
        https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
        If you are reading this part of the source code, it is likely because you had a crash on your OS.
        It is not easy for me to test all OSs out there!
        Websockets should work. But...
        If you wish, consider cloning
        https://github.com/Quafadas/dedav4s.git
        run:
        sbt console
        Then copy and paste...
        import viz.PlotTargets.desktopBrowser
        import viz.extensions.*
        List(1,4,6,7,4,4).plotBarChart()
        and you should have a reproduces your crash in a dev environment... and maybe fix for your OS?
        PR welcome :-) ...
       */
      val runtime = java.lang.Runtime.getRuntime()

      try
        val _ = runtime.exec(Array[String](s"""xdg-open $uri"""))
      catch
        case e: Exception =>
          println(
            s"Failed to open browser window with xdg-open: ${e.getMessage}. A probably cause is that xdg-utils are not installed."
          )
      end try
      ()
    end if
  end openBrowserWindow

  lazy val conf = org.ekrich.config.ConfigFactory.load()
  lazy val outPath: Option[String] =
    val pathIsSet: Boolean = conf.hasPath("dedavOutPath")
    if pathIsSet then Some(conf.getString("dedavOutPath"))
    else None
    end if
  end outPath

  private def tempFileHtml(spec: String, lib: ChartLibrary): String =
    lib match
      case ChartLibrary.Vega =>
        raw"""<!DOCTYPE html>
            <html>
            <head>
            <meta charset="utf-8" />
            <!-- Import Vega & Vega-Lite -->
            <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
            <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
            <!-- Import vega-embed -->
            <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
            <style>
                div#vis {
                    width: 95vmin;
                    height:95vmin;
                    style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"
                }
            </style>
            </head>
            <body>
                <div id="vis"></div>

            <script type="text/javascript">
            const spec = ${spec};
            vegaEmbed('#vis', spec, {
                renderer: "svg", // renderer (canvas or svg)
                container: "#vis", // parent DOM container
                hover: true, // enable hover processing
                actions: {
                  editor : true
                }
            }).then(function(result) {

            })
            </script>
            </body>
            </html>"""
      case ChartLibrary.Echarts => raw"""
          <!DOCTYPE html>            <html>
              <head>
                <meta charset="utf-8" />
                <title>ECharts</title>
                <!-- Include the ECharts file you just downloaded -->
                <script src="https://cdn.jsdelivr.net/npm/echarts@5.6.0/dist/echarts.min.js"></script>
              </head>
              <body>
                <!-- Prepare a DOM with a defined width and height for ECharts -->
                <div id="main" style="width: 100vw;height:100vh;"></div>
                <script type="text/javascript">
                  // Initialize the echarts instance based on the prepared dom
                  var myChart = echarts.init(document.getElementById('main'));

                  // Specify the configuration items and data for the chart
                  var option = $spec;

                  // Display the chart using the configuration items and data just specified.
                  myChart.setOption(option);
                </script>
              </body>
            </html>
        """

  given tempHtmlFile: PlotTarget = new TempFileTarget(Html):
    def show(spec: String, lib: ChartLibrary): VizReturn =
      val tmpPath = outPath match
        case Some(path) =>
          os.temp(dir = os.Path(path), suffix = ".html", prefix = "plot-")
        case None =>
          os.temp(suffix = ".html", prefix = "plot-")
      showWithTempFile(spec, tmpPath, lib)
      tmpPath
    end show

    override def showWithTempFile(spec: String, path: os.Path, lib: ChartLibrary): Unit =
      val theHtml = tempFileHtml(spec, lib)
      os.write.over(path, theHtml)
    end showWithTempFile

  given desktopBrowser: PlotTarget = new TempFileTarget(Html):

    def show(spec: String, lib: ChartLibrary): VizReturn =
      val tmpPath = outPath match
        case Some(path) =>
          os.temp(dir = os.Path(path), suffix = ".html", prefix = "plot-")
        case None =>
          os.temp(suffix = ".html", prefix = "plot-")
      showWithTempFile(spec, tmpPath, lib)
      tmpPath
    end show

    override def showWithTempFile(spec: String, path: os.Path, lib: ChartLibrary): Unit =
      val theHtml = tempFileHtml(spec, lib)
      os.write.over(path, theHtml)
      openBrowserWindow(path.toNIO.toUri())
    end showWithTempFile

  /*   given vsCodeNotebook: PlotTarget with
    override def show(spec: String)(using kernel: JupyterApi) = almond.show(spec)  */

  given almond: PlotTarget = new UnitTarget:
    override def show(spec: String, lib: ChartLibrary): Unit =
      val kernel = summon[JupyterApi]
      kernel.publish.display(
        DisplayData(
          data = Map(
            "application/vnd.vega.v5+json" -> spec
          )
        )
      )
    end show

  given publishToPort(using portI: Int): UnitTarget = new UnitTarget:
    override def show(spec: String, lib: ChartLibrary): Unit =
      requests.post(s"http://localhost:$portI/viz", data = spec)
      ()
    end show
  end publishToPort

  given websocket: UnitTarget = new UnitTarget:
    override def show(spec: String, lib: ChartLibrary): Unit =
      requests.post(s"http://localhost:8085/viz", data = spec)
      ()
    end show

  // given gitpod: UnitTarget = new UnitTarget:
  //   override def show(spec: String, lib: ChartLibrary): Unit =
  //     if WebsocketGitPodServer.firstTime then
  //       println(s"starting local server on $port")
  //       println(s"Open a browser at https://${WebsocketGitPodServer.port}-${WebsocketGitPodServer.gitpod_address}")
  //       println(
  //         "Your plot request was ignored because it appeared to be the first one and we needed to start a webserver. Try it again..."
  //       )
  //     else requests.post(s"${WebsocketGitPodServer.gitpod_postTo}", data = spec)
  //     end if
  //     ()
  //   end show

  given tempFileSpec: PlotTarget = new TempFileTarget(Txt):
    def show(spec: String, lib: ChartLibrary): viz.VizReturn =
      val tmpPath = outPath match
        case Some(path) =>
          os.temp(dir = os.Path(path), suffix = ".txt", prefix = "plot-")
        case None =>
          os.temp(suffix = ".txt", prefix = "plot-")
      showWithTempFile(spec, tmpPath, lib)
      tmpPath
    end show

    override def showWithTempFile(spec: String, path: os.Path, lib: ChartLibrary): Unit =
      os.write.over(path, spec)

  given png: PlotTarget = new TempFileTarget(Png):

    def show(spec: String, lib: ChartLibrary): viz.VizReturn =
      val tmpPath = outPath match
        case Some(path) =>
          os.temp(dir = os.Path(path), suffix = ".png", prefix = "plot-")
        case None =>
          os.temp(suffix = ".png", prefix = "plot-")
      showWithTempFile(spec, tmpPath, lib)
      tmpPath
    end show
    override def showWithTempFile(spec: String, path: os.Path, lib: ChartLibrary): Unit =
      val pngBytes = os.proc("vg2png").call(stdin = spec)
      pngBytes.exitCode match
        case 0 =>
          os.write.over(path, pngBytes.out.bytes)
        case _ => throw new Exception(pngBytes.err.text())
      end match
    end showWithTempFile

  // given pdf: PlotTarget = new TempFileTarget(Pdf):
  //   override def showWithTempFile(spec: String, path: os.Path): Unit =
  //     val pngBytes = os.proc("vg2pdf").call(stdin = spec)
  //     pngBytes.exitCode match
  //       case 0 =>
  //         os.write.over(path, pngBytes.out.bytes)
  //       case _ => throw new Exception(pngBytes.err.text())
  //     end match
  //   end showWithTempFile

  // given svg: PlotTarget = new TempFileTarget(Svg):
  //   override def showWithTempFile(spec: String, path: os.Path): Unit =
  //     val pngBytes = os.proc("vg2svg").call(stdin = spec)
  //     pngBytes.exitCode match
  //       case 0 =>
  //         os.write.over(path, pngBytes.out.bytes)
  //       case _ => throw new Exception(pngBytes.err.text())
  //     end match
  //   end showWithTempFile
end PlotTargets
