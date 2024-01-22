package viz
// import viz.PlotTargets.websocket // for local testing
import viz.PlotTargets.websocket
import viz.websockets.WebsocketVizServer
import com.microsoft.playwright.*;
import scala.util.Try
import scala.util.Success
import scala.util.Failure
import java.nio.file.FileSystemAlreadyExistsException
import java.nio.file.Paths

class PlaywrightTest extends munit.FunSuite:

  var port: Int = _
  var pw: Playwright = _

  def makePw : Playwright = {
    Try(Playwright.create()) match
      case Success(p) => p
      case Failure(e) =>
        e match
            case _: FileSystemAlreadyExistsException =>
              println("Playwright already exists")
              Thread.sleep(5000)
              makePw
            case rm: RuntimeException if rm.getMessage() == "Failed to create driver" =>
              println("Runtime exception, failed to make driver")
              Thread.sleep(5000)
              makePw
  }

  override def beforeAll(): Unit =
    val env = new java.util.HashMap[String, String]();
    env.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
    val createOptions = new Playwright.CreateOptions();
    createOptions.setEnv(env);
    port = WebsocketVizServer.randomPort
    val a = Playwright.create(createOptions).chromium().launch(
      new BrowserType.LaunchOptions()
        .setExecutablePath(Paths.get("Path/to/browser.exe"))
        .setHeadless(false)
        .setChromiumSandbox(true));

        // context = browser.newContext(
        //   new Browser.NewContextOptions()
        // .setViewportSize(1800, 1080)
    // )
  end beforeAll

  test("Check the default library settings") {

    lazy val conf = org.ekrich.config.ConfigFactory.load()

    // by default, these are not set
    assertEquals(conf.hasPath("dedavOutPath"), false)
    assertEquals(conf.hasPath("gitpod_port"), false)

  }
end PlaywrightTest
