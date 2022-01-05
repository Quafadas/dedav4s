package viz

import java.io.{ File, PrintWriter }
import java.awt.Desktop

trait PlotTarget:
  def show(spec: String): Unit

object PlotTargets:

  given doNothing: PlotTarget with
    override def show(spec: String) = ()

  given printlnTarget: PlotTarget with
    override def show(spec: String) = println(spec)

  given desktopBrowser: PlotTarget with
    override def show(spec: String) = {        
        val theHtml = raw"""<!DOCTYPE html>
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
            renderer: "canvas", // renderer (canvas or svg)
            container: "#vis", // parent DOM container
            hover: true, // enable hover processing
            actions: {
              editor : true
            }
        }).then(function(result) {

          })


        </script>
        </body>
        </html> """

        val tempFi = File.createTempFile("plot-",".html")
        tempFi.deleteOnExit()
        new PrintWriter(tempFi) {
            try {
            write(theHtml)
            } finally {
            close()
            }
        }
        Desktop.getDesktop().browse(tempFi.toURI())

    }

  given onelineHelp: PlotTarget with
    override def show(spec: String) = ??? // don't think we need this with the embedding working properly

  given vsCodeNotebook: PlotTarget with
    override def show(spec: String) = almond.show(spec) 

  given almond: PlotTarget with
    override def show(spec: String) = ???
    // we need almond to publish for scala 3
/*     override def show(spec: String) = 
  kernel.publish.display(
      almond.interpreter.api.DisplayData(
        data = Map(      
          "application/vnd.vega.v5+json" -> ujson.write(spec, 2)           
        )
      )  
    )
 */
  given postHttp: PlotTarget with
    override def show(spec: String) = requests.post("http://localhost:8080/viz", data=spec) // see https://github.com/Quafadas/viz-websockets for an example use
    // TODO read the url from configuration / ENV variable.