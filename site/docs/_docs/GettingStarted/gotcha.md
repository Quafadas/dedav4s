# Gotcha

When working with ujson, it is inevitable, that some string is going to get formatted with line breaks on certain platforms, something like this. ujson mnay crash with a cryptic warning

```scala
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

              """
)
```
Such problems are infuriatingly hard to debug. One hint is to trim all strings in advance of feeding them to `ujson`.

```scala mdoc
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
