package viz.vega

import io.circe.*
import io.circe.parser.parse
import viz.macros.SpecMod
import scala.util.Try

/** Tracks the source file for staleness detection.
  *
  * @param path
  *   Absolute path to the source file
  * @param contentHash
  *   Hash of file content at compile time
  */
case class SourceInfo(path: String, contentHash: Int)

/** Vega spec wrapper to generate typed accessors inferred from the JSON structure.
  *
  * When created from a file via `VegaPlot.pwd`, this class tracks the source file and automatically uses fresh content
  * if the file has changed since compilation. This ensures the output is always correct, even if the typed accessors
  * are stale.
  *
  * @param rawSpec
  *   The JSON spec as parsed at compile time
  * @param mod
  *   The typed accessor object for modifications
  * @param sourceInfo
  *   Optional source file tracking for staleness detection
  */
class VegaSpec[M](val rawSpec: Json, val mod: M, val sourceInfo: Option[SourceInfo] = None):

  @volatile private var stalenessWarned: Boolean = false

  /** Returns fresh spec content, re-reading from file if changed.
    *
    * If the source file has been modified since compilation, this reads the current file content and emits a warning.
    * The warning is only shown once per VegaSpec instance.
    */
  private lazy val freshSpec: Json = sourceInfo match
    case Some(info) =>
      Try {
        val currentContent = scala.io.Source.fromFile(info.path).mkString
        val currentHash = currentContent.hashCode
        if currentHash != info.contentHash then
          if !stalenessWarned then
            stalenessWarned = true
            System.err.println(
              s"⚠️  ${info.path} has changed since compilation - using fresh content. " +
                s"Re-evaluate VegaPlot.pwd(...) to access any new fields."
            )
          end if
          parse(currentContent).getOrElse(rawSpec)
        else rawSpec
        end if
      }.getOrElse(rawSpec)
    case None => rawSpec

  /** Apply modifications using lambda syntax: spec.build(_.title := "New")
    *
    * If the source file has changed, modifications are applied to the fresh file content.
    */
  def build(mods: (M => SpecMod)*): Json =
    mods.foldLeft(freshSpec)((json, modFn) => modFn(mod)(json))

  /** Apply modifications directly: spec.buildWith(titleMod, widthMod)
    *
    * If the source file has changed, modifications are applied to the fresh file content.
    */
  def buildWith(mods: SpecMod*): Json =
    mods.foldLeft(freshSpec)((json, m) => m(json))

end VegaSpec
