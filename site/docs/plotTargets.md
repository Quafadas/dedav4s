# Plot Targets

See the "PlotTargets" API. To get started with a PlotTarget, import

```scala
import io.github.quafadas.plots.SetupVega.{*, given}
```
And then the following "given" PlotTargets are available:

- `PlotTargets.desktopBrowser` opens a browser window on your desktop, to show the plot.
- `PlotTargets.websocket` sends the plot to a websocket server on the default port of 8085. You must have started that server seperately in a seperate process with e.g.
```sh
cs launch io.github.quafadas:dedav4s_3:@VERSION@ -M viz.websockets.serve -- 8085
```
or

```sh
scala-cli --dep io.github.quafadas:dedav4s_3:@VERSION@ -M viz.websockets.serve -- 8085
```

- `PlotTargets.publishToPort` sends the plot to a websocket server, which you must have started separately.
- `PlotTargets.almond` shows the plot in a Jupyter notebook (or VSCode notebook) environment.

e.g. `import viz.PlotTargets.websocket`

## Desktop Browser
The library writes a (temporary) file, assuming that

    java.io.File.createTempFile()

Is available. That temporary file assumes that you have an internet connection, and can resolve

    <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>

Finally, we assume the existence of a

    java.awt.Desktop

Which has a browser available...

    java.awt.Desktop.browse()

And we browse to the temporary file created in step one. On some linux distributions, this may not work. Consider using the websocket target instead.

## Websocket

Start the server, in a seperate terminal. You can do this using coursier in one line.

```shell
cs launch io.github.quafadas:dedav4s_3:@VERSION@ -M viz.websockets.serve -- 8085
```

This should start a server on port 8085. Check by visiting http://localhost:8085 in your browser. It should say "connected and waiting".

You can then use the `publishToPort` plot or `websocket` target, which will send the spec, to the server listening on that port.

### Multiple plots
I often want to see multiple plots. Navigate to
`http://localhost:8085/view/hi`, and you'll see "connected and waiting" again.
`http://localhost:8085/view/bob`, and you'll see "connected and waiting" again.

Now, you'll need to update the desription of the chart, to match the trailing path of the url. e.g.

```scal
given port: Int = 8085
List(("A",0),("B",-8),("C",20)).plotBarChart(List(spec => spec("description") = "hi"))
List(("A",5),("B",8),("C",-1)).plotBarChart(List(spec => spec("description") = "bob"))
```

And both browser tabs will now update with their respective plots.

## [Almond](https://www.almond.sh)

<img src="almond.gif" width=90% height=90% />

Feeds a jupyter computing instance the correct MIME type and the JSON spec, to display the plot in the Jupyter notebook (or VSCode notebook!) environment. Juypter ships with Vega, so it works OOTB, which is nice.

### GitHub-Compatible Almond Targets

GitHub's notebook renderer has limited support for Vega/Vega-Lite MIME types. If you want your charts to display correctly when viewing notebooks on GitHub, use one of these alternative targets:

#### `almondHtmlEmbed` (Recommended)
This target embeds a complete HTML document with Vega/Vega-Lite/Echarts libraries loaded from a CDN. This approach mimics Altair's strategy and is the most reliable method for GitHub compatibility as it doesn't depend on GitHub's MIME type support - the HTML is self-contained and executable.

```scala
import viz.PlotTargets.almondHtmlEmbed
import viz.vegaFlavour
import viz.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart(List())
```

This is the **recommended approach** for GitHub compatibility as it:
- Works on all platforms (GitHub, JupyterLab, VSCode, etc.)
- Requires no external tools
- Produces self-contained, executable HTML

#### `almondGithubCompat`
This target automatically detects whether your spec is Vega or Vega-Lite and uses GitHub-compatible MIME types:
- `application/vnd.vega.v3+json` for Vega specs
- `application/vnd.vegalite.v4+json` for Vega-Lite specs

Detection is based on the `$schema` field in your specification. This relies on Vega's backward compatibility to render v5/v6 specs using older MIME types.

```scala
import viz.PlotTargets.almondGithubCompat
import viz.vegaFlavour
import viz.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart(List())
```

#### `almondPngFallback`
This target generates both a PNG image and the original JSON spec. The PNG ensures charts are visible on GitHub (and other environments), while the JSON spec allows interactive viewing in full Jupyter environments.

Requires the [vega CLI](https://vega.github.io/vega/usage/#cli) to be installed (`npm install -g vega-cli`).

```scala
import viz.PlotTargets.almondPngFallback
import viz.vegaFlavour
import viz.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart(List())
```

## VSCode
Use the almond target and a `.ipynb`...

## Gitpod

Gitpod support is kind of brittle and needs a little config. Not testewd recently. By default, dedav will attempt to contact port 48485 of a webserver it starts in the pod. It will detect the pod address through the gitpod [environment variables](https://www.gitpod.io/docs/environment-variables).

You may change the port number, by setting the environment variable ```DEDAV_POD_PORT```. If it is not set, it's default port is 48485.

The port number, you will need to set in the configuration of your gitpod project. In your .gitpod.yml

```
ports:
  - port: 48485
    onOpen: open-browser
    visibility: public
```
48485 is if you do not require a custom port. In your repl, try...

```scala
import viz.PlotTargets.gitpod
import viz.vegaFlavour
import viz.extensions.RawIterables.*

List(("A",5),("B",8),("C",-1)).plotBarChart(List())
List(("A",5),("B",8),("C",-1)).plotBarChart(List())
```

The duplicates command is deliberate. The first request will be ignored - it starts the webserver behind the scenes. Unfortunately, I can't find a way to wait for that process to finish, and then send the request - gitpod appears to wait to open up the ports, until the command has finished executing. I am outsmarted...


## Do Nothing
```scala
import viz.PlotTargets.doNothing
import viz.extensions.RawIterables.*

List(("A",5),("B",8),("C",-1)).plotBarChart(List())
```
To no ones surprise, does nothing! The implementation simply executes unit ```()```. I regret the CPU cycles :-).

Importantly, this is default behaviour - important when we reach scala JS.

## printlnTarget

Formats and prints the final JSON spec to the console.

```scala mdoc:reset
import viz.PlotTargets.printlnTarget
import viz.vegaFlavour
import viz.extensions.*

List(("A",5),("B",8),("C",-1)).plotBarChart(List())
```

## Vega CLI outputs
The [vega CLI](https://vega.github.io/vega/usage/#cli) allows you to output pictures to (non interactive) SVG, PNG, and PDF formats.

This library _does not_ magically set vega cli up for you. It _assumes_ that you have sucessfully done that yourself - i.e. probably you need node.js and have successfully run ```npm install -g vega-cli```... and tested that worked.

Assuming we're plotting

```scala
import viz.PlotTargets.pdf
val pathToPDF = (1 to 10).plotBarChart()
```
![as png](plot-10805531892109353827.png)

### PDF
```scala
import viz.PlotTargets.pdf
```

### SVG
```scala
import viz.PlotTargets.svg
val pathToSVG = (1 to 10).plotBarChart()
```