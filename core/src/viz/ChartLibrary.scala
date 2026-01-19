package viz

enum ChartLibrary:
  case Vega, Echarts, Graph
end ChartLibrary

given vegaFlavour: ChartLibrary = ChartLibrary.Vega
given echartsFlavour: ChartLibrary = ChartLibrary.Echarts
given graphFlavour: ChartLibrary = ChartLibrary.Graph

extension (spec: ujson.Value)

  inline def applyMods(mods: Seq[ujson.Value => Unit]): ujson.Value =
    val temp = spec
    for m <- mods do m(temp)
    end for
    temp
  end applyMods
end extension
