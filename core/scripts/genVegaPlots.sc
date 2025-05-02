import $ivy.`org.jsoup:jsoup:1.12.1`, org.jsoup.*
import collection.JavaConverters.*

val url = "https://vega.github.io/vega/examples/"
val doc = Jsoup.connect(url).get
val previews = doc.select(".preview")

val codeGen =
  for (p <- previews.asScala)
    yield (
      p.children.asScala.head.attr("href"), // url
      p.text.replace(" ", "") // classname
    )

val codeGenFromList = codeGen.toList
val exampleStub = "https://vega.github.io/vega/examples/"

val specUrlStub = for (plot <- codeGenFromList) yield
  val base = s"""case ${plot._2} extends SpecUrl("$exampleStub${plot._1}"""
  val removeTrailingSlash = base.take(base.length - 1)
  s"$removeTrailingSlash.vg.json\")"

val caseStub =
  """case class BarChart(override val mods : JsonMod=List())(using PlotTarget) extends FromUrl(SpecUrl.BarChart)"""

val classDefs = for (plot <- codeGenFromList)
  yield s"""case class ${plot._2}(override val mods : JsonMod=List())(using PlotTarget) extends FromUrl(SpecUrl.${plot._2})"""

val toPasteSpecUrls = "\n    " + specUrlStub.mkString("\n    ")

val toPastClasses = "\n    " + classDefs.mkString("\n")

show(toPastClasses)
