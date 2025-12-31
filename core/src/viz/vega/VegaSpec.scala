package viz.vega

import io.circe.*
import viz.macros.SpecMod

/** Vega spec wrapper to generate typed accessors inferred from the JSON structure.
	*/
class VegaSpec[M](val rawSpec: Json, val mod: M):
	/** Apply modifications using lambda syntax: spec.build(_.title := "New") */
	def build(mods: (M => SpecMod)*): Json =
		mods.foldLeft(rawSpec)((json, modFn) => modFn(mod)(json))

	/** Apply modifications directly: spec.buildWith(titleMod, widthMod) */
	def buildWith(mods: SpecMod*): Json =
		mods.foldLeft(rawSpec)((json, m) => m(json))

end VegaSpec

