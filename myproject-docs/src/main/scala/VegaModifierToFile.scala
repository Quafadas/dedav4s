package viz.mdoc

import java.nio.file.Files
import java.nio.file.Paths
import mdoc.*
import scala.meta.inputs.Position
import scala.util.Random

class VegaModifierToFile extends mdoc.PostModifier:
  val name = "vegaspec"
  def process(ctx: PostModifierContext): String = 
    val relpath = Paths.get(ctx.info)
    val out = ctx.outputFile.toNIO.getParent.resolve(relpath)
    ctx.lastValue match
      case spec: viz.Spec =>
        Files.createDirectories(out.getParent)
        if (!Files.isRegularFile(out)) {
          os.write.over(os.Path(out.toAbsolutePath), spec.spec)
        }
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