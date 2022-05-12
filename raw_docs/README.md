# Dedav4s

Declarative data visualization for scala - a scala plotting concept. 

It is written exclusively in scala 3. There are currently no plans to backport to scala 2.

## Elevator Pitch
<img src="assets/dedav_intro.gif" width=90% height=90% />

# Background Information
This is a thin shim around [vega](https://vega.github.io/vega/) and [vega lite](https://vega.github.io/vega-lite/). It's aims are:
1. To make exploratory analysis in a repl (or in a notebook) as easy as possible.
2. To make the barrier to publication (via scala js) as low as possible.
3. To wrap vega / lite in an intuitive way

It pays to have an understanding (or at least some idea of what bega / lite are), both Vega & Vega-Lite. It may be worth taking a few minutes to orient yourself with this [talk/demo](https://www.youtube.com/watch?v=9uaHRWj04D4) from the creators at the Interactive Data Lab (IDL) at University of Washington.

If you are interested in plotting in general, I think that you will not regret learning a declarative paradigm.

# The important links
[Vega documentation](https://vega.github.io/vega/docs/)

[Vega Lite documentation](https://vega.github.io/vega-lite/docs/)

# Project status
The project has achieved it's design goals. On the JVM it currently contains targets for:

1. repl
2. notebooks
3. websockets
4. gitpob

On Scala JS

1. a browser via scalajs

The entire suite of vega examples it easily accessed via the library API. 

Mutating or creating your own charts is then enabled via any of the three approaches below

1. A strongly typed DSL
2. "mutably"
3. a mix

All of this is described in the documntation on the website. 