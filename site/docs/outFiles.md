# Out Files

Depending on the plot target you choose, dedav4s may produce temporary files. For example if you choose to produce a png, svg or pdf file, these will be written to disk as temporary files. You may find them in the path of the returned value of the plot function.

## Where do the temporary files live?

By default, the OS temp directory.

You may specify the location of temporary files through configuration. Either by having a suitably located "application.conf", or by passing in the environment variable ```DEDAV_OUT_PATH```.

e.g.
```sh
set DEDAV_OUT_PATH=/Users/simon/Pictures scala-cli
```
Assuming you have a chart, produced with one of the desktop targets (browser, tempFile, png, svg, pdf)

```scala mdoc
import viz.PlotTargets.tempFileSpec
import viz.Plottable.*
import viz.vegaFlavour
val chart = viz.vega.plots.SpecUrl.BarChart.plot
chart match
  case _: Unit => ()
  case p: os.Path => println(p)
```
It's location is in the `tmpPath` field of your chart. You could take advantage of the excellent `os` library and knowledge of the path of the file, to subsequently move it around. For example if you wish to automate producing a presentation via a script.