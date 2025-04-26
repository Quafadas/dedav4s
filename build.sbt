import laika.helium.Helium
import laika.helium.config.*
import laika.config.SyntaxHighlighting
import laika.format.Markdown
import laika.ast.Path.Root
import laika.theme.config.Color
import laika.ast.LengthUnit.*
import laika.ast.*
import com.typesafe.tools.mima.core.Problem
import com.typesafe.tools.mima.core.ProblemFilters
import org.scalajs.linker.interface.ModuleSplitStyle

import java.time.OffsetDateTime

ThisBuild / githubWorkflowBuildPreamble ++= Seq(
  WorkflowStep.Use(
    UseRef.Public("actions", "setup-node", "v3"),
    name = Some("Setup NodeJS v18 LTS"),
    params = Map("node-version" -> "18", "cache" -> "npm"),
    cond = Some("matrix.project == 'rootJS'")
  ),
  WorkflowStep.Run(
    List("npm install"),
    cond = Some("matrix.project == 'rootJS'")
  ),
    WorkflowStep.Use(
    UseRef.Public("coursier", "setup-action", "v1"),
    name = Some("Setup Coursier"),
    cond = Some("matrix.project == 'rootJVM'")
  ),
  WorkflowStep.Run(
    List("""cs launch com.microsoft.playwright:playwright:1.51.0 -M "com.microsoft.playwright.CLI" -- install --with-deps"""),
    cond = Some("matrix.project == 'rootJVM'")
  ),
)

Global / onChangedBuildSource := ReloadOnSourceChanges

import java.io.File

val scalaV = "3.7.0-RC4"

ThisBuild / tlFatalWarnings := false

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
ThisBuild / startYear := Some(2023)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("quafadas", "Simon Parten")
)
ThisBuild / sonatypeCredentialHost := xerial.sbt.Sonatype.sonatypeLegacy
ThisBuild / tlCiReleaseBranches := Seq("main")
ThisBuild / scalaVersion := scalaV
ThisBuild / tlJdkRelease := Some(17)

lazy val root = tlCrossRootProject.aggregate(core, dedav_laminar, dedav_calico, unidocs, tests)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .in(file("core"))
  .settings(
    name := "dedav4s",
    description := "Declarative data viz for scala",
    scalacOptions ++= Seq(
      "-Xmax-inlines:2000",
      """-Wconf:cat=deprecation:s""",

    ),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "4.1.0",
      "com.lihaoyi" %%% "scalatags" % "0.13.1",
      "org.ekrich" %%% "sconfig" % "1.8.1"
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "os-lib" % "0.11.4",
      "com.lihaoyi" %% "cask" % "0.10.2",
      "com.lihaoyi" %% "requests" % "0.9.0",
      ("sh.almond" % "scala-kernel-api_3.3.5" % "0.14.1" % Provided)
        .exclude("com.github.jupyter", "jvm-repr"),
        // .cross(CrossVersion.full)
        // .exclude("com.lihaoyi", "geny_2.13")
        // .exclude("org.scala-lang.modules", "scala-collection-compat_2.13")
        // .exclude("com.lihaoyi", "os-lib_2.13")
        // .exclude("com.github.jupyter", "jvm-repr"),
      "org.jsoup" % "jsoup" % "1.18.3"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "2.8.0",
      ("org.scala-js" %%% "scalajs-java-securerandom" % "1.0.0").cross(CrossVersion.for3Use2_13)
    )
  )

// lazy val generated = crossProject(JVMPlatform, JSPlatform)
//   .in(file("generated"))
//   .dependsOn(core)
//   .settings(
//     tlFatalWarnings := false,
//     scalacOptions ++= Seq(
//       "-Xmax-inlines:2000"
//     ),
//     libraryDependencies ++= Seq(
//       "io.circe" %%% "circe-core" % "0.14.6",
//       "io.circe" %%% "circe-parser" % "0.14.6"
//     )
//   )

lazy val dedav_calico = project
  .in(file("calico"))
  .dependsOn(core.jvm)
  .settings(
    libraryDependencies += "com.armanbilge" %%% "calico" % "0.2.3",

  )

  .dependsOn(core.js)
  .enablePlugins(ScalaJSPlugin)

lazy val dedav_laminar = project
  .in(file("laminar"))
  .settings(
    libraryDependencies += "com.raquo" %%% "laminar" % "17.2.1",
  )
  .dependsOn(core.js)
  .enablePlugins(ScalaJSPlugin)

lazy val tests = crossProject(JVMPlatform, JSPlatform)
  .in(file("tests"))
  .enablePlugins(NoPublishPlugin)
  .dependsOn(core)
  .settings(
    libraryDependencies += "org.scalameta" %%% "munit" % "1.1.0" % Test,

  )
  .jvmSettings(
    name := "tests-jvm",
    // classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat,
    libraryDependencies += "com.microsoft.playwright" % "playwright" % "1.51.0" % Test,
    libraryDependencies += "com.microsoft.playwright" % "driver-bundle" % "1.51.0" % Test
  )
  .jsSettings(
    name := "tests-js",
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule),
    },
    scalaJSLinkerConfig ~= {
      _.withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("viz")))
    }
  )
  .jsEnablePlugins(ScalaJSImportMapPlugin)

lazy val jsdocs = project
  .in(file("jsdocs"))
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.0",
    libraryDependencies += ("org.scala-js" %%% "scalajs-java-securerandom" % "1.0.0").cross(CrossVersion.for3Use2_13),
    libraryDependencies += ("io.github.cquiroz" %%% "scala-java-time" % "2.5.0").cross(CrossVersion.for3Use2_13),
    // scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.ESModule))
  )
  .dependsOn(dedav_calico, dedav_laminar, core.js)
  .enablePlugins(ScalaJSPlugin)
  .enablePlugins(ScalaJSImportMapPlugin)
  .enablePlugins(NoPublishPlugin)

lazy val unidocs = project
  .in(file("unidocs"))
  .enablePlugins(TypelevelUnidocPlugin) // also enables the ScalaUnidocPlugin
  .settings(
    name := "dedav4s-docs",
    ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(core.jvm)
  )

lazy val docs = project
  .in(file("myproject-docs")) // important: it must not be docs/
  .dependsOn(core.jvm)
  .settings(
    mdocJS := Some(jsdocs),
    mdocIn := new File("raw_docs"),
    mdocVariables += "js-opt" -> "fast",
    libraryDependencies ++= Seq(
      ("org.scalanlp" %% "breeze" % "2.1.0")
    ),
    // laikaTheme := Helium.defaults.build,
    // NOTE: Needed for Javasriptin in Laika
    // laikaIncludeAPI := true,
    laikaConfig ~= {
      _.withRawContent

    },
    laikaExtensions := Seq(
      Markdown.GitHubFlavor,
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
    }
  )
  .enablePlugins(TypelevelSitePlugin)
  .enablePlugins(NoPublishPlugin)
