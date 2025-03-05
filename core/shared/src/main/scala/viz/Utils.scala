/*
 * Copyright 2023 quafadas
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

import ujson.Value

object Utils:

  val removeXAxis = new ((Value => Unit)):
    override def toString = "remove X axis"
    def apply(spec: Value) =
      val tmp = spec("axes").arr.filterNot(ax => ax("orient").str == "bottom")
      spec("axes") = tmp
    end apply

  val removeYAxis = new ((Value => Unit)):
    override def toString = "remove Y axis"
    def apply(spec: Value) =
      val tmp = spec("axes").arr.filterNot(ax => ax("orient").str == "left")
      spec("axes") = tmp
    end apply

  val resize: ujson.Value => Unit = (spec: ujson.Value) =>
    spec("autosize") = ujson.Obj("resize" -> true, "contains" -> "padding", "type" -> "fit")

  val fillDiv: ujson.Value => Unit =
    (spec: ujson.Value) =>
      spec.obj.remove("height")
      spec.obj.remove("width")
      spec.obj.remove("autosize")
      val signalW = ujson.read("""
              {
                "name": "width",
                "init": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
                "on": [
                  {
                    "update": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
                    "events": "window:resize"
                  }
                ]
              }
              """.trim)

      val signalH = ujson.read("""
          {
                "name": "height",
                "init": "isFinite(containerSize()[1]) ? containerSize()[1] : 200",
                "on": [
                  {
                    "update": "isFinite(containerSize()[1]) ? containerSize()[1] : 200",
                    "events": "window:resize"
                  }
                ]
          }

        """.trim)
      val _ = if spec("$schema").str.contains("lite") then
        spec("width") = "container"
        spec("height") = "container"
      else
        if spec.obj.keys.toSeq.contains("signals") then
          val tmp = spec("signals").arr.filterNot(sig => sig("name").str == "height" || sig("name").str == "width")
          spec("signals") = tmp
        else spec("signals") = ujson.Arr()
        end if
        spec("autosize") = ujson.Obj("type" -> "fit", "resize" -> true, "contains" -> "padding")
        spec("signals").arr.append(signalH).append(signalW)
      ()
  end fillDiv

  val fixDefaultDataUrl: ujson.Value => Unit = new Function1[ujson.Value, Unit]:
    override def toString = "Fix default data url"
    def apply(spec: ujson.Value) =
      val test = spec("data")
      test.arrOpt match
        case Some(arrayd) =>
          spec("data")(0)("url") =
            "https://raw.githubusercontent.com/vega/vega/master/docs/" + spec("data")(0)("url").str
        case None => ()
      end match
      test.objOpt match
        case Some(objd) =>
          spec("data")("url") = "https://raw.githubusercontent.com/vega/vega/master/docs/" + spec("data")("url").str
        case None => ()
      end match
    end apply
end Utils
