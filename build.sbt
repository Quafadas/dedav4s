Global / semanticdbEnabled := true
Global / onChangedBuildSource := ReloadOnSourceChanges
import java.io.File
val libV = "0.0.7"

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
//      "sh.almond"      % "scala-kernel-api_2.13.4" % "0.11.2" % Provided,
      "org.jsoup" % "jsoup" % "1.14.3",
    )
  )

val scalafixRules = Seq(
  "OrganizeImports",
  "DisableSyntax",
  "LeakingImplicitClassVal",
  "ProcedureSyntax",
  "NoValInForComprehension"
).mkString(" ")

// need a different scala version to respect the version of mdoc
lazy val docs = project
  .in(file("myproject-docs")) // important: it must not be docs/
  .settings(
    mdocVariables := Map(
      "VERSION" -> libV
    ),
    mdocOut := new File("docs"),
    mdocIn := new File("rawDocs"),
    scalaVersion := "3.1.0",
    mdocAutoDependency := false,
    libraryDependencies ++= Seq(
          ("org.scalameta" %% "mdoc" % "2.2.24").exclude(
        "com.lihaoyi",
        "geny_2.13"
      )
    )
    
  )
  .dependsOn(root)
  .enablePlugins(MdocPlugin)
