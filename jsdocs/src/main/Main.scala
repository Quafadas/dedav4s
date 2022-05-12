package jsdocs

object Main {
  def main(args: Array[String]): Unit = {      
    val opts = viz.vega.facades.EmbedOptions
    val parsed = JSON.parse(spec)
    viz.vega.facades.VegaEmbed.embed(s"#", parsed, opts)
    ()/*  */
  }
}