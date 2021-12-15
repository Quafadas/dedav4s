Global / semanticdbEnabled := true
Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / organization := "io.github.quafadas"
ThisBuild / organizationName := "quafadas"
ThisBuild / organizationHomepage := Some(
  url("https://github.com/Quafadas/dedav4s")
)

ThisBuild / homepage := Some(url("https://github.com/username/project"))
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishMavenStyle := true
ThisBuild / versionScheme := Some("early-semver")
ThisBuild / publishTo := {
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value) {
    Some("snapshots" at nexus + "content/repositories/snapshots")
  }
  else {
   Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}
ThisBuild / licenses := List(
  "Apache 2.0" -> new URL("https://www.apache.org/licenses/LICENSE-2.0")
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "dedav4s",
    description := "Declarative data viz for scala",
    version := "0.0.2",
    scalaVersion := "3.1.0",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "upickle" % "1.4.2",
      "com.lihaoyi" %% "requests" % "0.6.9"
    )
  )

val scalafixRules = Seq(
  "OrganizeImports",
  "DisableSyntax",
  "LeakingImplicitClassVal",
  "ProcedureSyntax",
  "NoValInForComprehension"
).mkString(" ")

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/Quafadas/dedav4s"),
    "scm:git@github.quafadas/dedav4s.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "quafadas",
    name = "Simon Parten",
    email = "quafadas@gmail.com",
    url = url("https://www.google.com")
  )
)
