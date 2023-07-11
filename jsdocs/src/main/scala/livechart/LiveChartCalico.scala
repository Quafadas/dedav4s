package livechart

import scala.scalajs.js
import scala.scalajs.js.annotation.*
//import scala.util.Random

import calico.*
import calico.html.io.{*, given}
import calico.unsafe.given
import calico.syntax.*
import cats.effect.*
import cats.effect.std.Random
import fs2.*
import fs2.concurrent.*
import fs2.dom.*

object MyCalicoApp extends IOWebApp:
  def render: Resource[IO, HtmlElement[IO]] =
    calicoChart
end MyCalicoApp

val dataSignal = SignallingRef[IO]
  .of(List(2.4, 3.4, 5.1, -2.3))

def calicoChart: Resource[IO, HtmlDivElement[IO]] =
  SignallingRef[IO]
    .of(List(2.4, 3.4, 5.1, -2.3))
    .product(Channel.unbounded[IO, Int])
    .toResource
    .flatMap { (data, diff) =>
      div(
        p("We want to make it as easy as possible, to build a chart"),
        span("Here's a random data set: "),
        data.map(in => p(in.mkString("[", ",", "]"))),
        button(
          "Add a random number",
          onClick --> (
            _.evalMap(_ =>
              //IO.println("clicked") >>
                Random.scalaUtilRandom[IO].toResource.use{r => r.nextDouble.map(_* 5)}
            ).foreach(newD =>
              val d = data.get
              IO.println(newD) >>
                d.map(_ :+ newD).map(data.set).void
            )
          )
        ),
        p("")
        // child <-- data.signal.map { data =>
        //   val barChart: BarChart = data.plotBarChart(List(viz.Utils.fillDiv))
        //   LaminarViz.simpleEmbed(barChart)
        // }
      )
    }

def Counter(label: String, initialStep: Int): Resource[IO, HtmlDivElement[IO]] =
  SignallingRef[IO].of(initialStep).product(Channel.unbounded[IO, Int]).toResource.flatMap { (step, diff) =>

    val allowedSteps = List(1, 2, 3, 5, 10)

    div(
      p(
        "Step: ",
        select.withSelf { self =>
          (
            allowedSteps.map(step => option(value := step.toString, step.toString)),
            value <-- step.map(_.toString),
            onChange --> {
              _.evalMap(_ => self.value.get).map(_.toIntOption).unNone.foreach(step.set)
            }
          )
        }
      ),
      p(
        label + ": ",
        b(diff.stream.scanMonoid.map(_.toString).holdOptionResource),
        " ",
        button(
          "-",
          onClick --> {
            _.evalMap(_ => step.get).map(-1 * _).foreach(diff.send(_).void)
          }
        ),
        button(
          "+",
          onClick --> (_.evalMap(_ => step.get).foreach(diff.send(_).void))
        )
      )
    )
  }

val app: Resource[IO, HtmlDivElement[IO]] = div(
  h1("Let's count!"),
  Counter("Sheep", initialStep = 3)
)
