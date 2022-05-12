package jsdocs

import scala.scalajs.js.JSON
import viz.vega.facades.EmbedOptions

object Main {
  def main(args: Array[String]): Unit = {      
    val opts = viz.vega.facades.EmbedOptions
    val parsed = JSON.parse("")
    viz.vega.facades.VegaEmbed.embed(s"#", parsed, opts)
    ()/*  */
  }
}