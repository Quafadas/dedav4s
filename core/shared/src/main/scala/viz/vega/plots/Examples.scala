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
import viz.PlotTarget
import viz.vega.plots.SpecUrl
import viz.PlotHasVegaDsl

type JsonMod = Seq[ujson.Value => Unit]
import viz.LowPriorityPlotTarget
import viz.PlotHasVegaLiteDsl

given doNothing: LowPriorityPlotTarget = viz.doNothing.doNothing

// Vega
case class BarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.BarChart)
case class StackedBarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.StackedBarChart)
case class GroupedBarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.GroupedBarChart)
case class NestedBarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.NestedBarChart)
case class PopulationPyramid(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.PopulationPyramid)
case class LineChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.LineChart)
case class AreaChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.AreaChart)
case class StackedAreaChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.StackedAreaChart)
case class HorizonGraph(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.HorizonGraph)
case class JobVoyager(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.JobVoyager)
case class PieChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.PieChart)
case class DonutChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.DonutChart)
case class RadialPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.RadialPlot)
case class RadarChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.RadarChart)
case class ScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ScatterPlot)
case class ScatterPlotNullValues(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ScatterPlotNullValues)
case class ConnectedScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ConnectedScatterPlot)
case class ErrorBars(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ErrorBars)
case class BarleyTrellisPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.BarleyTrellisPlot)
case class Regression(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Regression)
case class LoessRegression(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.LoessRegression)
case class LabeledScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.LabeledScatterPlot)
case class TopKPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.TopKPlot)
case class TopKPlotWithOthers(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.TopKPlotWithOthers)
case class Histogram(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Histogram)
case class HistogramNullValues(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.HistogramNullValues)
case class DotPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.DotPlot)
case class ProbabilityDensity(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ProbabilityDensity)
case class BoxPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.BoxPlot)
case class ViolinPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ViolinPlot)
case class BinnedScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.BinnedScatterPlot)
case class ContourPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ContourPlot)
case class WheatPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.WheatPlot)
case class QuantileQuantilePlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.QuantileQuantilePlot)
case class QuantileDotPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.QuantileDotPlot)
case class HypotheticalOutcomePlots(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.HypotheticalOutcomePlots)
case class TimeUnits(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.TimeUnits)
case class CountyUnemployment(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.CountyUnemployment)
case class DorlingCartogram(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.DorlingCartogram)
case class WorldMap(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.WorldMap)
case class Earthquakes(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Earthquakes)
case class Projections(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Projections)
case class ZoomableWorldMap(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ZoomableWorldMap)
case class DistortionComparison(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.DistortionComparison)
case class VolcanoContours(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.VolcanoContours)
case class WindVectors(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.WindVectors)
case class AnnualPrecipitation(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.AnnualPrecipitation)
case class TreeLayout(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.TreeLayout)
case class RadialTreeLayout(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.RadialTreeLayout)
case class Treemap(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Treemap)
case class CirclePacking(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.CirclePacking)
case class Sunburst(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Sunburst)
case class EdgeBundling(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.EdgeBundling)
case class ForceDirectedLayout(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ForceDirectedLayout)
case class ReorderableMatrix(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ReorderableMatrix)
case class ArcDiagram(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ArcDiagram)
case class AirportConnections(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.AirportConnections)
case class Heatmap(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Heatmap)
case class DensityHeatmaps(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.DensityHeatmaps)
case class ParallelCoordinates(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ParallelCoordinates)
case class WordCloud(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.WordCloud)
case class BeeswarmPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.BeeswarmPlot)
case class CalendarView(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.CalendarView)
case class BudgetForecasts(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.BudgetForecasts)
case class WheatAndWages(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.WheatAndWages)
case class FalkenseePopulation(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.FalkenseePopulation)
case class AnnualTemperature(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.AnnualTemperature)
case class WeeklyTemperature(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.WeeklyTemperature)
case class FlightPassengers(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.FlightPassengers)
case class Timelines(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Timelines)
case class UDistrictCuisine(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.UDistrictCuisine)
case class Clock(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Clock)
case class Watch(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Watch)
case class CrossfilterFlights(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.CrossfilterFlights)
case class OverviewPlusDetail(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.OverviewPlusDetail)
case class BrushingScatterPlots(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.BrushingScatterPlots)
case class ZoomableScatterPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ZoomableScatterPlot)
case class ZoomableBinnedPlot(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.ZoomableBinnedPlot)
case class GlobalDevelopment(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.GlobalDevelopment)
case class InteractiveLegend(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.InteractiveLegend)
case class StockIndexChart(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.StockIndexChart)
case class PiMonteCarlo(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.PiMonteCarlo)
case class Pacman(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Pacman)
case class Platformer(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaDsl
    with FromUrl(SpecUrl.Platformer)
//Vega Lite

case class SimpleBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.SimpleBarChartLite)
case class ResponsiveBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ResponsiveBarChartLite)
case class AggregateBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.AggregateBarChartLite)
case class AggregateBarChart_SortedLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.AggregateBarChart_SortedLite)
case class GroupedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.GroupedBarChartLite)
case class GroupedBarChart_MultipleMeasurewithRepeatLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.GroupedBarChart_MultipleMeasurewithRepeatLite)
case class StackedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.StackedBarChartLite)
case class StackedBarChartwithRoundedCornersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.StackedBarChartwithRoundedCornersLite)
case class HorizontalStackedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.HorizontalStackedBarChartLite)
case class Normalized_PercentageStackedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Normalized_PercentageStackedBarChartLite)
case class Normalized_PercentageStackedBarChartWithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Normalized_PercentageStackedBarChartWithLabelsLite)
case class GanttChart_RangedBarMarksLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.GanttChart_RangedBarMarksLite)
case class ABarChartEncodingColorNamesintheDataLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ABarChartEncodingColorNamesintheDataLite)
case class LayeredBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LayeredBarChartLite)
case class DivergingStackedBarChart_PopulationPyramidLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.DivergingStackedBarChart_PopulationPyramidLite)
case class DivergingStackedBarChart_withNeutralPartsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.DivergingStackedBarChart_withNeutralPartsLite)
case class BarChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BarChartwithLabelsLite)
case class BarChartwithLabelOverlaysLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BarChartwithLabelOverlaysLite)
case class BarChartshowingInitialsofMonthNamesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BarChartshowingInitialsofMonthNamesLite)
case class BarChartwithNegativeValuesandaZero_BaselineLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BarChartwithNegativeValuesandaZero_BaselineLite)
case class HorizontalBarChartwithNegativeValuesandLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.HorizontalBarChartwithNegativeValuesandLabelsLite)
case class BarChartwithaSpacing_SavingY_AxisLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BarChartwithaSpacing_SavingY_AxisLite)
case class HistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.HistogramLite)
case class Histogram_fromBinnedDataLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Histogram_fromBinnedDataLite)
case class Log_scaledHistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Log_scaledHistogramLite)
case class Non_linearHistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Non_linearHistogramLite)
case class RelativeFrequencyHistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.RelativeFrequencyHistogramLite)
case class DensityPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.DensityPlotLite)
case class StackedDensityEstimatesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.StackedDensityEstimatesLite)
case class TwoDHistogramScatterplotLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TwoDHistogramScatterplotLite)
case class TwoDHistogramHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TwoDHistogramHeatmapLite)
case class CumulativeFrequencyDistributionLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CumulativeFrequencyDistributionLite)
case class LayeredHistogramandCumulativeHistogramLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LayeredHistogramandCumulativeHistogramLite)
case class WilkinsonDotPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.WilkinsonDotPlotLite)
case class IsotypeDotPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.IsotypeDotPlotLite)
case class IsotypeDotPlotwithEmojiLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.IsotypeDotPlotwithEmojiLite)
case class ScatterplotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ScatterplotLite)
case class OneDStripPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.OneDStripPlotLite)
case class StripPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.StripPlotLite)
case class ColoredScatterplotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ColoredScatterplotLite)
case class TwoDHistogramScatterplot0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TwoDHistogramScatterplot0Lite)
case class BubblePlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BubblePlotLite)
case class ScatterplotwithNullValuesinGreyLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ScatterplotwithNullValuesinGreyLite)
case class ScatterplotwithFilledCirclesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ScatterplotwithFilledCirclesLite)
case class BubblePlot_GapminderLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BubblePlot_GapminderLite)
case class BubblePlot_NaturalDisastersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BubblePlot_NaturalDisastersLite)
case class ScatterPlotwithTextMarksLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ScatterPlotwithTextMarksLite)
case class Image_basedScatterPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Image_basedScatterPlotLite)
case class StripplotwithcustomaxisticklabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.StripplotwithcustomaxisticklabelsLite)
case class DotPlotwithJitteringLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.DotPlotwithJitteringLite)
case class LineChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartLite)
case class LineChartwithPointMarkersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartwithPointMarkersLite)
case class LineChartwithStrokedPointMarkersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartwithStrokedPointMarkersLite)
case class MultiSeriesLineChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MultiSeriesLineChartLite)
case class MultiSeriesLineChartwithRepeatOperatorLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MultiSeriesLineChartwithRepeatOperatorLite)
case class MultiSeriesLineChartwithHaloStrokeLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MultiSeriesLineChartwithHaloStrokeLite)
case class SlopeGraphLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.SlopeGraphLite)
case class StepChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.StepChartLite)
case class LineChartwithMonotoneInterpolationLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartwithMonotoneInterpolationLite)
case class LineChartwithConditionalAxisPropertiesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartwithConditionalAxisPropertiesLite)
case class ConnectedScatterplot_LineswithCustomPathsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ConnectedScatterplot_LineswithCustomPathsLite)
case class BumpChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BumpChartLite)
case class LineChartwithVaryingSize_usingthetrailmarkLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartwithVaryingSize_usingthetrailmarkLite)
case class AcometchartshowingchangesbetweenbetweentwostatesLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.AcometchartshowingchangesbetweenbetweentwostatesLite)
case class LineChartwithMarkersandInvalidValuesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartwithMarkersandInvalidValuesLite)
case class CarbonDioxideintheAtmosphereLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CarbonDioxideintheAtmosphereLite)
case class LineChartsShowingRanksOverTimeLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartsShowingRanksOverTimeLite)
case class DrawingSineandCosineCurveswiththeSequenceGeneratorLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.DrawingSineandCosineCurveswiththeSequenceGeneratorLite)
case class LinechartwithvaryingstrokedashLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LinechartwithvaryingstrokedashLite)
case class LinechartwithadashedpartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LinechartwithadashedpartLite)
case class AreaChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.AreaChartLite)
case class AreaChartwithGradientLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.AreaChartwithGradientLite)
case class AreaChartwithOverlayingLinesandPointMarkersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.AreaChartwithOverlayingLinesandPointMarkersLite)
case class StackedAreaChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.StackedAreaChartLite)
case class NormalizedStackedAreaChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.NormalizedStackedAreaChartLite)
case class StreamgraphLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.StreamgraphLite)
case class HorizonGraphLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.HorizonGraphLite)
case class TableHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TableHeatmapLite)
case class AnnualWeatherHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.AnnualWeatherHeatmapLite)
case class TwoDHistogramHeatmap0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TwoDHistogramHeatmap0Lite)
case class TableBubblePlot_GithubPunchCardLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TableBubblePlot_GithubPunchCardLite)
case class HeatmapwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.HeatmapwithLabelsLite)
case class LasagnaPlot_DenseTime_SeriesHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LasagnaPlot_DenseTime_SeriesHeatmapLite)
case class MosaicChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MosaicChartwithLabelsLite)
case class WindVectorMapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.WindVectorMapLite)
case class PieChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.PieChartLite)
case class DonutChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.DonutChartLite)
case class PieChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.PieChartwithLabelsLite)
case class RadialPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.RadialPlotLite)
case class PyramidPieChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.PyramidPieChartLite)
case class CalculatePercentageofTotalLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CalculatePercentageofTotalLite)
case class CalculateDifferencefromAverageLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CalculateDifferencefromAverageLite)
case class CalculateDifferencefromAnnualAverageLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CalculateDifferencefromAnnualAverageLite)
case class CalculateResidualsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CalculateResidualsLite)
case class LineChartsShowingRanksOverTime0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartsShowingRanksOverTime0Lite)
case class WaterfallChartofMonthlyProfitandLossLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.WaterfallChartofMonthlyProfitandLossLite)
case class FilteringTop_KItemsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.FilteringTop_KItemsLite)
case class Top_KPlotwithOthersLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Top_KPlotwithOthersLite)
case class UsingthelookuptransformtocombinedataLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.UsingthelookuptransformtocombinedataLite)
case class CumulativeFrequencyDistribution0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CumulativeFrequencyDistribution0Lite)
case class LayeredHistogramandCumulativeHistogram0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LayeredHistogramandCumulativeHistogram0Lite)
case class ParallelCoordinatePlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ParallelCoordinatePlotLite)
case class BarChartShowingArgmaxValueLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BarChartShowingArgmaxValueLite)
case class LayeringAveragesoverRawValuesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LayeringAveragesoverRawValuesLite)
case class LayeringRollingAveragesoverRawValuesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LayeringRollingAveragesoverRawValuesLite)
case class LineCharttoShowBenchmarkingResultsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineCharttoShowBenchmarkingResultsLite)
case class Quantile_QuantilePlot_QQPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Quantile_QuantilePlot_QQPlotLite)
case class LinearRegressionLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LinearRegressionLite)
case class LoessRegressionLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LoessRegressionLite)
case class ErrorBarsShowingConfidenceIntervalLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ErrorBarsShowingConfidenceIntervalLite)
case class ErrorBarsShowingStandardDeviationLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ErrorBarsShowingStandardDeviationLite)
case class LineChartwithConfidenceIntervalBandLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartwithConfidenceIntervalBandLite)
case class ScatterplotwithMeanandStandardDeviationOverlayLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ScatterplotwithMeanandStandardDeviationOverlayLite)
case class BoxPlotwithMin_MaxWhiskersLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BoxPlotwithMin_MaxWhiskersLite)
case class TukeyBoxPlot_One_5IQRLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TukeyBoxPlot_One_5IQRLite)
case class BoxPlotwithPre_CalculatedSummariesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BoxPlotwithPre_CalculatedSummariesLite)
case class SimpleBarChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.SimpleBarChartwithLabelsLite)
case class SimpleBarChartwithLabelsandEmojisLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.SimpleBarChartwithLabelsandEmojisLite)
case class LayeringtextoverheatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LayeringtextoverheatmapLite)
case class CarbonDioxideintheAtmosphere0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CarbonDioxideintheAtmosphere0Lite)
case class BarChartHighlightingValuesbeyondaThresholdLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BarChartHighlightingValuesbeyondaThresholdLite)
case class MeanoverlayoverprecipitationchartLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MeanoverlayoverprecipitationchartLite)
case class HistogramwithaGlobalMeanOverlayLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.HistogramwithaGlobalMeanOverlayLite)
case class LineChartwithHighlightedRectanglesLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LineChartwithHighlightedRectanglesLite)
case class LayeringAveragesoverRawValues0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LayeringAveragesoverRawValues0Lite)
case class LayeringRollingAveragesoverRawValues0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LayeringRollingAveragesoverRawValues0Lite)
case class DistributionsandMediansofLikertScaleRatingsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.DistributionsandMediansofLikertScaleRatingsLite)
case class ComparativeLikertScaleRatingsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ComparativeLikertScaleRatingsLite)
case class CandlestickChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CandlestickChartLite)
case class RangedDotPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.RangedDotPlotLite)
case class BulletChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BulletChartLite)
case class LayeredPlotwithDual_AxisLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LayeredPlotwithDual_AxisLite)
case class HorizonGraph0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.HorizonGraph0Lite)
case class WeeklyWeatherPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.WeeklyWeatherPlotLite)
case class WheatandWagesExampleLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.WheatandWagesExampleLite)
case class TrellisBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TrellisBarChartLite)
case class TrellisStackedBarChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TrellisStackedBarChartLite)
case class TrellisScatterPlot_wrappedLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TrellisScatterPlot_wrappedLite)
case class TrellisHistogramsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TrellisHistogramsLite)
case class TrellisScatterPlotShowingAnscombesQuartetLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TrellisScatterPlotShowingAnscombesQuartetLite)
case class BeckersBarleyTrellisPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BeckersBarleyTrellisPlotLite)
case class TrellisAreaLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TrellisAreaLite)
case class TrellisAreaPlotShowingAnnualTemperaturesinSeattleLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.TrellisAreaPlotShowingAnnualTemperaturesinSeattleLite)
case class FacetedDensityPlotLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.FacetedDensityPlotLite)
case class CompactTrellisGridofBarChartsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CompactTrellisGridofBarChartsLite)
case class RepeatandLayertoShowDifferentMovieMeasuresLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.RepeatandLayertoShowDifferentMovieMeasuresLite)
case class VerticalConcatenationLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.VerticalConcatenationLite)
case class HorizontallyRepeatedChartsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.HorizontallyRepeatedChartsLite)
case class InteractiveScatterplotMatrixLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.InteractiveScatterplotMatrixLite)
case class MarginalHistogramsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MarginalHistogramsLite)
case class DiscretizingscalesLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.DiscretizingscalesLite)
case class NestedViewConcatenationAlignedwithAxisminExtentLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.NestedViewConcatenationAlignedwithAxisminExtentLite)
case class PopulationPyramidLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.PopulationPyramidLite)
case class ChoroplethofUnemploymentRateperCountyLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ChoroplethofUnemploymentRateperCountyLite)
case class OneDotperZipcodeintheU_S_Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.OneDotperZipcodeintheU_S_Lite)
case class OneDotperAirportintheU_S_OverlayedonGeoshapeLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.OneDotperAirportintheU_S_OverlayedonGeoshapeLite)
case class Rules_linesegmentsConnectingSEAtoeveryAirportReachableviaDirectFlightsLite(
    override val mods: Seq[ujson.Value => Unit] = List()
)(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Rules_linesegmentsConnectingSEAtoeveryAirportReachableviaDirectFlightsLite)
case class ThreeChoroplethsRepresentingDisjointDatafromtheSameTableLite(
    override val mods: Seq[ujson.Value => Unit] = List()
)(using
    PlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ThreeChoroplethsRepresentingDisjointDatafromtheSameTableLite)
case class U_S_StateCapitalsOverlayedonaMapofU_S_Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.U_S_StateCapitalsOverlayedonaMapofU_S_Lite)
case class LinebetweenAirportsintheU_S_Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LinebetweenAirportsintheU_S_Lite)
case class IncomeintheU_S_byStateFacetedoverIncomeBracketsLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.IncomeintheU_S_byStateFacetedoverIncomeBracketsLite)
case class LondonTubeLinesLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.LondonTubeLinesLite)
case class ProjectionexplorerLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ProjectionexplorerLite)
case class BarChartwithHighlightingonHoverandSelectiononClickLite(override val mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BarChartwithHighlightingonHoverandSelectiononClickLite)
case class InteractiveLegendLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.InteractiveLegendLite)
case class ScatterplotwithExternalLinksandTooltipsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ScatterplotwithExternalLinksandTooltipsLite)
case class RectangularBrushLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.RectangularBrushLite)
case class AreaChartwithRectangularBrushLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.AreaChartwithRectangularBrushLite)
case class PaintbrushHighlightLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.PaintbrushHighlightLite)
case class ScatterplotPanZoomLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ScatterplotPanZoomLite)
case class QueryWidgetsLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.QueryWidgetsLite)
case class InteractiveAverageLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.InteractiveAverageLite)
case class MultiSeriesLineChartwithInteractiveHighlightLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MultiSeriesLineChartwithInteractiveHighlightLite)
case class MultiSeriesLineChartwithLabelsLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MultiSeriesLineChartwithLabelsLite)
case class MultiSeriesLineChartwithTooltipLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MultiSeriesLineChartwithTooltipLite)
case class MultiSeriesLineChartwithTooltip0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.MultiSeriesLineChartwithTooltip0Lite)
case class IsotypeGridLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.IsotypeGridLite)
case class BrushingScatterPlottoshowdataonatableLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BrushingScatterPlottoshowdataonatableLite)
case class SelectableHeatmapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.SelectableHeatmapLite)
case class BarChartwithaMinimapLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.BarChartwithaMinimapLite)
case class InteractiveIndexChartLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.InteractiveIndexChartLite)
case class Focus_Context_SmoothHistogramZoomingLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Focus_Context_SmoothHistogramZoomingLite)
case class OverviewandDetailLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.OverviewandDetailLite)
case class CrossfilterLite(override val mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.CrossfilterLite)
case class InteractiveScatterplotMatrix0Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.InteractiveScatterplotMatrix0Lite)
case class InteractiveDashboardwithCrossHighlightLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.InteractiveDashboardwithCrossHighlightLite)
case class SeattleWeatherExplorationLite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.SeattleWeatherExplorationLite)
case class ConnectionsamongMajorU_S_Airports_Lite(override val mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.ConnectionsamongMajorU_S_Airports_Lite)
case class Aninteractivescatterplotofglobalhealthstatisticsbycountryandyear_Lite(
    override val mods: Seq[ujson.Value => Unit] = List()
)(using
    LowPriorityPlotTarget
) extends PlotHasVegaLiteDsl
    with FromUrl(SpecUrl.Aninteractivescatterplotofglobalhealthstatisticsbycountryandyear_Lite)

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
      in => ??? // upickle.default.read[T & BarData](in)
    )
end BarChart

object PieChart extends viz.companions.AxisUtils {}
