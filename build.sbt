Global / semanticdbEnabled := true
Global / onChangedBuildSource := ReloadOnSourceChanges
import java.io.File
val libV = "0.1.0"

resolvers += "4 jvm repr" at "https://maven.scijava.org/content/repositories/public/"

lazy val root = project
  .in(file("."))
  .settings(
    name := "dedav4s",
    description := "Declarative data viz for scala",
    version := libV,
    scalaVersion := "3.0.2",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "upickle" % "1.4.3",
      "com.lihaoyi" %% "requests" % "0.7.0",
      "com.lihaoyi" %% "cask" % "0.8.0",
       "com.github.jupyter" % "jvm-repr" %  "master-0.4.0-g2e50ad4-1",
      ("sh.almond" % "scala-kernel-api" % "0.11.2" % Provided).cross(CrossVersion.for3Use2_13With("",".4")).exclude(
        "com.lihaoyi",
        "geny_2.13"
      ).exclude(
        "com.lihaoyi",
        "sourcecode_2.13"
      ).exclude(
        "com.lihaoyi",
        "fansi_2.13"
      ).exclude(
        "com.lihaoyi",
        "pprint_2.13"
      ) ,
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
      "org.scalanlp"%%"breeze"%"2.0",
          ("org.scalameta" %% "mdoc" % "2.2.24").exclude(
        "com.lihaoyi",
        "geny_2.13"
      ).exclude(
        "com.lihaoyi",
        "sourcecode_2.13"
      ).exclude(
        "com.lihaoyi",
        "fansi_2.13"
      ).exclude(
        "com.lihaoyi",
        "pprint_2.13"
      )

    )
    
  )
  .dependsOn(root)
  .enablePlugins(MdocPlugin)
