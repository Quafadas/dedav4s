package viz

import munit.FunSuite

class ApplyModsTest extends FunSuite:

  val widen = (spec: ujson.Value) => spec("width") = 800
  val addData = (spec: ujson.Value) => spec("data")(0)("values") = ujson.Arr(1, 2, 3)

  def spec = ujson.Obj("width" -> 400, "data" -> ujson.Arr(ujson.Obj("name" -> "table")))

  test("applyMods returns a modified copy and leaves the original unchanged") {
    val original = spec
    val modified = original.applyMods(List(widen, addData))

    assertEquals(modified("width").num, 800.0)
    assertEquals(modified("data")(0)("values"), ujson.Arr(1, 2, 3))
    assertEquals(original, spec)
  }

  test("mod returns a modified copy and leaves the original unchanged") {
    val original = spec
    val modified = original.mod(List(widen, addData))

    assertEquals(modified("width").num, 800.0)
    assertEquals(modified("data")(0)("values"), ujson.Arr(1, 2, 3))
    assertEquals(original, spec)
  }

  test("two specs built from one shared spec are independent") {
    val shared = spec
    val a = shared.applyMods(List(s => s("data")(0)("values") = ujson.Arr(1)))
    val b = shared.applyMods(List(s => s("data")(0)("values") = ujson.Arr(2)))

    assertEquals(a("data")(0)("values"), ujson.Arr(1))
    assertEquals(b("data")(0)("values"), ujson.Arr(2))
  }
end ApplyModsTest
