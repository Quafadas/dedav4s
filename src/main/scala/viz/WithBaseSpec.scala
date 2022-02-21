package viz

import ujson.Value

abstract class WithBaseSpec(val mods: Seq[ujson.Value => Unit] = List())(using PlotTarget) extends Spec:

  lazy val baseSpec: ujson.Value = ???

  /*
    The idea - start from a base spec, "deep copy" it to prevent mutating "state" of any subclass.
    Modify the copy with the list of "modifiers"

    Ideally : validate the outcome against a Schema...
   */
  override def spec: String =
    val temp = ujson.read(baseSpec.toString)
    for m <- mods do m(temp)
    ujson.write(temp, 2)
