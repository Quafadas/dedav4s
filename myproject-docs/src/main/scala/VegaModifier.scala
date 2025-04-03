/*
 * Copyright 2022 quafadas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viz.mdoc

import mdoc.*
import scala.meta.inputs.Position
import scala.util.Random
import scala.annotation.experimental

@experimental
class VegaModifier extends mdoc.PostModifier:
  val name = "vegaplot"
  def process(ctx: PostModifierContext): String =
    ctx.lastValue match
      case spec: viz.Spec =>
        val anId = Random.alphanumeric.take(8).mkString("")
        vegaEmbed1(spec.spec, anId)
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
end VegaModifier

def vegaEmbed1(inSpec: String, vizId: String) = s"""

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
