/*
 * Copyright 2023 quafadas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viz
import org.scalajs.dom

import com.raquo.laminar.api.L.{*, given}
import scala.scalajs.js.annotation.JSExportTopLevel
import com.microsoft.playwright.*;

object Main {

  @JSExportTopLevel("main")
  def main(): Unit = {
    renderOnDomContentLoaded(dom.document.querySelector("#app"), app())
  }

  def app(): Div =
    div(
      idAttr("subDiv"),
      "Hello World"
    )
}

class MySuite extends munit.FunSuite:

  test("we can do something with Playwright") {
    val playwright = Playwright.create()
    val browser = playwright.chromium().launch();
    val page = browser.newPage();
    page.navigate("http://playwright.dev");

    // // Expect a title "to contain" a substring.
    // assert(page.hasTitle(Pattern.compile("Playwright")));

    // // create a locator
    // val getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));

    // // Expect an attribute "to be strictly equal" to the value.
    // assert(getStarted.hasAttribute("href", "/docs/intro"));
  }

  // test("Adding a chart works") {
  //   val app = dom.document.createElement("div")
  //   app.id = "app"
  //   dom.document.body.appendChild(app)

  //   val h1s = dom.document.getElementsByTagName("h1")
  //   assert(h1s.isEmpty)

  //   Page.onDOMContentLoaded(handler)

  //   val subDiv = dom.document.getElementById("subDiv")
  //   assertEquals(subDiv.textContent, "Hello World")
  // }

end MySuite
