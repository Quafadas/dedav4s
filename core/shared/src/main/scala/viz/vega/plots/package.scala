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
import viz.LowPriorityPlotTarget

given doNothing: LowPriorityPlotTarget = viz.doNothing.doNothing
type JsonMod = Seq[ujson.Value => Unit]

// Vega
case class BarChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BarChart)

case class StackedBarChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StackedBarChart)

case class GroupedBarChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.GroupedBarChart)

case class NestedBarChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.NestedBarChart)

case class PopulationPyramid(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PopulationPyramid)

case class LineChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LineChart)

case class AreaChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AreaChart)

case class StackedAreaChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StackedAreaChart)

case class HorizonGraph(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HorizonGraph)

case class JobVoyager(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.JobVoyager)

case class PieChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PieChart)

case class DonutChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DonutChart)

case class RadialPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RadialPlot)

case class RadarChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RadarChart)

case class ScatterPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ScatterPlot)

case class ScatterPlotNullValues(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ScatterPlotNullValues)

case class ConnectedScatterPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ConnectedScatterPlot)

case class ErrorBars(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ErrorBars)

case class BarleyTrellisPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BarleyTrellisPlot)

case class Regression(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Regression)

case class LoessRegression(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LoessRegression)

case class LabeledScatterPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LabeledScatterPlot)

case class TopKPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TopKPlot)

case class TopKPlotWithOthers(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TopKPlotWithOthers)

case class Histogram(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Histogram)

case class HistogramNullValues(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HistogramNullValues)

case class DotPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DotPlot)

case class ProbabilityDensity(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ProbabilityDensity)

case class BoxPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BoxPlot)

case class ViolinPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ViolinPlot)

case class BinnedScatterPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BinnedScatterPlot)

case class ContourPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ContourPlot)

case class WheatPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WheatPlot)

case class QuantileQuantilePlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.QuantileQuantilePlot)

case class QuantileDotPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.QuantileDotPlot)

case class HypotheticalOutcomePlots(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HypotheticalOutcomePlots)

case class TimeUnits(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TimeUnits)

case class CountyUnemployment(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CountyUnemployment)

case class DorlingCartogram(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DorlingCartogram)

case class WorldMap(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WorldMap)

case class Earthquakes(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Earthquakes)

case class Projections(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Projections)

case class ZoomableWorldMap(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ZoomableWorldMap)

case class DistortionComparison(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DistortionComparison)

case class VolcanoContours(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.VolcanoContours)

case class WindVectors(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WindVectors)

case class AnnualPrecipitation(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AnnualPrecipitation)

case class TreeLayout(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TreeLayout)

case class RadialTreeLayout(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RadialTreeLayout)

case class Treemap(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Treemap)

case class CirclePacking(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CirclePacking)

case class Sunburst(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Sunburst)

case class EdgeBundling(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.EdgeBundling)

case class ForceDirectedLayout(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ForceDirectedLayout)

case class ReorderableMatrix(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ReorderableMatrix)

case class ArcDiagram(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ArcDiagram)

case class AirportConnections(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AirportConnections)

case class Heatmap(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Heatmap)

case class DensityHeatmaps(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DensityHeatmaps)

case class ParallelCoordinates(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ParallelCoordinates)

case class WordCloud(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WordCloud)

case class BeeswarmPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BeeswarmPlot)

case class CalendarView(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CalendarView)

case class BudgetForecasts(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BudgetForecasts)

case class WheatAndWages(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WheatAndWages)

case class FalkenseePopulation(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.FalkenseePopulation)

case class AnnualTemperature(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AnnualTemperature)

case class WeeklyTemperature(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WeeklyTemperature)

case class FlightPassengers(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.FlightPassengers)

case class Timelines(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Timelines)

case class UDistrictCuisine(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.UDistrictCuisine)

case class Clock(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Clock)

case class Watch(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Watch)

case class CrossfilterFlights(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CrossfilterFlights)

case class OverviewPlusDetail(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.OverviewPlusDetail)

case class BrushingScatterPlots(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BrushingScatterPlots)

case class ZoomableScatterPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ZoomableScatterPlot)

case class ZoomableBinnedPlot(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ZoomableBinnedPlot)

case class GlobalDevelopment(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.GlobalDevelopment)

case class InteractiveLegend(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.InteractiveLegend)

case class StockIndexChart(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StockIndexChart)

case class PiMonteCarlo(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PiMonteCarlo)

case class Pacman(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Pacman)

case class Platformer(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Platformer)

//Vega Lite

case class SimpleBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.SimpleBarChartLite)

case class ResponsiveBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ResponsiveBarChartLite)

case class AggregateBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AggregateBarChartLite)

case class AggregateBarChart_SortedLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.AggregateBarChart_SortedLite)

case class GroupedBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.GroupedBarChartLite)

case class GroupedBarChart_MultipleMeasurewithRepeatLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.GroupedBarChart_MultipleMeasurewithRepeatLite)

case class StackedBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StackedBarChartLite)

case class StackedBarChartwithRoundedCornersLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.StackedBarChartwithRoundedCornersLite)

case class HorizontalStackedBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.HorizontalStackedBarChartLite)

case class Normalized_PercentageStackedBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Normalized_PercentageStackedBarChartLite)

case class Normalized_PercentageStackedBarChartWithLabelsLite(mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Normalized_PercentageStackedBarChartWithLabelsLite)

case class GanttChart_RangedBarMarksLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.GanttChart_RangedBarMarksLite)

case class ABarChartEncodingColorNamesintheDataLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ABarChartEncodingColorNamesintheDataLite)

case class LayeredBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LayeredBarChartLite)

case class DivergingStackedBarChart_PopulationPyramidLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.DivergingStackedBarChart_PopulationPyramidLite)

case class DivergingStackedBarChart_withNeutralPartsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.DivergingStackedBarChart_withNeutralPartsLite)

case class BarChartwithLabelsLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BarChartwithLabelsLite)

case class BarChartwithLabelOverlaysLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartwithLabelOverlaysLite)

case class BarChartshowingInitialsofMonthNamesLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartshowingInitialsofMonthNamesLite)

case class BarChartwithNegativeValuesandaZero_BaselineLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartwithNegativeValuesandaZero_BaselineLite)

case class HorizontalBarChartwithNegativeValuesandLabelsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.HorizontalBarChartwithNegativeValuesandLabelsLite)

case class BarChartwithaSpacing_SavingY_AxisLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartwithaSpacing_SavingY_AxisLite)

case class HistogramLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HistogramLite)

case class Histogram_fromBinnedDataLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Histogram_fromBinnedDataLite)

case class Log_scaledHistogramLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Log_scaledHistogramLite)

case class Non_linearHistogramLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Non_linearHistogramLite)

case class RelativeFrequencyHistogramLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.RelativeFrequencyHistogramLite)

case class DensityPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DensityPlotLite)

case class StackedDensityEstimatesLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.StackedDensityEstimatesLite)

case class TwoDHistogramScatterplotLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TwoDHistogramScatterplotLite)

case class TwoDHistogramHeatmapLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TwoDHistogramHeatmapLite)

case class CumulativeFrequencyDistributionLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CumulativeFrequencyDistributionLite)

case class LayeredHistogramandCumulativeHistogramLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeredHistogramandCumulativeHistogramLite)

case class WilkinsonDotPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WilkinsonDotPlotLite)

case class IsotypeDotPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.IsotypeDotPlotLite)

case class IsotypeDotPlotwithEmojiLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.IsotypeDotPlotwithEmojiLite)

case class ScatterplotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ScatterplotLite)

case class OneDStripPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.OneDStripPlotLite)

case class StripPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StripPlotLite)

case class ColoredScatterplotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ColoredScatterplotLite)

case class TwoDHistogramScatterplot0Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TwoDHistogramScatterplot0Lite)

case class BubblePlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BubblePlotLite)

case class ScatterplotwithNullValuesinGreyLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterplotwithNullValuesinGreyLite)

case class ScatterplotwithFilledCirclesLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterplotwithFilledCirclesLite)

case class BubblePlot_GapminderLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BubblePlot_GapminderLite)

case class BubblePlot_NaturalDisastersLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BubblePlot_NaturalDisastersLite)

case class ScatterPlotwithTextMarksLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterPlotwithTextMarksLite)

case class Image_basedScatterPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Image_basedScatterPlotLite)

case class StripplotwithcustomaxisticklabelsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.StripplotwithcustomaxisticklabelsLite)

case class DotPlotwithJitteringLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DotPlotwithJitteringLite)

case class LineChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LineChartLite)

case class LineChartwithPointMarkersLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithPointMarkersLite)

case class LineChartwithStrokedPointMarkersLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithStrokedPointMarkersLite)

case class MultiSeriesLineChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.MultiSeriesLineChartLite)

case class MultiSeriesLineChartwithRepeatOperatorLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithRepeatOperatorLite)

case class MultiSeriesLineChartwithHaloStrokeLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithHaloStrokeLite)

case class SlopeGraphLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.SlopeGraphLite)

case class StepChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StepChartLite)

case class LineChartwithMonotoneInterpolationLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithMonotoneInterpolationLite)

case class LineChartwithConditionalAxisPropertiesLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithConditionalAxisPropertiesLite)

case class ConnectedScatterplot_LineswithCustomPathsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ConnectedScatterplot_LineswithCustomPathsLite)

case class BumpChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BumpChartLite)

case class LineChartwithVaryingSize_usingthetrailmarkLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithVaryingSize_usingthetrailmarkLite)

case class AcometchartshowingchangesbetweenbetweentwostatesLite(mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.AcometchartshowingchangesbetweenbetweentwostatesLite)

case class LineChartwithMarkersandInvalidValuesLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithMarkersandInvalidValuesLite)

case class CarbonDioxideintheAtmosphereLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CarbonDioxideintheAtmosphereLite)

case class LineChartsShowingRanksOverTimeLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartsShowingRanksOverTimeLite)

case class DrawingSineandCosineCurveswiththeSequenceGeneratorLite(mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.DrawingSineandCosineCurveswiththeSequenceGeneratorLite)

case class LinechartwithvaryingstrokedashLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LinechartwithvaryingstrokedashLite)

case class LinechartwithadashedpartLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LinechartwithadashedpartLite)

case class AreaChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AreaChartLite)

case class AreaChartwithGradientLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AreaChartwithGradientLite)

case class AreaChartwithOverlayingLinesandPointMarkersLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.AreaChartwithOverlayingLinesandPointMarkersLite)

case class StackedAreaChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StackedAreaChartLite)

case class NormalizedStackedAreaChartLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.NormalizedStackedAreaChartLite)

case class StreamgraphLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.StreamgraphLite)

case class HorizonGraphLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HorizonGraphLite)

case class TableHeatmapLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TableHeatmapLite)

case class AnnualWeatherHeatmapLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.AnnualWeatherHeatmapLite)

case class TwoDHistogramHeatmap0Lite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TwoDHistogramHeatmap0Lite)

case class TableBubblePlot_GithubPunchCardLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TableBubblePlot_GithubPunchCardLite)

case class HeatmapwithLabelsLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HeatmapwithLabelsLite)

case class LasagnaPlot_DenseTime_SeriesHeatmapLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LasagnaPlot_DenseTime_SeriesHeatmapLite)

case class MosaicChartwithLabelsLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.MosaicChartwithLabelsLite)

case class WindVectorMapLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WindVectorMapLite)

case class PieChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PieChartLite)

case class DonutChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DonutChartLite)

case class PieChartwithLabelsLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PieChartwithLabelsLite)

case class RadialPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RadialPlotLite)

case class PyramidPieChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PyramidPieChartLite)

case class CalculatePercentageofTotalLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CalculatePercentageofTotalLite)

case class CalculateDifferencefromAverageLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CalculateDifferencefromAverageLite)

case class CalculateDifferencefromAnnualAverageLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CalculateDifferencefromAnnualAverageLite)

case class CalculateResidualsLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CalculateResidualsLite)

case class LineChartsShowingRanksOverTime0Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartsShowingRanksOverTime0Lite)

case class WaterfallChartofMonthlyProfitandLossLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.WaterfallChartofMonthlyProfitandLossLite)

case class FilteringTop_KItemsLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.FilteringTop_KItemsLite)

case class Top_KPlotwithOthersLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Top_KPlotwithOthersLite)

case class UsingthelookuptransformtocombinedataLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.UsingthelookuptransformtocombinedataLite)

case class CumulativeFrequencyDistribution0Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CumulativeFrequencyDistribution0Lite)

case class LayeredHistogramandCumulativeHistogram0Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeredHistogramandCumulativeHistogram0Lite)

case class ParallelCoordinatePlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ParallelCoordinatePlotLite)

case class BarChartShowingArgmaxValueLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartShowingArgmaxValueLite)

case class LayeringAveragesoverRawValuesLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringAveragesoverRawValuesLite)

case class LayeringRollingAveragesoverRawValuesLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringRollingAveragesoverRawValuesLite)

case class LineCharttoShowBenchmarkingResultsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineCharttoShowBenchmarkingResultsLite)

case class Quantile_QuantilePlot_QQPlotLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Quantile_QuantilePlot_QQPlotLite)

case class LinearRegressionLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LinearRegressionLite)

case class LoessRegressionLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LoessRegressionLite)

case class ErrorBarsShowingConfidenceIntervalLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ErrorBarsShowingConfidenceIntervalLite)

case class ErrorBarsShowingStandardDeviationLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ErrorBarsShowingStandardDeviationLite)

case class LineChartwithConfidenceIntervalBandLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithConfidenceIntervalBandLite)

case class ScatterplotwithMeanandStandardDeviationOverlayLite(mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterplotwithMeanandStandardDeviationOverlayLite)

case class BoxPlotwithMin_MaxWhiskersLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BoxPlotwithMin_MaxWhiskersLite)

case class TukeyBoxPlot_One_5IQRLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TukeyBoxPlot_One_5IQRLite)

case class BoxPlotwithPre_CalculatedSummariesLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BoxPlotwithPre_CalculatedSummariesLite)

case class SimpleBarChartwithLabelsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.SimpleBarChartwithLabelsLite)

case class SimpleBarChartwithLabelsandEmojisLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.SimpleBarChartwithLabelsandEmojisLite)

case class LayeringtextoverheatmapLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringtextoverheatmapLite)

case class CarbonDioxideintheAtmosphere0Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CarbonDioxideintheAtmosphere0Lite)

case class BarChartHighlightingValuesbeyondaThresholdLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartHighlightingValuesbeyondaThresholdLite)

case class MeanoverlayoverprecipitationchartLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MeanoverlayoverprecipitationchartLite)

case class HistogramwithaGlobalMeanOverlayLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.HistogramwithaGlobalMeanOverlayLite)

case class LineChartwithHighlightedRectanglesLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LineChartwithHighlightedRectanglesLite)

case class LayeringAveragesoverRawValues0Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringAveragesoverRawValues0Lite)

case class LayeringRollingAveragesoverRawValues0Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeringRollingAveragesoverRawValues0Lite)

case class DistributionsandMediansofLikertScaleRatingsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.DistributionsandMediansofLikertScaleRatingsLite)

case class ComparativeLikertScaleRatingsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ComparativeLikertScaleRatingsLite)

case class CandlestickChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CandlestickChartLite)

case class RangedDotPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RangedDotPlotLite)

case class BulletChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BulletChartLite)

case class LayeredPlotwithDual_AxisLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LayeredPlotwithDual_AxisLite)

case class HorizonGraph0Lite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.HorizonGraph0Lite)

case class WeeklyWeatherPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WeeklyWeatherPlotLite)

case class WheatandWagesExampleLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.WheatandWagesExampleLite)

case class TrellisBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TrellisBarChartLite)

case class TrellisStackedBarChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TrellisStackedBarChartLite)

case class TrellisScatterPlot_wrappedLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TrellisScatterPlot_wrappedLite)

case class TrellisHistogramsLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TrellisHistogramsLite)

case class TrellisScatterPlotShowingAnscombesQuartetLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TrellisScatterPlotShowingAnscombesQuartetLite)

case class BeckersBarleyTrellisPlotLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BeckersBarleyTrellisPlotLite)

case class TrellisAreaLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.TrellisAreaLite)

case class TrellisAreaPlotShowingAnnualTemperaturesinSeattleLite(mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.TrellisAreaPlotShowingAnnualTemperaturesinSeattleLite)

case class FacetedDensityPlotLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.FacetedDensityPlotLite)

case class CompactTrellisGridofBarChartsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.CompactTrellisGridofBarChartsLite)

case class RepeatandLayertoShowDifferentMovieMeasuresLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.RepeatandLayertoShowDifferentMovieMeasuresLite)

case class VerticalConcatenationLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.VerticalConcatenationLite)

case class HorizontallyRepeatedChartsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.HorizontallyRepeatedChartsLite)

case class InteractiveScatterplotMatrixLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.InteractiveScatterplotMatrixLite)

case class MarginalHistogramsLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.MarginalHistogramsLite)

case class DiscretizingscalesLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.DiscretizingscalesLite)

case class NestedViewConcatenationAlignedwithAxisminExtentLite(mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.NestedViewConcatenationAlignedwithAxisminExtentLite)

case class PopulationPyramidLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PopulationPyramidLite)

case class ChoroplethofUnemploymentRateperCountyLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ChoroplethofUnemploymentRateperCountyLite)

case class OneDotperZipcodeintheU_S_Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.OneDotperZipcodeintheU_S_Lite)

case class OneDotperAirportintheU_S_OverlayedonGeoshapeLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.OneDotperAirportintheU_S_OverlayedonGeoshapeLite)

case class Rules_linesegmentsConnectingSEAtoeveryAirportReachableviaDirectFlightsLite(
    mods: Seq[ujson.Value => Unit] = List()
)(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Rules_linesegmentsConnectingSEAtoeveryAirportReachableviaDirectFlightsLite)

case class ThreeChoroplethsRepresentingDisjointDatafromtheSameTableLite(
    mods: Seq[ujson.Value => Unit] = List()
)(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ThreeChoroplethsRepresentingDisjointDatafromtheSameTableLite)

case class U_S_StateCapitalsOverlayedonaMapofU_S_Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.U_S_StateCapitalsOverlayedonaMapofU_S_Lite)

case class LinebetweenAirportsintheU_S_Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.LinebetweenAirportsintheU_S_Lite)

case class IncomeintheU_S_byStateFacetedoverIncomeBracketsLite(mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.IncomeintheU_S_byStateFacetedoverIncomeBracketsLite)

case class LondonTubeLinesLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.LondonTubeLinesLite)

case class ProjectionexplorerLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ProjectionexplorerLite)

case class BarChartwithHighlightingonHoverandSelectiononClickLite(mods: Seq[ujson.Value => Unit] = List())(
    using LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BarChartwithHighlightingonHoverandSelectiononClickLite)

case class InteractiveLegendLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.InteractiveLegendLite)

case class ScatterplotwithExternalLinksandTooltipsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ScatterplotwithExternalLinksandTooltipsLite)

case class RectangularBrushLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.RectangularBrushLite)

case class AreaChartwithRectangularBrushLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.AreaChartwithRectangularBrushLite)

case class PaintbrushHighlightLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.PaintbrushHighlightLite)

case class ScatterplotPanZoomLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.ScatterplotPanZoomLite)

case class QueryWidgetsLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.QueryWidgetsLite)

case class InteractiveAverageLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.InteractiveAverageLite)

case class MultiSeriesLineChartwithInteractiveHighlightLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithInteractiveHighlightLite)

case class MultiSeriesLineChartwithLabelsLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithLabelsLite)

case class MultiSeriesLineChartwithTooltipLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithTooltipLite)

case class MultiSeriesLineChartwithTooltip0Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.MultiSeriesLineChartwithTooltip0Lite)

case class IsotypeGridLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.IsotypeGridLite)

case class BrushingScatterPlottoshowdataonatableLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.BrushingScatterPlottoshowdataonatableLite)

case class SelectableHeatmapLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.SelectableHeatmapLite)

case class BarChartwithaMinimapLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.BarChartwithaMinimapLite)

case class InteractiveIndexChartLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.InteractiveIndexChartLite)

case class Focus_Context_SmoothHistogramZoomingLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.Focus_Context_SmoothHistogramZoomingLite)

case class OverviewandDetailLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.OverviewandDetailLite)

case class CrossfilterLite(mods: Seq[ujson.Value => Unit] = List())(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.CrossfilterLite)

case class InteractiveScatterplotMatrix0Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.InteractiveScatterplotMatrix0Lite)

case class InteractiveDashboardwithCrossHighlightLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.InteractiveDashboardwithCrossHighlightLite)

case class SeattleWeatherExplorationLite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.SeattleWeatherExplorationLite)

case class ConnectionsamongMajorU_S_Airports_Lite(mods: Seq[ujson.Value => Unit] = List())(using
    LowPriorityPlotTarget
) extends FromUrl(SpecUrl.ConnectionsamongMajorU_S_Airports_Lite)

case class Aninteractivescatterplotofglobalhealthstatisticsbycountryandyear_Lite(
    mods: Seq[ujson.Value => Unit] = List()
)(using LowPriorityPlotTarget)
    extends FromUrl(SpecUrl.Aninteractivescatterplotofglobalhealthstatisticsbycountryandyear_Lite)

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
        val vanilla = writeJs(in)(using upickleDefault).obj

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
