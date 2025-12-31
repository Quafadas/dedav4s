package viz.vega

import io.circe.*
import viz.macros.SpecMod

/** Vega spec wrapper to generate typed accessors inferred from the JSON structure.
	*/
class VegaSpec[M](val rawSpec: Json, val mod: M):
	/** Apply modifications and return the modified spec */
	def build(mods: SpecMod*): Json =
		mods.foldLeft(rawSpec)((json, mod) => mod(json))

end VegaSpec

