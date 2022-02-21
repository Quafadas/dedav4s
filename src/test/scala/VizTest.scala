class MySuite extends munit.FunSuite {
  test("hello") {
    val obtained = 43
    val expected = 43
    assertEquals(obtained, expected)
  }
}