package viz.mdoc

import java.nio.file.Files
import java.nio.file.Paths
import mdoc.*
import scala.meta.inputs.Position
import scala.util.Random

class VegaModifier extends mdoc.PostModifier:
  val name = "vegaplot"
  def process(ctx: PostModifierContext): String =    
    ctx.lastValue match
      case spec: viz.Spec =>
        val anId = Random.alphanumeric.take(8).mkString("")
        vegaEmbed(spec.spec, anId)
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

def vegaEmbed(inSpec : String, vizId:String ) = s"""

<div id="viz_$vizId" class="viz"></div>

<script type="text/javascript">
const spec$vizId = $inSpec
vegaEmbed('#viz_$vizId', spec$vizId , {
    renderer: "canvas", // renderer (canvas or svg)
    container: "#viz_$vizId", // parent DOM container
    hover: true, // enable hover processing
    actions: {
        editor : true
    }
}).then(function(result) {

})
</script>
"""