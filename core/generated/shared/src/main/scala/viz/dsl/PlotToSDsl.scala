// package viz.dsl

// import viz.dsl.vega.VegaDsl
// import viz.dsl.vegaLite.VegaLiteDsl
// import io.circe.parser.decode
// import viz.dsl.*

// object syntax:
//   extension
//     PlotHasVegaDsl(using LowPriorityPlotTarget) extends WithBaseSpec:
//     def toDsl(): VegaDsl = decode[VegaDsl](baseSpec.toString()).fold(throw _, identity)
//   end PlotHasVegaDsl

//   trait PlotHasVegaLiteDsl(using LowPriorityPlotTarget) extends WithBaseSpec:
//     def toDsl(): VegaLiteDsl = decode[VegaLiteDsl](baseSpec.toString()).fold(throw _, identity)
//   end PlotHasVegaLiteDsl
