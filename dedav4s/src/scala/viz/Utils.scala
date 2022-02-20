package viz

import ujson.Value

object Utils:

  val removeXAxis = new ((Value => Unit)):
    override def toString = "remove X axis"
    def apply(spec: Value) = spec("axes").arr.dropWhileInPlace(ax => ax("orient").str == "bottom")

  val removeYAxis = new ((Value => Unit)):
    override def toString = "remove Y axis"
    def apply(spec: Value) = spec("axes").arr.dropWhileInPlace(ax => ax("orient").str == "left")

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
              """)

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
          
        """)
      if spec("$schema").str.contains("lite") then
        spec("width") = "container"
        spec("height") = "container"
      else
        if spec.obj.keys.toSeq.contains("signals") then
          spec("signals").arr.dropWhileInPlace(signal => signal("name").str.toLowerCase() == "width")
          spec("signals").arr.dropWhileInPlace(signal => signal("name").str.toLowerCase() == "height")
        else spec("signals") = ujson.Arr()
        spec("autosize") = ujson.Obj("type" -> "fit", "resize" -> true, "contains" -> "padding")
        spec("signals").arr.append(signalH).append(signalW)

  val fixDefaultDataUrl: ujson.Value => Unit = new Function1[ujson.Value, Unit]:
    override def toString = "Fix default data url"
    def apply(spec: ujson.Value) =
      val test = spec("data")
      test.arrOpt match
        case Some(arrayd) =>
          spec("data")(0)("url") =
            "https://raw.githubusercontent.com/vega/vega/master/docs/" + spec("data")(0)("url").str
        case None => ()
      test.objOpt match
        case Some(objd) =>
          spec("data")("url") = "https://raw.githubusercontent.com/vega/vega/master/docs/" + spec("data")("url").str
        case None => ()
