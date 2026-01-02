package viz.macros

import munit.FunSuite
import io.circe.*
import io.circe.syntax.*
import io.circe.optics.JsonPath.*
import io.circe.literal.*
import io.circe.parser.decode

class NtCodec extends FunSuite:

  test("NamedTuple codec should encode and decode correctly") {

    val tupleInstance = (name = "Alice", age = 25, isStudent = true)

    import viz.NtCirce.given

    val json = tupleInstance.asJson.noSpaces
    assertEquals(json, """{"name":"Alice","age":25,"isStudent":true}""")

    val decoded = decode[(name: String, age: Int, isStudent: Boolean)](json)
    assertEquals(decoded, Right(tupleInstance))
  }

  test("Nested named tuples should encode and decode correctly") {

    val nestedInstance = (person = (name = "Bob", age = 30), address = (city = "New York", zip = "10001"))

    import viz.NtCirce.given

    val json = nestedInstance.asJson.noSpaces
    assertEquals(json, """{"person":{"name":"Bob","age":30},"address":{"city":"New York","zip":"10001"}}""")

    val decoded = decode[(person: (name: String, age: Int), address: (city: String, zip: String))](json)
    assertEquals(decoded, Right(nestedInstance))
  }
end NtCodec
