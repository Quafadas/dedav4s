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

import viz.PlotTarget
import org.scalajs.dom.html
import scala.scalajs.js.JSON
import viz.embed.vegaEmbed.mod.default as embedV
import viz.embed.vegaTypings.specMod.Spec as embedTypings


enum BundleStrategy:
  case BrowserDirect
  case Bundler
  case MDoc

//case class PlotTarget(in:html.Div, bundleStrategy: BundlerStrategy.Bund)
type PlotTarget = html.Div | Tuple2[html.Div, BundleStrategy]

trait PlatformShow(implicit plotTarget: PlotTarget) extends Spec:
  def show[A](inDiv: A): Unit =
    inDiv match
      case (inDiv: html.Div, b: BundleStrategy) =>
        b match
          case BundleStrategy.BrowserDirect =>
            val typedDiv = inDiv.asInstanceOf[html.Div]
            val anId = typedDiv.id
            val newId = if anId.isEmpty then
              val temp = java.util.UUID.randomUUID()
              typedDiv.setAttribute("id", temp.toString())
            else anId
            val opts = viz.embed.vegaEmbed.mod.EmbedOptions[String, viz.embed.vegaTypings.rendererMod.Renderers]()
            opts.setActions(true)
            opts.setHover(true)
            val parsed = JSON.parse(spec).asInstanceOf[viz.embed.vegaTypings.specMod.Spec]
            val aPromise = embedV(s"#$anId", parsed, opts)

          case BundleStrategy.MDoc => show(inDiv) // this is the default, as it is assumed what most people will want
          case BundleStrategy.Bundler =>
            println("bundler")
            val typedDiv = inDiv.asInstanceOf[html.Div]
            val anId = typedDiv.id
            val newId = if anId.isEmpty then
              val temp = java.util.UUID.randomUUID()
              typedDiv.setAttribute("id", temp.toString())
            else anId
            val opts = viz.embed.vegaEmbed.mod.EmbedOptions[String, viz.embed.vegaTypings.rendererMod.Renderers]()
            opts.setActions(true)
            opts.setHover(true)
            val parsed = JSON.parse(spec).asInstanceOf[viz.embed.vegaTypings.specMod.Spec]
            val aPromise = embedV(s"#$anId", parsed, opts)

      case scalaJsDomDiv: html.Div =>
        val typedDiv = inDiv.asInstanceOf[html.Div]
        val anId = typedDiv.id
        val newId = if anId.isEmpty then
          val temp = java.util.UUID.randomUUID()
          typedDiv.setAttribute("id", temp.toString())
        else anId
        val opts = viz.embed.vegaEmbed.mod.EmbedOptions[String, viz.embed.vegaTypings.rendererMod.Renderers]()
        opts.setActions(true)
        opts.setHover(true)
        //val parsed = JSON.parse(spec).asInstanceOf[viz.embed.vegaEmbed.VisualizationSpec]
        val parsed = JSON.parse(spec).asInstanceOf[viz.embed.vegaTypings.specMod.Spec]
        val aPromise = embedV(s"#$anId", parsed, opts)

  val doShow = show(plotTarget)
