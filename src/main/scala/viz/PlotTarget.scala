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

  given almond: PlotTarget with
    override def show(spec: String) = ???