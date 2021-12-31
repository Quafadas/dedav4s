Global / semanticdbEnabled := true
Global / onChangedBuildSource := ReloadOnSourceChanges
import java.io.File
val libV = "0.0.6"

lazy val root = project
  .in(file("."))
  .settings(
    name := "dedav4s",
    description := "Declarative data viz for scala",
    version := libV,
    scalaVersion := "3.0.2",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "upickle" % "1.4.2",
      "com.lihaoyi" %% "requests" % "0.6.9",
      "org.jsoup" % "jsoup" % "1.14.3"
    )
  )

val scalafixRules = Seq(
  "OrganizeImports",
  "DisableSyntax",
  "LeakingImplicitClassVal",
  "ProcedureSyntax",
  "NoValInForComprehension"
).mkString(" ")

lazy val docs = project
  .in(file("myproject-docs")) // important: it must not be docs/
  .settings(
    mdocVariables := Map(
      "VERSION" -> libV
    ),
    mdocOut := new File("publishedDoc"),
    scalaVersion := "3.0.2",
    mdocAutoDependency := false,
    libraryDependencies ++= List(
      ("org.scalameta" %% "mdoc" % "2.2.24").exclude(
        "com.lihaoyi",
        "geny_2.13"
      )
    )
  )
  .dependsOn(root)
  .enablePlugins(MdocPlugin)
