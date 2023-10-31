# Gotcha

When working with ujson, it is inevitable, that some string is going to get formatted as below.

```scala mdoc:crash
ujson.read("""
              {
                "name": "width",
                "init": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
                "on": [
                  {
                    "update": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
                    "events": "window:resize"
                  }
                ]
              }"""
)
"""
```
Such problems are infuriatingly hard to debug. The simple solution, is to trim all strings in advance of feeding them to `ujson`.

```scala mdoc:crash
ujson.read("""
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
              """.trim
)
```
