# dedav4s
Declarative Data Viz 4 Scala - a thin shim around vega and vega lite. It is aimed at repl, interactive environments and exploratory analysis

[Documentation](https://quafadas.github.io/dedav4s/) 

[Video](https://www.youtube.com/watch?v=_yhDhvYWPBk)

# Elevator pitch

![3 sec pitch](/raw_docs/assets/dedav_intro.gif)

The aim is to make "simple" plotting cases as simple as possible in interactive / scripting scala environments. The idea is to expose vega / lite, in a fairly "raw" manner. There are "plot targets" for the all the "common" environments that I have wanted.

## Project status
This library works well enough for my needs. It's been "launched" to see if there is enthusiasm / acceptance of the paradigm. This is a young, simple project with plenty "todo". The barrier to involvement should be rather low, should you have the desire! 

## Generating the DSL 

Clone quicktype, switch to the scala3 branch. 
```
script/quicktype -o vega-lite.scala -t VegaLiteDsl -l scala3 --no-combine-classes -s schema --framework circe --src /Users/simon/Code/quicktype/quicktype/test/inputs/schema/vega-lite.schema --package viz.vega.dsl.vegaLite
```
```
script/quicktype -o vega.scala -t Vega -l scala3 --no-combine-classes -s schema --package viz.dsl.vega --framework circe --src /Users/simon/Code/quicktype/quicktype/test/inputs/schema/vega.schema
```

Currently, there are some niggling problems which remain after generation; 
1. Some givens of union types have duplicate shapes, need to comment out the encoder / decoders for some of them
2. introduce a nulltype parameter.



