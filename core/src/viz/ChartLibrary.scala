package viz

enum ChartLibrary:
  case Vega, Echarts
end ChartLibrary

given vegaFlavour: ChartLibrary = ChartLibrary.Vega
given echartsFlavour: ChartLibrary = ChartLibrary.Echarts

extension (spec: ujson.Value)

  /** Returns a copy of the spec with each mod applied in turn. The spec this is called on is left unchanged, so it is
    * safe to call on a shared spec such as `SpecUrl.BarChart.jsonSpec`.
    */
  inline def applyMods(mods: Seq[ujson.Value => Unit]): ujson.Value =
    val temp = ujson.copy(spec)
    for m <- mods do m(temp)
    end for
    temp
  end applyMods
end extension
