import $ivy.`org.jsoup:jsoup:1.12.1`, org.jsoup.*
import collection.JavaConverters.*
import scala.collection.mutable.ArrayBuffer

val url = "https://vega.github.io/vega-lite/examples/"
val doc = Jsoup.connect(url).get
val previews = doc.select(".imagegroup")

var found = ArrayBuffer[String]()
var already = ArrayBuffer[String]()
val codeGen = for p <- previews.asScala yield

  val className = p.text
    .replace(" ", "")
    .replace("(", "_")
    .replace(")", "")
    .replace(".", "_")
    .replace("/", "_")
    .replace("-", "_")
    .replace("+", "_")
    .replace("1", "One")
    .replace("2", "Two")
    .replace("“", "")
    .replace("”", "")
    .replace("’", "")
    .replace(",", "")
    .replace("&", "") // classname

  val finalCN = if found.contains(className) then
    val theStr = className + already.filter(x => x == className).length.toString()
    already.addOne(className)
    found.addOne(theStr)
    theStr
  else className
  found.addOne(className)
  (
    p.attr("href"), // url
    finalCN
  )

val codeGenFromList = codeGen.toList
val exampleStub = "https://vega.github.io"

val specUrlStub =
  for plot <- codeGenFromList yield s"""case ${plot._2}Lite extends SpecUrl("$exampleStub${plot._1}", VegaLite) """

val toPasteSpecUrls = "\n    " + specUrlStub.mkString("\n    ")
show(toPasteSpecUrls)

val classDefs =
  for plot <- codeGenFromList
  yield s"""case class ${plot._2}Lite (override val mods : JsonMod=List())(using PlotTarget) extends FromUrl(SpecUrl.${plot._2}Lite)"""

val toPastClasses = "\n    " + classDefs.mkString("\n")

show(toPastClasses)
