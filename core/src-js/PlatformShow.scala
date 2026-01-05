package viz

type VizReturn = Unit

// @nowarn
// trait PlatformPlot:

// end PlatformPlot

// object Plottable:
//   given ujsonMod: PlatformPlot = new PlatformPlot {}
//   private def applyMods(spec: ujson.Value, mods: Seq[ujson.Value => Unit]): ujson.Value =
//     val temp = spec
//     for m <- mods do m(temp)
//     end for
//     temp
//   end applyMods

//   given PlatformPlot[SpecUrl] with
//     extension (plottable: SpecUrl)(using plotTarget: LowPriorityPlotTarget, chartLibrary: ChartLibrary)
//       def plot(
//           mods: Seq[ujson.Value => Unit]
//       ): VizReturn =
//         val spec = plottable.jsonSpec
//         val modifiedSpec = applyMods(spec, mods)
//         plotTarget.show(modifiedSpec.toString, chartLibrary)

//       end plot

//       def plot: VizReturn =
//         val spec = plottable.jsonSpec
//         plotTarget.show(spec.toString(), chartLibrary)

//       end plot

//     end extension
//   end given

//   def show(inDiv: html.Div): Unit =
//     val anId = inDiv.id
//     val _ = if anId.isEmpty then
//       val temp = java.util.UUID.randomUUID()
//       inDiv.setAttribute("id", temp.toString())
//     else anId

//     val opts = viz.vega.facades.EmbedOptions()
//     val parsed = JSON.parse(spec)
//     viz.vega.facades.embed.embed(s"#$anId", parsed, opts)
//     ()
//   end show

//   // // when the class is instantiated, show the plot as a side effect...
//   plotTarget match
//     case _: LowPriorityPlotTarget => ()
//     case h: html.Div              => show(h)
//   end match
// end PlatformShow
