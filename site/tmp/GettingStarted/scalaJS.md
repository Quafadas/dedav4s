# Introduction

<mark> dedav ***does not include*** the underlying JS libraries out of it's box</mark>. You'll need to include them through a bundling mechansim, `script`s in the page header, or remapping to ESModules at link time, depending on your build setup.


## Integrations

### Laminar

Imports below - SBT

```scala sc:nocompile
libraryDependencies += "io.github.quafadas" %% "dedav_laminar" % "@VERSION@"
```

Ammonite / mill

```scala sc:nocompile
import $ivy.`io.github.quafadas::dedav_laminar:@VERSION@`
```

### MDoc
Is how this documentation works. It is recommended to include the vega libs in the page headers, rather than introduce a (complex) bundling solution. The source of this library could provide inspiration :-).


# Notes

The goal of sharing the same chart between JVM and scala JS :-) has, in my mind been achieved.

The seamless transition between exploration in a repl  on the JVM, luxuriating in it's rapid feedback and typsafe tooling, and subsequent publication into a browser with scala JS I have found to be highly productive.




## Javascript libraries
The example dependency is set out above. It _should_ work with _any_ bundling solution, or even by directly embedding the dependancies in the header of the html. Your choice.

The ~~burden~~ freedom is left to you to get vega itself in scope. Via a bundler, the simplest package.json depedancies would be;

```json
"dependencies": {
    "vega-embed" : "6.20.8"
}
```

Here's an alternative - serve this simple page to plot a simple chart. The embedding strategy translates the JS parts to scalaJS

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>ESM Experiment</title>
  </head>
  <body>
    <h1>Welcome to ESM Experimental</h1>

    <p>This is a simple HTML file for the ESM Experiment project.</p>
    <div id="viz"></div>
    <script type="module">
      import embed from "https://esm.run/vega-embed";
      // import Embed from "https://esm.run/vega-embed";

      var spec =
        "https://raw.githubusercontent.com/vega/vega/master/docs/examples/bar-chart.vg.json";
      embed("#viz", spec)
        .then(function (result) {
          // Access the Vega view instance (https://vega.github.io/vega/docs/api/view/) as result.view
        })
        .catch(console.error);
    </script>
  </body>
</html>

```

## ES Module Map

```json
{
  "imports": {
    "##vega-embed": "https://esm.run/vega-embed",
    "##vega-view": "https://esm.run/vega-view",
  }
}
```
