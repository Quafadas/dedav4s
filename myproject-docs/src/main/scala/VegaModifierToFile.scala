package viz.mdoc

import java.nio.file.Files
import java.nio.file.Paths
import mdoc.*
import scala.meta.inputs.Position
import scala.util.Random
//import os.RelPath

class VegaModifierToFile extends mdoc.PostModifier:
  val name = "vegaspec"
  def process(ctx: PostModifierContext): String = 
    val relname = (ctx.info)
    val out = os.Path(ctx.outputFile.toNIO.getParent).toString.toLowerCase
    //println(out)
    //println(relname)
    ctx.lastValue match
      case spec: viz.Spec =>                
        os.write.over( os.Path(out) / os.up /"assets" / s"$relname.json", spec.spec)
        ""
      case _ =>
        val (pos, obtained) = ctx.variables.lastOption match
          case Some(variable) =>
            val prettyObtained =
              s"${variable.staticType} = ${variable.runtimeValue}"
            (variable.pos, prettyObtained)
          case None =>
            (Position.Range(ctx.originalCode, 0, 0), "nothing")
        ctx.reporter.error(
          pos,
          s"""type mismatch:
  expected: viz.Spec
  obtained: $obtained"""
        )
        ""