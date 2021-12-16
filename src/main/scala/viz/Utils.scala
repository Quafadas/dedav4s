package viz

object Utils:
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
      if spec.obj.keys.toSeq.contains("signals") then
        spec("signals").arr.dropWhileInPlace(signal =>
          signal("name").str.toLowerCase() == "width"
        )
        spec("signals").arr.dropWhileInPlace(signal =>
          signal("name").str.toLowerCase() == "height"
        )
        else
          spec("signals") = ujson.Arr()
      spec("autosize") =
        ujson.Obj("type" -> "fit", "resize" -> true, "contains" -> "padding")

      spec("signals").arr.append(signalH).append(signalW)
