import laika.helium.Helium
import laika.helium.config.HeliumIcon
import laika.helium.config.IconLink
import laika.ast.Path.Root
import laika.theme.config.Color
import java.time.Instant
import laika.markdown.github.GitHubFlavor
import laika.parse.code.SyntaxHighlighting
import com.typesafe.tools.mima.core.*
import org.typelevel.sbt.site.*
import laika.ast.LengthUnit.*
import laika.helium.config.Favicon
import laika.helium.config.ImageLink
import laika.ast.*
import org.scalajs.linker.interface.ESVersion.*

Global / onChangedBuildSource := ReloadOnSourceChanges
import java.io.File

val scalaV = "3.2.2" // Really we want to tie this to almond, but almond is slow...

inThisBuild(
  List(
    scalaVersion := scalaV,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision
  )
)

inThisBuild(
  mimaBinaryIssueFilters ++= Seq(
    ProblemFilters.exclude[Problem]("viz.*")
  )
)

ThisBuild / tlSitePublishBranch := Some("main")
ThisBuild / tlBaseVersion := "0.8"
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

ThisBuild / githubWorkflowJobSetup ++= Seq(
  WorkflowStep.Use(
    UseRef.Public("actions", "setup-node", "v3"),
    name = Some("Setup NodeJS v16"),
    params = Map("node-version" -> "16", "cache" -> "npm")
    //env = Map("NODE_OPTIONS" -> "--openssl-legacy-provider")
  )
)

ThisBuild /  scalaJSLinkerConfig ~= (
  _.withModuleKind(ModuleKind.ESModule),  
)
ThisBuild / scalaJSLinkerConfig ~= { _.withESFeatures(_.withESVersion(ES2020)) }

lazy val generated = crossProject(JVMPlatform, JSPlatform)
  .in(file("generated"))
  .settings(
    tlFatalWarnings := false,
    scalacOptions ++= Seq(
      "-Xmax-inlines:2000"
    ),
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-core" % "0.14.3",
      "io.circe" %%% "circe-parser" % "0.14.3"
    )
  )

lazy val root = tlCrossRootProject.aggregate(core, generated, unidocs, tests)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .in(file("core"))
  .dependsOn(generated)
  .settings(
    name := "dedav4s",
    description := "Declarative data viz for scala",
    scalacOptions ++= Seq(
      "-Xmax-inlines:2000",
      """-Wconf:cat=deprecation:s"""
    ),    
    
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "3.0.0",
      "com.lihaoyi" %%% "scalatags" % "0.12.0",
      "org.ekrich" %%% "sconfig" % "1.4.4", // otherwise have to upgrade scala
      ("sh.almond" % "scala-kernel-api" % "0.13.3" % Provided)
        .cross(CrossVersion.for3Use2_13With("", ".10"))
        .exclude("com.lihaoyi", "geny_2.13")
        .exclude("com.lihaoyi", "sourcecode_2.13")
        .exclude("com.lihaoyi", "fansi_2.13")
        .exclude("com.lihaoyi", "os-lib_2.13")
        .exclude("com.lihaoyi", "pprint_2.13")
        .exclude("org.scala-lang.modules", "scala-collection-compat_2.13")
        .exclude("com.github.jupyter", "jvm-repr")
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "os-lib" % "0.9.0",
      "com.lihaoyi" %% "cask" % "0.9.0",
      "com.lihaoyi" %% "requests" % "0.8.0",
      "org.jsoup" % "jsoup" % "1.15.4"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "2.4.0",
      ("org.scala-js" %%% "scalajs-java-securerandom" % "1.0.0").cross(CrossVersion.for3Use2_13)
    )
  )

// Currently no JS tests, would be great to change that
lazy val tests = crossProject(JVMPlatform, JSPlatform)
  .in(file("tests"))
  .enablePlugins(NoPublishPlugin)
  .dependsOn(core)
  .settings(
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.0-M7" % Test
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
  .dependsOn(core.js)
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
  .settings(
    mdocJS := Some(jsdocs),
    mdocIn := new File("raw_docs"),
    libraryDependencies ++= Seq(
      ("org.scalanlp" %% "breeze" % "2.1.0")
    ),
    //laikaTheme := Helium.defaults.build,
    laikaConfig ~= { _.withRawContent },
    tlSiteHeliumConfig := {
      Helium.defaults.site
        .metadata(
          title = Some("Dedav4s"),
          language = Some("en"),
          description = Some("Declarative data visualisation for scala"),
          authors = Seq("Simon Parten"),
          date = Some(Instant.now)
        )
        .site
        .topNavigationBar(
          homeLink = IconLink.internal(Root, HeliumIcon.home),
          navLinks = Seq(IconLink.external("https://github.com/Quafadas/dedav4s", HeliumIcon.github))
        )
        .site
        .autoLinkJS()
    }
  )
  .dependsOn(core.jvm)
  .enablePlugins(TypelevelSitePlugin)
  .enablePlugins(NoPublishPlugin)
