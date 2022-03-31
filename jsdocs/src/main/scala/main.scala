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

package jsdocs

//import viz.embed.vegaEmbed.mod.^
import viz.docs.vegaEmbed.mod.^

object Main:
  def main(args: Array[String]): Unit =

    val opts = viz.docs.vegaEmbed.mod.EmbedOptions[String, viz.docs.vegaTypings.rendererMod.Renderers]()
    opts.setActions(true)
    opts.setHover(true)
    val aPromise = viz.docs.vegaEmbed.mod.default(s"#bah", s"json", opts)
