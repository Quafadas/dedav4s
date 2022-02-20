import mill._, scalalib._ 
import mill.scalalib.publish._
import mill.scalalib.bsp.ScalaMetalsSupport
import scala.annotation.compileTimeOnly

object dedav4s extends ScalaModule with PublishModule with ScalaMetalsSupport  {
    def publishVersion = "0.2.2"
    def pomSettings = PomSettings(
        description = "Declarative data viz for scala",
        organization = "com.lihaoyi",
        url = "https://github.com/lihaoyi/example",
        licenses = Seq(License.MIT),
        versionControl = VersionControl.github("lihaoyi", "example"),
        developers = Seq(
        Developer("lihaoyi", "Li Haoyi", "https://github.com/lihaoyi")
        )
    )

  def scalaVersion = "3.0.2"
  def semanticDbVersion = "4.4.35"
  def ivyDeps = Agg(
    ivy"com.lihaoyi::upickle:1.4.3",
    ivy"com.lihaoyi::requests:0.7.0",
    ivy"com.lihaoyi::os-lib:0.8.0",
    ivy"com.lihaoyi::cask:0.8.0",
    ivy"com.lihaoyi::scalatags:0.11.1",
    ivy"org.ekrich::sconfig:1.4.4",
    ivy"org.jsoup:jsoup:1.14.3",    
  )

  def compileIvyDeps = Agg(
      ivy"sh.almond:scala-kernel-api_2.13.4:0.11.2".exclude(
          "com.lihaoyi" -> "upickle",
          "com.lihaoyi" -> "requests",
          "com.lihaoyi" -> "os-lib",
          "com.lihaoyi" -> "cask",
          "com.lihaoyi" -> "scalatags",
          "com.lihaoyi" -> "sconfig",
          "org.scala-lang.modules" -> "scala-collection-compat_2.13",
          "com.github.jupyter" -> "jvm-repr"
      )
  )
}