package viz

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import scala.quoted.*

/** Compile time resolution of file paths anchored to something more stable than the compiler's working directory.
  *
  * `VegaPlot` reads a spec during compilation to infer its typed accessors. Anchoring the path to the source file, or
  * to the project root discovered above it, makes that reproducible no matter where the build was invoked from.
  *
  * Some front ends give the macro nothing useful to anchor against. A notebook cell has no source file on disk, and
  * under a build server the compiler runs inside a long lived daemon whose working directory is a cache directory
  * unrelated to anyone's code. For those, [[anchorKey]] lets the host declare the anchor outright, which takes priority
  * over everything this object would otherwise infer.
  */
private[viz] object SourceAnchor:

  /** Name of the anchor, accepted both as a `-Xmacro-settings` entry and as a system property.
    *
    * Two channels because the hosts that need them cannot both reach the same one. A build server compiles in a daemon
    * that a `-D` on the build never touches, so it needs `-Xmacro-settings:dedav4s.root=...`, which travels with the
    * compile request. A notebook kernel compiles cells in its own JVM, so a cell can call
    * `System.setProperty("dedav4s.root", ...)` and have every later cell see it.
    */
  val anchorKey = "dedav4s.root"

  /** Where an anchor directory came from, so a failure can say which channel produced the directory it searched. */
  enum Provenance(val describe: String):
    case MacroSetting extends Provenance(s"the '$anchorKey' compiler setting")
    case SystemProperty extends Provenance(s"the '$anchorKey' system property")
    case CallSite extends Provenance("the source file that called it")
    case WorkingDir extends Provenance("the compiler's working directory")
  end Provenance

  /** A path resolved at compile time, together with the project root it was anchored against. */
  final case class Anchored(absolutePath: Path, projectRoot: Path, provenance: Provenance)

  /** Files that mark the top of a project. */
  val rootMarkers: Set[String] = Set(
    "build.sbt",
    "project.scala",
    "build.sc",
    "build.mill",
    "build.mill.scala",
    ".scala-build",
    ".git",
    "pom.xml",
    "build.gradle"
  )

  /** Path fragments that identify a compile server's own cache rather than a project. A working directory under one of
    * these belongs to a daemon that was started long before the code being compiled existed, so it is never a
    * meaningful anchor.
    */
  private val buildServerDirs = Seq("ScalaCli/bloop", "ScalaCli\\bloop", ".bloop", ".bsp", ".metals")

  private def ancestors(path: Path): LazyList[Path] =
    LazyList
      .iterate(Option(path))(_.flatMap(p => Option(p.getParent)))
      .takeWhile(_.isDefined)
      .map(_.get)

  /** Reads `-Xmacro-settings` off the `Quotes` instance reflectively.
    *
    * `CompilationInfo.XmacroSettings` is `@experimental`, and calling it directly would force every enclosing
    * definition to be `@experimental` too - all the way out through `VegaPlot.relativeToSource` to user code, which
    * would then need `-experimental` to compile. Reaching it reflectively gets the same list with no annotation to
    * propagate.
    */
  private def macroSettings(using q: Quotes): List[String] =
    try
      val reflectModule = q.getClass.getMethod("reflect").invoke(q)
      val compilationInfo = reflectModule.getClass.getMethod("CompilationInfo").invoke(reflectModule)
      compilationInfo.getClass.getMethod("XmacroSettings").invoke(compilationInfo).asInstanceOf[List[String]]
    catch case _: Throwable => Nil
  end macroSettings

  /** Value of `anchorKey` in `-Xmacro-settings`, which arrives as `key=value` entries. */
  private def anchorFromMacroSettings(using Quotes): Option[String] =
    macroSettings
      .collectFirst {
        case setting if setting.startsWith(s"$anchorKey=") => setting.drop(anchorKey.length + 1)
      }
      .filter(_.nonEmpty)

  /** Value of `anchorKey` as a system property. */
  private def anchorFromSystemProperty: Option[String] =
    Option(System.getProperty(anchorKey)).map(_.trim).filter(_.nonEmpty)

  /** Directory holding the source file that expanded this macro, if that call site has a file on disk.
    *
    * Notebook and REPL front ends (almond, ammonite, the scala REPL) compile cells from memory, so there is no source
    * path to anchor to and this is `None`.
    */
  def callSiteDir(using Quotes): Option[Path] =
    import quotes.reflect.*
    Position.ofMacroExpansion.sourceFile.getJPath.map { jPath =>
      val abs = jPath.toAbsolutePath.normalize
      val sourceDir = Option(abs.getParent).getOrElse(abs)
      ancestors(sourceDir)
        .find(p => Option(p.getFileName).exists(_.toString == ".scala-build"))
        .flatMap(p => Option(p.getParent))
        .getOrElse(sourceDir)
    }
  end callSiteDir

  /** Working directory of the compiler.
    *
    * Only meaningful when the compiler runs in the process the build started. Behind a build server it is the daemon's
    * own cache directory, which is why [[anchorDir]] treats it as a last resort and warns when it lands here.
    */
  def workingDir: Path = Paths.get("").toAbsolutePath.normalize

  /** First ancestor of `from` containing one of [[rootMarkers]], or `from` itself if there is none. */
  def projectRootFrom(from: Path): Path =
    ancestors(from)
      .find(d => rootMarkers.exists(marker => Files.exists(d.resolve(marker))))
      .getOrElse(from)
  end projectRootFrom

  /** Paths given to the anchored constructors are always relative to their anchor, so a leading separator is a slip
    * rather than a request for the filesystem root. Drop it, otherwise `resolve` discards the anchor and looks for the
    * file at `/`.
    */
  def anchorRelative(path: String): String = path.dropWhile(c => c == '/' || c == '\\')

  /** The directory anchored paths resolve against.
    *
    * A declared anchor wins over an inferred one. That ordering is the point: a tool that compiles a notebook cell by
    * writing it to a scratch file has a call site on disk, but at a location with no relationship to the notebook the
    * reader is looking at, so the declared value has to be able to override it.
    */
  def anchorDir(using Quotes): (Path, Provenance) =
    val declared = anchorFromMacroSettings
      .map(_ -> Provenance.MacroSetting)
      .orElse(anchorFromSystemProperty.map(_ -> Provenance.SystemProperty))
    declared match
      case Some((dir, provenance)) => Paths.get(dir).toAbsolutePath.normalize -> provenance
      case None                    =>
        callSiteDir match
          case Some(sourceDir) => sourceDir -> Provenance.CallSite
          case None            => workingDir -> Provenance.WorkingDir
    end match
  end anchorDir

  /** Says how to declare an anchor, for the case where the one in use is not the one the caller wanted. */
  def anchorAdvice: String =
    s"The anchor can be set with -Xmacro-settings:$anchorKey=/path/to/dir, which travels with the compile request and " +
      s"so reaches a build server daemon, or with System.setProperty(\"$anchorKey\", \"/path/to/dir\") from an earlier " +
      "cell in a notebook kernel, which compiles in its own JVM. Alternatively use absolutePath or fromResource, " +
      "which do not anchor."

  /** Warns when a resolution fell back to [[workingDir]], which is only coincidentally related to the code being
    * compiled - and under a build server is not related to it at all.
    */
  private def warnWorkingDir(anchor: Path)(using Quotes): Unit =
    import quotes.reflect.*
    val daemon = buildServerDirs.exists(anchor.toString.contains)
    val diagnosis =
      if daemon then
        s"dedav4s: this call site has no source file on disk, so paths resolve against the compiler's working " +
          s"directory '$anchor'. That directory belongs to a build server daemon, not to your project, so this will " +
          "not find your spec."
      else
        s"dedav4s: this call site has no source file on disk, so paths resolve against the compiler's working " +
          s"directory '$anchor', which may not be where your spec lives."
    report.warning(s"$diagnosis $anchorAdvice", Position.ofMacroExpansion)
  end warnWorkingDir

  /** Resolves `path` against [[anchorDir]]. */
  def relativeToSource(path: String)(using Quotes): Anchored =
    val (anchor, provenance) = anchorDir
    if provenance == Provenance.WorkingDir then warnWorkingDir(anchor)
    end if
    Anchored(anchor.resolve(anchorRelative(path)).toAbsolutePath.normalize, projectRootFrom(anchor), provenance)
  end relativeToSource

  /** Resolves `path` against the project root above [[anchorDir]].
    *
    * A declared anchor is taken as the root as given. Walking up from it to find a build file would ignore what the
    * caller just said the root was.
    */
  def projectRoot(path: String)(using Quotes): Anchored =
    val (anchor, provenance) = anchorDir
    if provenance == Provenance.WorkingDir then warnWorkingDir(anchor)
    end if
    val root = provenance match
      case Provenance.MacroSetting | Provenance.SystemProperty => anchor
      case Provenance.CallSite | Provenance.WorkingDir         => projectRootFrom(anchor)
    Anchored(root.resolve(anchorRelative(path)).toAbsolutePath.normalize, root, provenance)
  end projectRoot

end SourceAnchor
