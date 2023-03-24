package jsdocs

import scala.scalajs.js.JSON
import viz.vega.facades.EmbedOptions
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport


object Main {
  def main(args: Array[String]): Unit = {    
    println("called here")  
    val opts = viz.vega.facades.EmbedOptions
    val parsed = JSON.parse("")
    viz.vega.facades.VegaEmbed.embed(s"#", parsed, opts)
    require("vega-embed", x => js.Object )
    ()/*  */
  }
}


@js.native
@JSImport("require", JSImport.Namespace)
object require extends js.Object {  
  def apply(n: String, f : Function1[js.Any, js.Any]): String = js.native
}