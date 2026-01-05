package viz

import ujson.Value

extension (plottable: ujson.Value)
  def mod(mods: List[ujson.Value => Unit]): ujson.Value =
    val temp = plottable
    for m <- mods do m(temp)
    end for
    temp
  end mod
end extension

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
