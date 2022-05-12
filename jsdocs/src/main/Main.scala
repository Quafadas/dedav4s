package jsdocs

object Main {
  def main(args: Array[String]): Unit = {      
    val anId = inDiv.id
    val newId = if anId.isEmpty then
      val temp = java.util.UUID.randomUUID()
      inDiv.setAttribute("id", temp.toString())
    else anId

    val opts = viz.vega.facades.EmbedOptions
    val parsed = JSON.parse(spec)
    viz.vega.facades.VegaEmbed.embed(s"#$anId", parsed, opts)
    ()/*  */
  }
}