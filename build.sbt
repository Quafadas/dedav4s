import laika.helium.Helium
import laika.helium.config.*
import laika.markdown.github.GitHubFlavor
import laika.parse.code.SyntaxHighlighting
import laika.ast.Path.Root
import laika.theme.config.Color
import laika.ast.LengthUnit.*
import laika.ast.*
import com.typesafe.tools.mima.core.Problem
import com.typesafe.tools.mima.core.ProblemFilters

import java.time.OffsetDateTime

Global / onChangedBuildSource := ReloadOnSourceChanges
import java.io.File

val scalaV = "3.3.1"

inThisBuild(
  List(
    scalaVersion := scalaV
  )
)
inThisBuild(
  mimaBinaryIssueFilters ++= Seq(
    ProblemFilters.exclude[Problem]("viz.dsl.*") // don't maintain the DSL
  )
)

ThisBuild / tlVersionIntroduced := Map("3" -> "1.0.0") // disable mima for now

ThisBuild / tlSitePublishBranch := Some("main")
ThisBuild / tlBaseVersion := "0.9"
ThisBuild / organization := "io.github.quafadas"
ThisBuild / organizationName := "quafadas"
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("quafadas", "Simon Parten")
)
ThisBuild / tlSonatypeUseLegacyHost := false
ThisBuild / tlCiReleaseBranches := Seq("main")
ThisBuild / scalaVersion := scalaV

lazy val root = tlCrossRootProject.aggregate(core, generated, dedav_laminar, dedav_calico, unidocs, tests)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .in(file("core"))
  .settings(
    name := "dedav4s",
    description := "Declarative data viz for scala",
    scalacOptions ++= Seq(
      "-Xmax-inlines:2000",
      """-Wconf:cat=deprecation:s"""
    ),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "3.1.3",
      "com.lihaoyi" %%% "scalatags" % "0.12.0",
      "org.ekrich" %%% "sconfig" % "1.5.1"
      // ("sh.almond" % "scala-kernel-api" % "0.13.14" % Provided)
      //   .cross(CrossVersion.for3Use2_13With("", ".10"))
      //   .exclude("com.lihaoyi", "geny_2.13")
      //   .exclude("com.lihaoyi", "sourcecode_2.13")
      //   .exclude("com.lihaoyi", "fansi_2.13")
      //   .exclude("com.lihaoyi", "os-lib_2.13")
      //   .exclude("com.lihaoyi", "pprint_2.13")
      //   .exclude("org.scala-lang.modules", "scala-collection-compat_2.13")
      //   .exclude("com.github.jupyter", "jvm-repr")
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "os-lib" % "0.9.2",
      "com.lihaoyi" %% "cask" % "0.9.1",
      "com.lihaoyi" %% "requests" % "0.8.0",
      ("sh.almond" %% "scala-kernel-api" % "0.14.0-RC14" % Provided)
        .cross(CrossVersion.full)
        .exclude("com.lihaoyi", "geny_2.13")
        .exclude("org.scala-lang.modules", "scala-collection-compat_2.13")
        .exclude("com.lihaoyi", "os-lib_2.13")
        .exclude("com.github.jupyter", "jvm-repr"),
      "org.jsoup" % "jsoup" % "1.16.2"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "2.7.0",
      ("org.scala-js" %%% "scalajs-java-securerandom" % "1.0.0").cross(CrossVersion.for3Use2_13)
    )
  )

lazy val generated = crossProject(JVMPlatform, JSPlatform)
  .in(file("generated"))
  .dependsOn(core)
  .settings(
    tlFatalWarnings := false,
    scalacOptions ++= Seq(
      "-Xmax-inlines:2000"
    ),
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-core" % "0.14.6",
      "io.circe" %%% "circe-parser" % "0.14.6"
    )
  )

lazy val dedav_calico = project
  .in(file("calico"))
  .dependsOn(generated.jvm)
  .settings(
    libraryDependencies += "com.armanbilge" %%% "calico" % "0.2.1"
  )
  .dependsOn(core.js)
  .enablePlugins(ScalaJSPlugin)

lazy val dedav_laminar = project
  .in(file("laminar"))
  .settings(
    libraryDependencies += "com.raquo" %%% "laminar" % "16.0.0"
  )
  .dependsOn(core.js)
  .enablePlugins(ScalaJSPlugin)

lazy val tests = crossProject(JVMPlatform, JSPlatform)
  .in(file("tests"))
  .enablePlugins(NoPublishPlugin)
  .dependsOn(core, generated)
  .settings(
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.0-M10" % Test
  )
  .jvmSettings(name := "tests-jvm")
  .jsSettings(name := "tests-js")

lazy val jsdocs = project
  .in(file("jsdocs"))
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.4.0",
    libraryDependencies += ("org.scala-js" %%% "scalajs-java-securerandom" % "1.0.0").cross(CrossVersion.for3Use2_13),
    libraryDependencies += ("io.github.cquiroz" %%% "scala-java-time" % "2.5.0").cross(CrossVersion.for3Use2_13)
  )
  .dependsOn(dedav_calico, dedav_laminar, generated.js)
  .enablePlugins(ScalaJSPlugin)
  .enablePlugins(NoPublishPlugin)

lazy val unidocs = project
  .in(file("unidocs"))
  .enablePlugins(TypelevelUnidocPlugin) // also enables the ScalaUnidocPlugin
  .settings(
    name := "dedav4s-docs",
    ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(core.jvm, generated.jvm)
  )

lazy val docs = project
  .in(file("myproject-docs")) // important: it must not be docs/
  .dependsOn(generated.jvm)
  .settings(
    mdocJS := Some(jsdocs),
    mdocIn := new File("raw_docs"),
    mdocVariables += "js-opt" -> "fast",
    libraryDependencies ++= Seq(
      ("org.scalanlp" %% "breeze" % "2.1.0")
    ),
    // laikaTheme := Helium.defaults.build,
    // NOTE: Needed for Javasriptin in Laika
    laikaConfig ~= { _.withRawContent },
    laikaExtensions := Seq(
      GitHubFlavor,
      SyntaxHighlighting
    ),
    tlSiteHelium := {
      Helium.defaults.site
        .metadata(
          title = Some("Dedav4s"),
          language = Some("en"),
          description = Some("Declarative data visualisation for scala"),
          authors = Seq("Simon Parten"),
          datePublished = Some(OffsetDateTime.now)
        )
        .site
        .topNavigationBar(
          homeLink = IconLink.internal(Root / "README.md", HeliumIcon.home),
          navLinks = Seq(IconLink.external("https://github.com/Quafadas/dedav4s", HeliumIcon.github))
        )
      Helium.defaults.site
        .externalJS(
          url = "https://cdn.jsdelivr.net/npm/vega@5"
        )
        .site
        .externalJS(
          url = "https://cdn.jsdelivr.net/npm/vega-lite@5"
        )
        .site
        .externalJS(
          url = "https://cdn.jsdelivr.net/npm/vega-embed@6"
        )
        .site
        .autoLinkJS()
      // .site
      // NOTE: Needed for Javasriptin in Laika
      // .internalJS(Root)
      // .site
      // .internalCSS(Root)
    }
  )
  .enablePlugins(TypelevelSitePlugin)
  .enablePlugins(NoPublishPlugin)
