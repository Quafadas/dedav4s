package viz

object Utils:
  val fillDiv : ujson.Value => Unit =
    (spec: ujson.Value) => {
      spec.obj.remove("height")
      spec.obj.remove("width")
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
      spec("signals").arr.dropWhileInPlace(signal =>
        signal("name").str.toLowerCase() == "width"
      )
      spec("signals").arr.dropWhileInPlace(signal =>
        signal("name").str.toLowerCase() == "height"
      )
      println(spec("signals"))
      spec("signals").arr.append(signalH).append(signalW)
}