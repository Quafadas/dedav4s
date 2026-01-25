package viz.vega

import io.circe.*
import io.circe.parser.parse
import viz.macros.SpecMod
import scala.util.Try

/** Tracks the source for staleness detection. */
sealed trait SourceInfo:
  def contentHash: Int
  def description: String
  def readCurrentContent(): Option[String]
end SourceInfo

/** Tracks a filesystem file for staleness detection.
  *
  * @param path
  *   Absolute path to the source file
  * @param contentHash
  *   Hash of file content at compile time
  */
case class FileSourceInfo(path: String, contentHash: Int) extends SourceInfo:
  def description: String = path
  def readCurrentContent(): Option[String] =
    Try(scala.io.Source.fromFile(path).mkString).toOption
end FileSourceInfo

/** Tracks a classpath resource for staleness detection.
  *
  * @param resourcePath
  *   Resource path (e.g., "mySpec.vl.json")
  * @param contentHash
  *   Hash of resource content at compile time
  */
case class ResourceSourceInfo(resourcePath: String, contentHash: Int) extends SourceInfo:
  def description: String = s"resource:$resourcePath"
  def readCurrentContent(): Option[String] =
    Try(scala.io.Source.fromResource(resourcePath).mkString).toOption
end ResourceSourceInfo

/** Vega spec wrapper to generate typed accessors inferred from the JSON structure.
  *
  * When created from a file via `VegaPlot.pwd` or resource via `VegaPlot.fromResource`, this class tracks the source
  * and automatically uses fresh content if it has changed since compilation. This ensures the output is always correct,
  * even if the typed accessors are stale.
  *
  * @param rawSpec
  *   The JSON spec as parsed at compile time
  * @param mod
  *   The typed accessor object for modifications
  * @param sourceInfo
  *   Optional source tracking for staleness detection
  */
class VegaSpec[M](val rawSpec: Json, val mod: M, val sourceInfo: Option[SourceInfo] = None):

  @volatile private var stalenessWarned: Boolean = false

  /** Returns fresh spec content, re-reading from source if changed.
    *
    * If the source has been modified since compilation, this reads the current content and emits a warning. The warning
    * is only shown once per VegaSpec instance.
    */
  private lazy val freshSpec: Json = sourceInfo match
    case Some(info) =>
      info.readCurrentContent() match
        case Some(currentContent) =>
          val currentHash = currentContent.hashCode
          if currentHash != info.contentHash then
            if !stalenessWarned then
              stalenessWarned = true
              System.err.println(
                s"⚠️  ${info.description} has changed since compilation - using fresh content. " +
                  s"Re-evaluate VegaPlot.pwd/fromResource(...) to access any new fields."
              )
            end if
            parse(currentContent).getOrElse(rawSpec)
          else rawSpec
          end if
        case None => rawSpec
    case None => rawSpec

  /** Apply modifications using lambda syntax: spec.build(_.title := "New")
    *
    * If the source has changed, modifications are applied to the fresh content.
    */
  def build(mods: (M => SpecMod)*): Json =
    mods.foldLeft(freshSpec)((json, modFn) => modFn(mod)(json))

  /** Apply modifications directly: spec.buildWith(titleMod, widthMod)
    *
    * If the source has changed, modifications are applied to the fresh content.
    */
  def buildWith(mods: SpecMod*): Json =
    mods.foldLeft(freshSpec)((json, m) => m(json))

end VegaSpec
