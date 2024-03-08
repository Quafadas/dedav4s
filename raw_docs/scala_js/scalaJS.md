# Introduction

<mark> dedav ***does not include*** the underlying JS libraries out of it's box</mark>. You'll need to include them through a bundling mechansim, `script`s in the page header, or remapping to ESModules at link time, depending on your build setup.

## Does it support my Scala JS UI framework?
It turns out, that scala JS Dom is simply a facade for the browser API. Dedav works, through providing a reference to a dom `div` element.

Due to how fundamental the statement above is, we implicitly support _all_ JS UI frameworks. It must be possible to coerce the DIV wrapper of your framework into a scala js dom Div. For the frameworks I have interest myself, there are easier intergrations ...

## Integrations

### Laminar

[laminar docs](laminar.md)

Imports below - SBT

```scala
libraryDependencies += "io.github.quafadas" %% "dedav_laminar" % "@VERSION@"
```

Ammonite / mill

```scala
import $ivy.`io.github.quafadas::dedav_laminar:@VERSION@`
```


### MDoc
Is how this documentation works. It is recommended to include the vega libs in the page headers, rather than introduce a (complex) bundling solution. The source of this library could provide inspiration :-).

### Laika with mdoc


Which are a formiddable documentation team. You may need to also work with Laika docs it's [documentation](https://planet42.github.io/Laika/0.18/02-running-laika/01-sbt-plugin.html). This seciton is here to remind me to;

1. disable auto linking (otherwise you'll end up with two sets of imports)
2. enable raw content

```
laikaConfig ~= { _.withRawContent },

and
tlSiteHeliumConfig := {
  .site
  // NOTE: Needed for Javasriptin in Laika
  .autoLinkJS()
}
```

The github repo of this documentation is a successful example!


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

Which should bring in it's transitive dependancies. You could also consider going sans-bundler via ESM modules or directly via a script tag in the header of your html - have a look at the source of this page for such an example.

```html
  <head>
    <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
    <script src="https://cdn.jsdelivr.net/npm/vega-embed@6"></script>
  </head>
```

## ES Module Map

```json
{
  "imports": {
    "##vega-embed": "https://cdn.jsdelivr.net/npm/vega-embed@6/+esm",
    "##vega-view": "https://cdn.jsdelivr.net/npm/vega-view@5/+esm",
  }
}
```
