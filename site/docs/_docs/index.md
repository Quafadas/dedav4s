# Dedav4s

Declarative data visualization for scala - a scala plotting concept.

## Elevator Pitch
<img src="dedav_intro.gif" width=90% height=90% />

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
On the JVM it currently contains targets for:

1. repl
2. notebooks
3. websockets
5. Svg, pdf and png files

It further aims to help plotting in scala JS, so that the same charts are easily re-useable in both environments. See [getting started](./gettingStarted.mdoc.html) for more information.

<script>
    const sse = new EventSource("/refresh/v1/sse");
    sse.addEventListener("message", (e) => {
    const msg = JSON.parse(e.data);

    if ("KeepAlive" in msg) console.log("KeepAlive");

    if ("PageRefresh" in msg) location.reload();
    });
</script>