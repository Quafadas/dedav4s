package viz.notebook

import java.io.OutputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

import scala.util.Try
import scala.util.Using

import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Engine
import org.graalvm.polyglot.Source
import org.graalvm.polyglot.Value
import org.graalvm.polyglot.proxy.ProxyArray
import org.graalvm.polyglot.proxy.ProxyExecutable

/** Renders charts to SVG inside the JVM, by running the charting libraries' own browser bundles in GraalJS.
  *
  * The JS context is created on first use (about a second, it loads vega, vega-lite and echarts) and then reused. JS is
  * single threaded, so every call is serialised on this object.
  *
  * Failures are returned as `Left(reason)` rather than thrown: a static render is a fallback, it should never break the
  * plot it accompanies.
  */
object StaticRenderer:

  private val resourceRoot = "dedav4s-notebook"

  // Order matters: shims before the bundles (vega captures `fetch` on load), vega before vega-lite.
  private val scripts =
    Seq("shims.js", "js/vega.min.js", "js/vega-lite.min.js", "js/echarts.min.js", "js/lz-string.min.js", "render.js")

  private lazy val http = HttpClient
    .newBuilder()
    .connectTimeout(Duration.ofSeconds(10))
    .followRedirects(HttpClient.Redirect.NORMAL)
    .build()

  /** Backs `fetch` in the JS context, so that specs loading data from a URL render. Returns `[status, body]`. */
  private val httpGet: ProxyExecutable = new ProxyExecutable:
    override def execute(args: Value*): AnyRef =
      val request = HttpRequest.newBuilder(URI.create(args(0).asString)).timeout(Duration.ofSeconds(30)).GET().build()
      val response = http.send(request, HttpResponse.BodyHandlers.ofString())
      ProxyArray.fromArray(Int.box(response.statusCode()), response.body())
    end execute

  private lazy val context: Either[String, Context] =
    Try {
      // The bundles log to the console; in a notebook that output would land in the cell.
      val engine = Engine
        .newBuilder("js")
        .option("engine.WarnInterpreterOnly", "false")
        .out(OutputStream.nullOutputStream())
        .err(OutputStream.nullOutputStream())
        .build()
      val ctx = Context.newBuilder("js").engine(engine).build()
      ctx.getBindings("js").putMember("__dedavHttpGet", httpGet)
      for name <- scripts do
        val path = s"$resourceRoot/$name"
        val stream = Option(getClass.getClassLoader.getResourceAsStream(path))
          .getOrElse(throw new IllegalStateException(s"missing resource $path"))
        val code = Using.resource(stream)(s => String(s.readAllBytes(), "UTF-8"))
        ctx.eval(Source.newBuilder("js", code, name).build())
      end for
      ctx
    }.toEither.left.map(e => s"could not start the JS engine: ${describe(e)}")

  private def describe(e: Throwable): String = Option(e.getMessage).getOrElse(e.getClass.getName)

  private def call(fn: String, args: Any*): Either[String, Value] =
    synchronized {
      context.flatMap { ctx =>
        Try(
          ctx.getBindings("js").getMember("__dedav").getMember(fn).execute(args.map(_.asInstanceOf[AnyRef])*)
        ).toEither.left
          .map(describe)
      }
    }

  private def member(v: Value, key: String): Option[String] =
    Option(v.getMember(key)).filterNot(_.isNull).map(_.asString)

  /** The JS side reports `{svg}` or `{error}`; neither means a promise never settled. */
  private def svgResult(result: Value): Either[String, String] =
    member(result, "svg") match
      case Some(svg) => Right(svg)
      case None      => Left(member(result, "error").getOrElse("the render did not complete"))

  /** Vega or Vega-Lite (decided by [[isVegaLite]]) to SVG. `width` and `height` apply to container-sized specs. */
  def vegaSvg(spec: ujson.Value, width: Int, height: Int): Either[String, String] =
    call("vega", spec.render(), isVegaLite(spec), width, height).flatMap(svgResult)

  def echartsSvg(option: ujson.Value, width: Int, height: Int): Either[String, String] =
    call("echarts", option.render(), width, height).flatMap(svgResult)

  /** lz-string's `compressToEncodedURIComponent`, the encoding the Vega editor reads from its URL. */
  def compressForUrl(text: String): Either[String, String] =
    call("compressForUrl", text).map(_.asString)

  /** By `$schema` when present, otherwise by shape: Vega-Lite has a `mark` or a composition, Vega has `marks`. */
  def isVegaLite(spec: ujson.Value): Boolean =
    spec.objOpt.exists { obj =>
      obj.get("$schema").flatMap(_.strOpt) match
        case Some(schema) => schema.contains("vega-lite")
        case None         =>
          !obj.contains("marks") &&
          Seq("mark", "layer", "concat", "hconcat", "vconcat", "facet", "repeat").exists(obj.contains)
    }
end StaticRenderer
