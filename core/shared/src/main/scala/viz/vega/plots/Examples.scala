/*
 * Copyright 2023 quafadas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viz.vega.plots

import viz.FromUrl
import viz.PlotHasVegaDsl

type JsonMod = Seq[ujson.Value => Unit]
import viz.LowPriorityPlotTarget
import viz.PlotHasVegaLiteDsl

given doNothing: LowPriorityPlotTarget = viz.doNothing.doNothing

// Vega
case class BarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BarChart)
    with PlotHasVegaDsl

case class StackedBarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StackedBarChart)
    with PlotHasVegaDsl
case class GroupedBarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.GroupedBarChart)
    with PlotHasVegaDsl
case class NestedBarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.NestedBarChart)
    with PlotHasVegaDsl
case class PopulationPyramid(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PopulationPyramid)
    with PlotHasVegaDsl
case class LineChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LineChart)
    with PlotHasVegaDsl
case class AreaChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AreaChart)
    with PlotHasVegaDsl
case class StackedAreaChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StackedAreaChart)
    with PlotHasVegaDsl
case class HorizonGraph(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HorizonGraph)
    with PlotHasVegaDsl
case class JobVoyager(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.JobVoyager)
    with PlotHasVegaDsl
case class PieChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PieChart)
    with PlotHasVegaDsl
case class DonutChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DonutChart)
    with PlotHasVegaDsl
case class RadialPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RadialPlot)
    with PlotHasVegaDsl
case class RadarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RadarChart)
    with PlotHasVegaDsl
case class ScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ScatterPlot)
    with PlotHasVegaDsl
case class ScatterPlotNullValues(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ScatterPlotNullValues)
    with PlotHasVegaDsl
case class ConnectedScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ConnectedScatterPlot)
    with PlotHasVegaDsl
case class ErrorBars(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ErrorBars)
    with PlotHasVegaDsl
case class BarleyTrellisPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BarleyTrellisPlot)
    with PlotHasVegaDsl
case class Regression(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Regression)
    with PlotHasVegaDsl
case class LoessRegression(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LoessRegression)
    with PlotHasVegaDsl
case class LabeledScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LabeledScatterPlot)
    with PlotHasVegaDsl
case class TopKPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TopKPlot)
    with PlotHasVegaDsl
case class TopKPlotWithOthers(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TopKPlotWithOthers)
    with PlotHasVegaDsl
case class Histogram(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Histogram)
    with PlotHasVegaDsl
case class HistogramNullValues(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HistogramNullValues)
    with PlotHasVegaDsl
case class DotPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DotPlot)
    with PlotHasVegaDsl
case class ProbabilityDensity(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ProbabilityDensity)
    with PlotHasVegaDsl
case class BoxPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BoxPlot)
    with PlotHasVegaDsl
case class ViolinPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ViolinPlot)
    with PlotHasVegaDsl
case class BinnedScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BinnedScatterPlot)
    with PlotHasVegaDsl
case class ContourPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ContourPlot)
    with PlotHasVegaDsl
case class WheatPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WheatPlot)
    with PlotHasVegaDsl
case class QuantileQuantilePlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.QuantileQuantilePlot)
    with PlotHasVegaDsl
case class QuantileDotPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.QuantileDotPlot)
    with PlotHasVegaDsl
case class HypotheticalOutcomePlots(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HypotheticalOutcomePlots)
    with PlotHasVegaDsl
case class TimeUnits(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TimeUnits)
    with PlotHasVegaDsl
case class CountyUnemployment(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CountyUnemployment)
    with PlotHasVegaDsl
case class DorlingCartogram(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DorlingCartogram)
    with PlotHasVegaDsl
case class WorldMap(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WorldMap)
    with PlotHasVegaDsl
case class Earthquakes(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Earthquakes)
    with PlotHasVegaDsl
case class Projections(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Projections)
    with PlotHasVegaDsl
case class ZoomableWorldMap(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ZoomableWorldMap)
    with PlotHasVegaDsl
case class DistortionComparison(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DistortionComparison)
    with PlotHasVegaDsl
case class VolcanoContours(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.VolcanoContours)
    with PlotHasVegaDsl
case class WindVectors(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WindVectors)
    with PlotHasVegaDsl
case class AnnualPrecipitation(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AnnualPrecipitation)
    with PlotHasVegaDsl
case class TreeLayout(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TreeLayout)
    with PlotHasVegaDsl
case class RadialTreeLayout(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RadialTreeLayout)
    with PlotHasVegaDsl
case class Treemap(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Treemap)
    with PlotHasVegaDsl
case class CirclePacking(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CirclePacking)
    with PlotHasVegaDsl
case class Sunburst(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Sunburst)
    with PlotHasVegaDsl
case class EdgeBundling(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.EdgeBundling)
    with PlotHasVegaDsl
case class ForceDirectedLayout(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ForceDirectedLayout)
    with PlotHasVegaDsl
case class ReorderableMatrix(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ReorderableMatrix)
    with PlotHasVegaDsl
case class ArcDiagram(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ArcDiagram)
    with PlotHasVegaDsl
case class AirportConnections(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AirportConnections)
    with PlotHasVegaDsl
case class Heatmap(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Heatmap)
    with PlotHasVegaDsl
case class DensityHeatmaps(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DensityHeatmaps)
    with PlotHasVegaDsl
case class ParallelCoordinates(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ParallelCoordinates)
    with PlotHasVegaDsl
case class WordCloud(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WordCloud)
    with PlotHasVegaDsl
case class BeeswarmPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BeeswarmPlot)
    with PlotHasVegaDsl
case class CalendarView(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CalendarView)
    with PlotHasVegaDsl
case class BudgetForecasts(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BudgetForecasts)
    with PlotHasVegaDsl
case class WheatAndWages(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WheatAndWages)
    with PlotHasVegaDsl
case class FalkenseePopulation(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.FalkenseePopulation)
    with PlotHasVegaDsl
case class AnnualTemperature(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AnnualTemperature)
    with PlotHasVegaDsl
case class WeeklyTemperature(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WeeklyTemperature)
    with PlotHasVegaDsl
case class FlightPassengers(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.FlightPassengers)
    with PlotHasVegaDsl
case class Timelines(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Timelines)
    with PlotHasVegaDsl
case class UDistrictCuisine(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.UDistrictCuisine)
    with PlotHasVegaDsl
case class Clock(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Clock)
    with PlotHasVegaDsl
case class Watch(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Watch)
    with PlotHasVegaDsl
case class CrossfilterFlights(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CrossfilterFlights)
    with PlotHasVegaDsl
case class OverviewPlusDetail(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.OverviewPlusDetail)
    with PlotHasVegaDsl
case class BrushingScatterPlots(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BrushingScatterPlots)
    with PlotHasVegaDsl
case class ZoomableScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ZoomableScatterPlot)
    with PlotHasVegaDsl
case class ZoomableBinnedPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ZoomableBinnedPlot)
    with PlotHasVegaDsl
case class GlobalDevelopment(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.GlobalDevelopment)
    with PlotHasVegaDsl
case class InteractiveLegend(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.InteractiveLegend)
    with PlotHasVegaDsl
case class StockIndexChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StockIndexChart)
    with PlotHasVegaDsl
case class PiMonteCarlo(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PiMonteCarlo)
    with PlotHasVegaDsl
case class Pacman(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Pacman)
    with PlotHasVegaDsl
case class Platformer(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Platformer)
    with PlotHasVegaDsl
//Vega Lite

case class SimpleBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.SimpleBarChartLite)
    with PlotHasVegaLiteDsl

case class ResponsiveBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ResponsiveBarChartLite)
    with PlotHasVegaLiteDsl

case class AggregateBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AggregateBarChartLite)
    with PlotHasVegaLiteDsl

case class AggregateBarChart_SortedLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.AggregateBarChart_SortedLite)
    with PlotHasVegaLiteDsl
case class GroupedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.GroupedBarChartLite)
    with PlotHasVegaLiteDsl

case class GroupedBarChart_MultipleMeasurewithRepeatLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.GroupedBarChart_MultipleMeasurewithRepeatLite)
    with PlotHasVegaLiteDsl
case class StackedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StackedBarChartLite)
    with PlotHasVegaLiteDsl

case class StackedBarChartwithRoundedCornersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.StackedBarChartwithRoundedCornersLite)
    with PlotHasVegaLiteDsl

case class HorizontalStackedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.HorizontalStackedBarChartLite)
    with PlotHasVegaLiteDsl
case class Normalized_PercentageStackedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Normalized_PercentageStackedBarChartLite)
    with PlotHasVegaLiteDsl
case class Normalized_PercentageStackedBarChartWithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Normalized_PercentageStackedBarChartWithLabelsLite)
    with PlotHasVegaLiteDsl
case class GanttChart_RangedBarMarksLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.GanttChart_RangedBarMarksLite)
    with PlotHasVegaLiteDsl
case class ABarChartEncodingColorNamesintheDataLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ABarChartEncodingColorNamesintheDataLite)
    with PlotHasVegaLiteDsl
case class LayeredBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LayeredBarChartLite)
    with PlotHasVegaLiteDsl

case class DivergingStackedBarChart_PopulationPyramidLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.DivergingStackedBarChart_PopulationPyramidLite)
    with PlotHasVegaLiteDsl
case class DivergingStackedBarChart_withNeutralPartsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.DivergingStackedBarChart_withNeutralPartsLite)
    with PlotHasVegaLiteDsl
case class BarChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BarChartwithLabelsLite)
    with PlotHasVegaLiteDsl
case class BarChartwithLabelOverlaysLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartwithLabelOverlaysLite)
    with PlotHasVegaLiteDsl
case class BarChartshowingInitialsofMonthNamesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartshowingInitialsofMonthNamesLite)
    with PlotHasVegaLiteDsl
case class BarChartwithNegativeValuesandaZero_BaselineLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartwithNegativeValuesandaZero_BaselineLite)
    with PlotHasVegaLiteDsl
case class HorizontalBarChartwithNegativeValuesandLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.HorizontalBarChartwithNegativeValuesandLabelsLite)
    with PlotHasVegaLiteDsl
case class BarChartwithaSpacing_SavingY_AxisLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartwithaSpacing_SavingY_AxisLite)
    with PlotHasVegaLiteDsl
case class HistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HistogramLite)
    with PlotHasVegaLiteDsl
case class Histogram_fromBinnedDataLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Histogram_fromBinnedDataLite)
    with PlotHasVegaLiteDsl
case class Log_scaledHistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Log_scaledHistogramLite)
    with PlotHasVegaLiteDsl
case class Non_linearHistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Non_linearHistogramLite)
    with PlotHasVegaLiteDsl
case class RelativeFrequencyHistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.RelativeFrequencyHistogramLite)
    with PlotHasVegaLiteDsl
case class DensityPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DensityPlotLite)
    with PlotHasVegaLiteDsl
case class StackedDensityEstimatesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.StackedDensityEstimatesLite)
    with PlotHasVegaLiteDsl
case class TwoDHistogramScatterplotLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TwoDHistogramScatterplotLite)
    with PlotHasVegaLiteDsl
case class TwoDHistogramHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TwoDHistogramHeatmapLite)
    with PlotHasVegaLiteDsl
case class CumulativeFrequencyDistributionLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CumulativeFrequencyDistributionLite)
    with PlotHasVegaLiteDsl
case class LayeredHistogramandCumulativeHistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeredHistogramandCumulativeHistogramLite)
    with PlotHasVegaLiteDsl
case class WilkinsonDotPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WilkinsonDotPlotLite)
    with PlotHasVegaLiteDsl
case class IsotypeDotPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.IsotypeDotPlotLite)
    with PlotHasVegaLiteDsl
case class IsotypeDotPlotwithEmojiLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.IsotypeDotPlotwithEmojiLite)
    with PlotHasVegaLiteDsl
case class ScatterplotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ScatterplotLite)
    with PlotHasVegaLiteDsl
case class OneDStripPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.OneDStripPlotLite)
    with PlotHasVegaLiteDsl
case class StripPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StripPlotLite)
    with PlotHasVegaLiteDsl
case class ColoredScatterplotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ColoredScatterplotLite)
    with PlotHasVegaLiteDsl
case class TwoDHistogramScatterplot0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TwoDHistogramScatterplot0Lite)
    with PlotHasVegaLiteDsl
case class BubblePlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BubblePlotLite)
    with PlotHasVegaLiteDsl
case class ScatterplotwithNullValuesinGreyLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterplotwithNullValuesinGreyLite)
    with PlotHasVegaLiteDsl
case class ScatterplotwithFilledCirclesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterplotwithFilledCirclesLite)
    with PlotHasVegaLiteDsl
case class BubblePlot_GapminderLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BubblePlot_GapminderLite)
    with PlotHasVegaLiteDsl
case class BubblePlot_NaturalDisastersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BubblePlot_NaturalDisastersLite)
    with PlotHasVegaLiteDsl
case class ScatterPlotwithTextMarksLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterPlotwithTextMarksLite)
    with PlotHasVegaLiteDsl
case class Image_basedScatterPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Image_basedScatterPlotLite)
    with PlotHasVegaLiteDsl
case class StripplotwithcustomaxisticklabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.StripplotwithcustomaxisticklabelsLite)
    with PlotHasVegaLiteDsl
case class DotPlotwithJitteringLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DotPlotwithJitteringLite)
    with PlotHasVegaLiteDsl
case class LineChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LineChartLite)
    with PlotHasVegaLiteDsl
case class LineChartwithPointMarkersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithPointMarkersLite)
    with PlotHasVegaLiteDsl
case class LineChartwithStrokedPointMarkersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithStrokedPointMarkersLite)
    with PlotHasVegaLiteDsl
case class MultiSeriesLineChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.MultiSeriesLineChartLite)
    with PlotHasVegaLiteDsl
case class MultiSeriesLineChartwithRepeatOperatorLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithRepeatOperatorLite)
    with PlotHasVegaLiteDsl
case class MultiSeriesLineChartwithHaloStrokeLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithHaloStrokeLite)
    with PlotHasVegaLiteDsl
case class SlopeGraphLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.SlopeGraphLite)
    with PlotHasVegaLiteDsl
case class StepChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StepChartLite)
    with PlotHasVegaLiteDsl
case class LineChartwithMonotoneInterpolationLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithMonotoneInterpolationLite)
    with PlotHasVegaLiteDsl
case class LineChartwithConditionalAxisPropertiesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithConditionalAxisPropertiesLite)
    with PlotHasVegaLiteDsl
case class ConnectedScatterplot_LineswithCustomPathsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ConnectedScatterplot_LineswithCustomPathsLite)
    with PlotHasVegaLiteDsl
case class BumpChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BumpChartLite)
    with PlotHasVegaLiteDsl
case class LineChartwithVaryingSize_usingthetrailmarkLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithVaryingSize_usingthetrailmarkLite)
    with PlotHasVegaLiteDsl
case class AcometchartshowingchangesbetweenbetweentwostatesLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.AcometchartshowingchangesbetweenbetweentwostatesLite)
    with PlotHasVegaLiteDsl

case class LineChartwithMarkersandInvalidValuesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithMarkersandInvalidValuesLite)
    with PlotHasVegaLiteDsl
case class CarbonDioxideintheAtmosphereLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CarbonDioxideintheAtmosphereLite)
    with PlotHasVegaLiteDsl
case class LineChartsShowingRanksOverTimeLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartsShowingRanksOverTimeLite)
    with PlotHasVegaLiteDsl
case class DrawingSineandCosineCurveswiththeSequenceGeneratorLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.DrawingSineandCosineCurveswiththeSequenceGeneratorLite)
    with PlotHasVegaLiteDsl

case class LinechartwithvaryingstrokedashLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LinechartwithvaryingstrokedashLite)
    with PlotHasVegaLiteDsl
case class LinechartwithadashedpartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LinechartwithadashedpartLite)
    with PlotHasVegaLiteDsl
case class AreaChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AreaChartLite)
    with PlotHasVegaLiteDsl
case class AreaChartwithGradientLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AreaChartwithGradientLite)
    with PlotHasVegaLiteDsl
case class AreaChartwithOverlayingLinesandPointMarkersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.AreaChartwithOverlayingLinesandPointMarkersLite)
    with PlotHasVegaLiteDsl
case class StackedAreaChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StackedAreaChartLite)
    with PlotHasVegaLiteDsl
case class NormalizedStackedAreaChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.NormalizedStackedAreaChartLite)
    with PlotHasVegaLiteDsl
case class StreamgraphLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StreamgraphLite)
    with PlotHasVegaLiteDsl
case class HorizonGraphLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HorizonGraphLite)
    with PlotHasVegaLiteDsl
case class TableHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TableHeatmapLite)
    with PlotHasVegaLiteDsl
case class AnnualWeatherHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AnnualWeatherHeatmapLite)
    with PlotHasVegaLiteDsl
case class TwoDHistogramHeatmap0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TwoDHistogramHeatmap0Lite)
    with PlotHasVegaLiteDsl
case class TableBubblePlot_GithubPunchCardLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TableBubblePlot_GithubPunchCardLite)
    with PlotHasVegaLiteDsl
case class HeatmapwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HeatmapwithLabelsLite)
    with PlotHasVegaLiteDsl
case class LasagnaPlot_DenseTime_SeriesHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LasagnaPlot_DenseTime_SeriesHeatmapLite)
    with PlotHasVegaLiteDsl
case class MosaicChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.MosaicChartwithLabelsLite)
    with PlotHasVegaLiteDsl
case class WindVectorMapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WindVectorMapLite)
    with PlotHasVegaLiteDsl
case class PieChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PieChartLite)
    with PlotHasVegaLiteDsl
case class DonutChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DonutChartLite)
    with PlotHasVegaLiteDsl
case class PieChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PieChartwithLabelsLite)
    with PlotHasVegaLiteDsl
case class RadialPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RadialPlotLite)
    with PlotHasVegaLiteDsl
case class PyramidPieChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PyramidPieChartLite)
    with PlotHasVegaLiteDsl
case class CalculatePercentageofTotalLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CalculatePercentageofTotalLite)
    with PlotHasVegaLiteDsl
case class CalculateDifferencefromAverageLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CalculateDifferencefromAverageLite)
    with PlotHasVegaLiteDsl
case class CalculateDifferencefromAnnualAverageLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CalculateDifferencefromAnnualAverageLite)
    with PlotHasVegaLiteDsl
case class CalculateResidualsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CalculateResidualsLite)
    with PlotHasVegaLiteDsl
case class LineChartsShowingRanksOverTime0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartsShowingRanksOverTime0Lite)
    with PlotHasVegaLiteDsl
case class WaterfallChartofMonthlyProfitandLossLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.WaterfallChartofMonthlyProfitandLossLite)
    with PlotHasVegaLiteDsl
case class FilteringTop_KItemsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.FilteringTop_KItemsLite)
    with PlotHasVegaLiteDsl
case class Top_KPlotwithOthersLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Top_KPlotwithOthersLite)
    with PlotHasVegaLiteDsl
case class UsingthelookuptransformtocombinedataLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.UsingthelookuptransformtocombinedataLite)
    with PlotHasVegaLiteDsl
case class CumulativeFrequencyDistribution0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CumulativeFrequencyDistribution0Lite)
    with PlotHasVegaLiteDsl
case class LayeredHistogramandCumulativeHistogram0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeredHistogramandCumulativeHistogram0Lite)
    with PlotHasVegaLiteDsl
case class ParallelCoordinatePlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ParallelCoordinatePlotLite)
    with PlotHasVegaLiteDsl
case class BarChartShowingArgmaxValueLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartShowingArgmaxValueLite)
    with PlotHasVegaLiteDsl
case class LayeringAveragesoverRawValuesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringAveragesoverRawValuesLite)
    with PlotHasVegaLiteDsl
case class LayeringRollingAveragesoverRawValuesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringRollingAveragesoverRawValuesLite)
    with PlotHasVegaLiteDsl
case class LineCharttoShowBenchmarkingResultsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineCharttoShowBenchmarkingResultsLite)
    with PlotHasVegaLiteDsl
case class Quantile_QuantilePlot_QQPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Quantile_QuantilePlot_QQPlotLite)
    with PlotHasVegaLiteDsl
case class LinearRegressionLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LinearRegressionLite)
    with PlotHasVegaLiteDsl
case class LoessRegressionLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LoessRegressionLite)
    with PlotHasVegaLiteDsl
case class ErrorBarsShowingConfidenceIntervalLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ErrorBarsShowingConfidenceIntervalLite)
    with PlotHasVegaLiteDsl
case class ErrorBarsShowingStandardDeviationLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ErrorBarsShowingStandardDeviationLite)
    with PlotHasVegaLiteDsl
case class LineChartwithConfidenceIntervalBandLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithConfidenceIntervalBandLite)
    with PlotHasVegaLiteDsl
case class ScatterplotwithMeanandStandardDeviationOverlayLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterplotwithMeanandStandardDeviationOverlayLite)
    with PlotHasVegaLiteDsl
case class BoxPlotwithMin_MaxWhiskersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BoxPlotwithMin_MaxWhiskersLite)
    with PlotHasVegaLiteDsl
case class TukeyBoxPlot_One_5IQRLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TukeyBoxPlot_One_5IQRLite)
    with PlotHasVegaLiteDsl
case class BoxPlotwithPre_CalculatedSummariesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BoxPlotwithPre_CalculatedSummariesLite)
    with PlotHasVegaLiteDsl
case class SimpleBarChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.SimpleBarChartwithLabelsLite)
    with PlotHasVegaLiteDsl
case class SimpleBarChartwithLabelsandEmojisLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.SimpleBarChartwithLabelsandEmojisLite)
    with PlotHasVegaLiteDsl
case class LayeringtextoverheatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringtextoverheatmapLite)
    with PlotHasVegaLiteDsl
case class CarbonDioxideintheAtmosphere0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CarbonDioxideintheAtmosphere0Lite)
    with PlotHasVegaLiteDsl
case class BarChartHighlightingValuesbeyondaThresholdLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartHighlightingValuesbeyondaThresholdLite)
    with PlotHasVegaLiteDsl
case class MeanoverlayoverprecipitationchartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MeanoverlayoverprecipitationchartLite)
    with PlotHasVegaLiteDsl
case class HistogramwithaGlobalMeanOverlayLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.HistogramwithaGlobalMeanOverlayLite)
    with PlotHasVegaLiteDsl
case class LineChartwithHighlightedRectanglesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithHighlightedRectanglesLite)
    with PlotHasVegaLiteDsl
case class LayeringAveragesoverRawValues0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringAveragesoverRawValues0Lite)
    with PlotHasVegaLiteDsl
case class LayeringRollingAveragesoverRawValues0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringRollingAveragesoverRawValues0Lite)
    with PlotHasVegaLiteDsl
case class DistributionsandMediansofLikertScaleRatingsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.DistributionsandMediansofLikertScaleRatingsLite)
    with PlotHasVegaLiteDsl
case class ComparativeLikertScaleRatingsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ComparativeLikertScaleRatingsLite)
    with PlotHasVegaLiteDsl
case class CandlestickChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CandlestickChartLite)
    with PlotHasVegaLiteDsl
case class RangedDotPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RangedDotPlotLite)
    with PlotHasVegaLiteDsl
case class BulletChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BulletChartLite)
    with PlotHasVegaLiteDsl
case class LayeredPlotwithDual_AxisLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeredPlotwithDual_AxisLite)
    with PlotHasVegaLiteDsl
case class HorizonGraph0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HorizonGraph0Lite)
    with PlotHasVegaLiteDsl
case class WeeklyWeatherPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WeeklyWeatherPlotLite)
    with PlotHasVegaLiteDsl
case class WheatandWagesExampleLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WheatandWagesExampleLite)
    with PlotHasVegaLiteDsl
case class TrellisBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TrellisBarChartLite)
    with PlotHasVegaLiteDsl
case class TrellisStackedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TrellisStackedBarChartLite)
    with PlotHasVegaLiteDsl
case class TrellisScatterPlot_wrappedLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TrellisScatterPlot_wrappedLite)
    with PlotHasVegaLiteDsl
case class TrellisHistogramsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TrellisHistogramsLite)
    with PlotHasVegaLiteDsl
case class TrellisScatterPlotShowingAnscombesQuartetLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TrellisScatterPlotShowingAnscombesQuartetLite)
    with PlotHasVegaLiteDsl
case class BeckersBarleyTrellisPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BeckersBarleyTrellisPlotLite)
    with PlotHasVegaLiteDsl
case class TrellisAreaLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TrellisAreaLite)
    with PlotHasVegaLiteDsl
case class TrellisAreaPlotShowingAnnualTemperaturesinSeattleLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TrellisAreaPlotShowingAnnualTemperaturesinSeattleLite)
    with PlotHasVegaLiteDsl
case class FacetedDensityPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.FacetedDensityPlotLite)
    with PlotHasVegaLiteDsl
case class CompactTrellisGridofBarChartsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CompactTrellisGridofBarChartsLite)
    with PlotHasVegaLiteDsl
case class RepeatandLayertoShowDifferentMovieMeasuresLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.RepeatandLayertoShowDifferentMovieMeasuresLite)
    with PlotHasVegaLiteDsl
case class VerticalConcatenationLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.VerticalConcatenationLite)
    with PlotHasVegaLiteDsl
case class HorizontallyRepeatedChartsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.HorizontallyRepeatedChartsLite)
    with PlotHasVegaLiteDsl
case class InteractiveScatterplotMatrixLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.InteractiveScatterplotMatrixLite)
    with PlotHasVegaLiteDsl
case class MarginalHistogramsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.MarginalHistogramsLite)
    with PlotHasVegaLiteDsl
case class DiscretizingscalesLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DiscretizingscalesLite)
    with PlotHasVegaLiteDsl
case class NestedViewConcatenationAlignedwithAxisminExtentLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.NestedViewConcatenationAlignedwithAxisminExtentLite)
    with PlotHasVegaLiteDsl
case class PopulationPyramidLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PopulationPyramidLite)
    with PlotHasVegaLiteDsl
case class ChoroplethofUnemploymentRateperCountyLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ChoroplethofUnemploymentRateperCountyLite)
    with PlotHasVegaLiteDsl
case class OneDotperZipcodeintheU_S_Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.OneDotperZipcodeintheU_S_Lite)
    with PlotHasVegaLiteDsl
case class OneDotperAirportintheU_S_OverlayedonGeoshapeLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.OneDotperAirportintheU_S_OverlayedonGeoshapeLite)
    with PlotHasVegaLiteDsl
case class Rules_linesegmentsConnectingSEAtoeveryAirportReachableviaDirectFlightsLite(
    override val mods: Seq[ujson.Value => Unit] = List()
)(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Rules_linesegmentsConnectingSEAtoeveryAirportReachableviaDirectFlightsLite)
    with PlotHasVegaLiteDsl
case class ThreeChoroplethsRepresentingDisjointDatafromtheSameTableLite(
    override val mods: Seq[ujson.Value => Unit] = List()
)(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ThreeChoroplethsRepresentingDisjointDatafromtheSameTableLite)
    with PlotHasVegaLiteDsl
case class U_S_StateCapitalsOverlayedonaMapofU_S_Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.U_S_StateCapitalsOverlayedonaMapofU_S_Lite)
    with PlotHasVegaLiteDsl
case class LinebetweenAirportsintheU_S_Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LinebetweenAirportsintheU_S_Lite)
    with PlotHasVegaLiteDsl
case class IncomeintheU_S_byStateFacetedoverIncomeBracketsLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.IncomeintheU_S_byStateFacetedoverIncomeBracketsLite)
    with PlotHasVegaLiteDsl
case class LondonTubeLinesLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LondonTubeLinesLite)
    with PlotHasVegaLiteDsl
case class ProjectionexplorerLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ProjectionexplorerLite)
    with PlotHasVegaLiteDsl
case class BarChartwithHighlightingonHoverandSelectiononClickLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartwithHighlightingonHoverandSelectiononClickLite)
    with PlotHasVegaLiteDsl
case class InteractiveLegendLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.InteractiveLegendLite)
    with PlotHasVegaLiteDsl
case class ScatterplotwithExternalLinksandTooltipsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterplotwithExternalLinksandTooltipsLite)
    with PlotHasVegaLiteDsl
case class RectangularBrushLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RectangularBrushLite)
    with PlotHasVegaLiteDsl
case class AreaChartwithRectangularBrushLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.AreaChartwithRectangularBrushLite)
    with PlotHasVegaLiteDsl
case class PaintbrushHighlightLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PaintbrushHighlightLite)
    with PlotHasVegaLiteDsl
case class ScatterplotPanZoomLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ScatterplotPanZoomLite)
    with PlotHasVegaLiteDsl
case class QueryWidgetsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.QueryWidgetsLite)
    with PlotHasVegaLiteDsl
case class InteractiveAverageLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.InteractiveAverageLite)
    with PlotHasVegaLiteDsl
case class MultiSeriesLineChartwithInteractiveHighlightLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithInteractiveHighlightLite)
    with PlotHasVegaLiteDsl
case class MultiSeriesLineChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithLabelsLite)
    with PlotHasVegaLiteDsl
case class MultiSeriesLineChartwithTooltipLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithTooltipLite)
    with PlotHasVegaLiteDsl
case class MultiSeriesLineChartwithTooltip0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithTooltip0Lite)
    with PlotHasVegaLiteDsl
case class IsotypeGridLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.IsotypeGridLite)
    with PlotHasVegaLiteDsl
case class BrushingScatterPlottoshowdataonatableLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BrushingScatterPlottoshowdataonatableLite)
    with PlotHasVegaLiteDsl
case class SelectableHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.SelectableHeatmapLite)
    with PlotHasVegaLiteDsl
case class BarChartwithaMinimapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BarChartwithaMinimapLite)
    with PlotHasVegaLiteDsl
case class InteractiveIndexChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.InteractiveIndexChartLite)
    with PlotHasVegaLiteDsl
case class Focus_Context_SmoothHistogramZoomingLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Focus_Context_SmoothHistogramZoomingLite)
    with PlotHasVegaLiteDsl
case class OverviewandDetailLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.OverviewandDetailLite)
    with PlotHasVegaLiteDsl
case class CrossfilterLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CrossfilterLite)
    with PlotHasVegaLiteDsl
case class InteractiveScatterplotMatrix0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.InteractiveScatterplotMatrix0Lite)
    with PlotHasVegaLiteDsl
case class InteractiveDashboardwithCrossHighlightLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.InteractiveDashboardwithCrossHighlightLite)
    with PlotHasVegaLiteDsl
case class SeattleWeatherExplorationLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.SeattleWeatherExplorationLite)
    with PlotHasVegaLiteDsl
case class ConnectionsamongMajorU_S_Airports_Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ConnectionsamongMajorU_S_Airports_Lite)
    with PlotHasVegaLiteDsl
case class Aninteractivescatterplotofglobalhealthstatisticsbycountryandyear_Lite(
    override val mods: Seq[ujson.Value => Unit] = List()
)(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Aninteractivescatterplotofglobalhealthstatisticsbycountryandyear_Lite)
    with PlotHasVegaLiteDsl

object BarChart extends viz.companions.AxisUtils:
  import upickle.default.*
  import ujson.Obj
  import ujson.Value

  trait BarData {}

  trait BarPlottable(val category: String, val amount: Double) extends BarData
  trait MarkColour(val colour: String) extends BarData

  val takeColourFromData: (ujson.Value => Unit) = spec =>
    spec("marks")(0)("encode")("update")("fill") = Obj("field" -> "colour")

  // def replaceBarData[T <: BarData](d: Seq[BarData]) = writeJs(d)

  given enc[JsValue, T <: BarData](using upickleDefault: ReadWriter[T]): ReadWriter[T & BarData] =
    readwriter[ujson.Value].bimap[T & BarData](
      in =>
        var toMerge: scala.collection.mutable.ArraySeq[Obj] = scala.collection.mutable.ArraySeq()
        val vanilla = writeJs(in)(upickleDefault).obj

        in match
          case colorful: MarkColour =>
            toMerge :+= Obj("colour" -> colorful.colour)
          case _ => ()
        end match

        in match
          case barData: BarPlottable =>
            toMerge :+= Obj("category" -> barData.category, "amount" -> barData.amount)
          case _ => ()
        end match
        toMerge.fold[Obj](vanilla) { case (start, next) => start.value ++ next.value }
      ,
      _ => ??? // upickle.default.read[T & BarData](in)
    )
end BarChart

object PieChart extends viz.companions.AxisUtils {}
