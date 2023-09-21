package livechart

import scala.scalajs.js
import scala.scalajs.js.annotation.*
//import scala.util.Random

import viz.extensions.*
import viz.vega.plots.{BarChart, given}
import calico.*
import calico.html.io.{*, given}
import calico.syntax.*
import cats.effect.*
import cats.effect.std.Random
import fs2.*
import fs2.concurrent.*
import fs2.dom.*
import viz.vega.facades.EmbedOptions

object MyCalicoApp extends IOWebApp:
  def render: Resource[IO, HtmlElement[IO]] = calicoChart

end MyCalicoApp

def calicoChart: Resource[IO, HtmlElement[IO]] =
  SignallingRef[IO]
    .of(List(2.4, 3.4, 5.1, -2.3))
    .product(Channel.unbounded[IO, Int])
    .toResource
    .flatMap { (data: SignallingRef[cats.effect.IO, List[Double]], diff) =>
      div(
        p("We want to make it as easy as possible, to build a chart"),
        span("Here's a random data set: "),
        data.map(in => p(in.mkString("[", ",", "]"))),
        button(
          "Add a random number",
          onClick --> (
            _.evalMap(_ => Random.scalaUtilRandom[IO].toResource.use(r => r.nextDouble.map(_ * 5))).foreach(newD =>
              val d = data.get
              IO.println(newD) >>
                data.update(_ :+ newD).void
            )
          )
        ),
        p(""),
        data.map { data =>
          val barChart: BarChart = data.plotBarChart(
            List(
              viz.Utils.fillDiv,
              viz.Utils.removeYAxis
            )
          )
          val chartDiv = div("")
          chartDiv.flatMap { d =>
            // To my astonishment, this doesn't work...
            /* val dCheat = d.asInstanceOf[org.scalajs.dom.html.Div]
            dCheat.style.height = "40vmin"
            dCheat.style.width = "40vmin" */
            // end yuck

            // I had to set the div size down in here. Then it worked. But I have no idea why.
            viz.CalicoViz.viewEmbed(barChart, Some(chartDiv), Some(EmbedOptions)).map(_._1)
          }
        }
      )
    }
