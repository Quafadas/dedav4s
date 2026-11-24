package calicointegrationapp

import calico.*
import calico.html.io.{*, given}
import cats.effect.*
import fs2.concurrent.*
import fs2.dom.*
import viz.CalicoViz
import viz.applyMods
import viz.vega.facades.EmbedOptions
import viz.vega.plots.SpecUrl

object CalicoIntegrationApp extends IOWebApp:

  // Sized to its div, so the chart listens for window:resize.
  val spec = upickle.default.write(SpecUrl.BarChart.jsonSpec.applyMods(List(viz.Utils.fillDiv)))

  def render: Resource[IO, HtmlElement[IO]] =
    SignallingRef[IO].of(0).toResource.flatMap { renders =>
      div(
        h1("Calico Viz"),
        button("Re-render", onClick --> (_.foreach(_ => renders.update(_ + 1)))),
        // Each render embeds a new chart and releases the previous one, like a chart that follows changing data.
        renders.map { n =>
          CalicoViz.viewEmbed(spec, Some(div(idAttr := s"chart-$n")), Some(EmbedOptions())).map(_._1)
        }
      )
    }
end CalicoIntegrationApp
