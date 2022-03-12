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

package viz

import org.scalajs.dom.html
import typings.estree.estreeBooleans.`true`


trait PlotTarget:
  def show(spec: String, parent: html.Div): Unit

object PlotTargets:
  lazy val conf = org.ekrich.config.ConfigFactory.load()

  given doNothing: PlotTarget with
    override def show(spec: String, parent: html.Div): Unit = ()

  given formatSpec: PlotTarget with
    override def show(spec: String, parent: html.Div): Unit = parent.innerHTML = spec

  given printlnTarget: PlotTarget with
    override def show(spec: String, parent: html.Div): Unit = println(spec)

  given div: PlotTarget with
    override def show(spec: String, parent: html.Div): Unit =
      val anId = parent.getAttribute("id")
      scalajs.js.eval(s"""
            vegaEmbed('#$anId', $spec, {
                renderer: "canvas", // renderer (canvas or svg)
                container: "#$anId", // parent DOM container
                hover: true, // enable hover processing
                actions: {
                    editor : true
                }
            }).then(function(result) {
              console.log(result)
            })""")
  given typedShow: PlotTarget with
    override def show(spec: String, parent: html.Div) = 
      val anId = parent.getAttribute("id")
      val opts = typings.vegaEmbed.mod.EmbedOptions[String, typings.vegaTypings.rendererMod.Renderers ]()
      opts.setActions(true)
      opts.setHover(true)
      val aPromise = typings.vegaEmbed.mod.default(s"#$anId", spec, opts)
      
