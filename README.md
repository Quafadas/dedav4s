# dedav4s
Declarative Data Viz 4 Scala - a thin shim around vega and vega lite.

[Documentation](https://quafadas.github.io/dedav4s/)

[Video](https://www.youtube.com/watch?v=_yhDhvYWPBk)

[![javadoc](https://javadoc.io/badge2/io.github.quafadas/dedav4s_3/javadoc.svg)](https://javadoc.io/doc/io.github.quafadas/dedav4s_3)

# Elevator pitch

![3 sec pitch](/raw_docs/assets/dedav_intro.gif)

The aim is to make "simple" plotting cases as simple as possible in interactive / scripting scala environments. The idea is to expose vega / lite, in a fairly "raw" manner. There are "plot targets" for the all the "common" environments that I have wanted.

## Project status
This library works well enough for my needs, and I've been using it for a couple of years now.

## Generating the DSL

Clone quicktype `npm run build`... or paste into app.quicktype.io

```
script/quicktype -o vega-lite.scala -t VegaLiteDsl -l scala3 --no-combine-classes -s schema --framework circe --src https://raw.githubusercontent.com/vega/schema/master/vega-lite/v5.7.1.json  --package viz.vega.dsl.vegaLite
```
```
script/quicktype -o vega.scala -t Vega -l scala3 --no-combine-classes -s schema --package viz.dsl.vega --framework circe --src https://raw.githubusercontent.com/vega/schema/master/vega/v5.24.0.json
```

Currently, there are some niggling problems which remain after generation;
1. Some givens of union types have duplicate shapes, need to comment out the encoder / decoders for some of them
2. introduce a nulltype parameter.

## Import map

```json
{
  "imports": {
    "@vega/npm": "https://cdn.jsdelivr.net/npm/"
  }
}
```

