package viz

class DefaultsTest extends munit.FunSuite:
  test("Check the default library settings") {
    lazy val conf = org.ekrich.config.ConfigFactory.load()
    assertEquals(conf.hasPath("dedavOutPath"), false)
    assertEquals(conf.hasPath("gitpod_port"), false)
  }

end DefaultsTest
