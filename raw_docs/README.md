# Dedav4s

Declarative data visualization for scala - a scala plotting concept.

## Elevator Pitch
<img src="assets/dedav_intro.gif" width=90% height=90% />

# Background Information
This is a thin shim around [vega](https://vega.github.io/vega/),  [vega lite](https://vega.github.io/vega-lite/) and [echarts](https://echarts.apache.org/). It's aims are:

1. To make exploratory analysis in a repl (or in a notebook) as easy as possible.
2. To make the barrier to publication (via scala js) as low as possible.
3. To wrap vega / lite in such a manner that charting is robust... with the dream being compile errors for charting

It pays to have an understanding (or at least some idea of what vega / lite are), both Vega & Vega-Lite. It may be worth taking a few minutes to orient yourself with this [talk/demo](https://www.youtube.com/watch?v=9uaHRWj04D4) from the creators at the Interactive Data Lab (IDL) at University of Washington.

If you are interested in plotting in general, I think that you will not regret learning a declarative paradigm.

# Underlying Libraries
[Vega documentation](https://vega.github.io/vega/docs/)

[Vega Lite documentation](https://vega.github.io/vega-lite/docs/)

[Echarts documentation](https://echarts.apache.org/en/index.html)

# Project status
It's more or less achieved my goals on the JVM. On the JVM it currently contains targets for:

1. repl
2. notebooks
3. websockets
5. Svg, pdf and png files

It further aims to help plotting in scala JS, so that the same charts are easily re-useable in both environments.

# High Level Design

Vega / Lite, and echarts both work the same way - interpreting a JSON spec. You provide the spec, it does the drawing that spec on the screen. It's at first daunting... but absurdly effective if you can get your head round it.

By far the easiest way to get started is to start from a plot that looks close to what you want, and evolve it. Therefore, this project contains the http addresses of the _entire_ suite of vega examples. The aliases are pretty obvious and discoverable through tab completion in 'viz.vega.plots.SpecUrl`