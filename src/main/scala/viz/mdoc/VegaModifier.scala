package viz.mdoc

import java.nio.file.Files
import java.nio.file.Paths
import mdoc.*
import scala.meta.inputs.Position

class VegaModifier extends mdoc.PostModifier:
  val name = "vegaplot"
  def process(ctx: PostModifierContext): String =
    //val relpath = Paths.get(ctx.info)
    //val out = ctx.outputFile.toNIO.getParent.resolve(relpath)
    println("at least we got here")
    ctx.lastValue match
      case spec: viz.Spec =>
        vegaEmbed(spec.spec)
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

def vegaEmbed(inSpec : String ) = s"""
<script type="text/javascript">
const spec = $inSpec
vegaEmbed('#viz', spec, {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>

"""