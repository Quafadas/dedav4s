package viz.vega.plots

import viz.FromResource

import viz.PlotTarget

case class SunburstDrag(override val mods: JsonMod = List())(using PlotTarget) extends FromResource:
  override lazy val path = "SunburstDrag.json"

case class SimpleRegression(override val mods: JsonMod = List())(using PlotTarget) extends FromResource:
  override lazy val path = "SimpleRegression.json"

case class SeriesScatter(override val mods: Seq[ujson.Value => Unit] = List())(using PlotTarget) extends FromResource:
  override lazy val path = "SeriesScatter.json"
