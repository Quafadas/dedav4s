package viz

import viz.Macros.Implicits.given_Writer_T

class NTTest extends munit.FunSuite:
  test("That we can serilaise a NamedTuple") {

    val spec = (
      `$schema` = "https://vega.github.io/schema/vega-lite/v6.json",
      width = "container",
      height = 250,
      data = (url = "https://raw.githubusercontent.com/vega/vega/refs/heads/main/docs/data/cars.json"),
      mark = "bar",
      encoding = (
        x = (`field` = "Origin"),
        y = (
          aggregate = "count",
          title = "Number of Cars"
        )
      )
    )

    // If this compiles we're doing well.
    upickle.default.writeJs(spec)

  }
end NTTest
