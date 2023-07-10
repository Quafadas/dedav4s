package jsdocs

import scala.scalajs.js.JSON
import viz.vega.facades.EmbedOptions

// We need to trick mdoc, that we actually referecnes these libraries, and so it is important that this method remains in place..

//  I think ...

object Main {
  def main(args: Array[String]): Unit = {
    val opts = viz.vega.facades.EmbedOptions
    val parsed = JSON.parse("")
    viz.vega.facades.VegaEmbed.embed(s"#", parsed, opts)
    ()/*  */
  }
}