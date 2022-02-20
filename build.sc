import mill._, scalalib._

object dedav4s extends ScalaModule {
  def scalaVersion = "3.0.2"
  def ivyDeps = Agg(
    ivy"com.lihaoyi::upickle:1.4.3",
    ivy"com.lihaoyi::requests:0.7.0",
    ivy"com.lihaoyi::os-lib:0.8.0",
    ivy"com.lihaoyi::cask:0.8.0",
    ivy"com.lihaoyi::scalatags:0.11.1",
    ivy"org.ekrich::sconfig:1.4.4",
    ivy"org.jsoup:jsoup:1.14.3",
    ivy""
  )
}