import laika.helium.Helium
import laika.helium.config.HeliumIcon
import laika.helium.config.IconLink
import laika.ast.Path.Root
import laika.theme.config.Color
import java.time.Instant
import com.typesafe.tools.mima.core.*

Global / onChangedBuildSource := ReloadOnSourceChanges
import java.io.File
inThisBuild(
  List(
    scalaVersion := "3.1.0",
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
ThisBuild / tlBaseVersion := "0.7"
ThisBuild / organization := "io.github.quafadas"
ThisBuild / organizationName := "quafadas"
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("quafadas", "Simon Parten")
)
ThisBuild / tlSonatypeUseLegacyHost := false
ThisBuild / tlCiReleaseBranches := Seq("main")

ThisBuild / scalaVersion := "3.1.2"

lazy val generated = crossProject(JVMPlatform, JSPlatform)
  .in(file("generated"))
  .settings(
    tlFatalWarnings := false,
    scalacOptions ++= Seq(
      "-Xmax-inlines:2000"
    ),
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-core" % "0.15.0-M1",
      "io.circe" %%% "circe-parser" % "0.15.0-M1"
    )
  ).enablePlugins(NoPublishPlugin)

lazy val root = tlCrossRootProject.aggregate(core, generated, tests)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .in(file("core"))
  .dependsOn(generated)
  .settings(
    name := "dedav4s",
    description := "Declarative data viz for scala",
    scalacOptions ++= Seq(
      "-Xmax-inlines:2000"
    ),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "2.0.0",
      //"com.lihaoyi" %%% "ujson-circe" % "2.0.0", doesn't exist for scala3
      "com.lihaoyi" %%% "scalatags" % "0.11.1",
      "org.ekrich" %%% "sconfig" % "1.4.4", // otherwise have to upgrade scala
      //"org.scalameta" %%% "munit" % "0.7.29" % Test,
      //"com.github.jupyter" % "jvm-repr" %  "0.4.0",
      ("sh.almond" % "scala-kernel-api" % "0.11.2" % Provided)
        .cross(CrossVersion.for3Use2_13With("", ".4"))
        .exclude("com.lihaoyi", "geny_2.13")
        .exclude("com.lihaoyi", "sourcecode_2.13")
        .exclude("com.lihaoyi", "fansi_2.13")
        .exclude("com.lihaoyi", "os-lib_2.13")
        .exclude("com.lihaoyi", "pprint_2.13")
        .exclude("org.scala-lang.modules", "scala-collection-compat_2.13")
        .exclude("com.github.jupyter", "jvm-repr"),
      "org.jsoup" % "jsoup" % "1.14.3"
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "os-lib" % "0.8.0",
      "com.lihaoyi" %% "cask" % "0.8.0",
      "com.lihaoyi" %% "requests" % "0.7.0"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "2.1.0"
    )
    //webpackBundlingMode := BundlingMode.LibraryOnly(),
    /*stMinimize := Selection.AllExcept("vega-embed", "vega-typings"),
    scalaJSLinkerConfig ~= (_.withSourceMap(false)),
    useYarn := true
    stOutputPackage := "viz.vega",
    Compile / npmDependencies ++= Seq(
      "vega-typings" -> "0.22.2",
      "vega-embed" -> "6.20.8",
      //"vega" -> "5.22.0",
      //"vega-lite" -> "5.2.0"
    )
     */
  )
//.jsEnablePlugins(ScalablyTypedConverterGenSourcePlugin)

lazy val tests = crossProject(JVMPlatform, JSPlatform)
  .in(file("tests"))
  .enablePlugins(NoPublishPlugin)
  .dependsOn(core)
  .settings(
    name := "dedav-tests",
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.0-M3" % Test
  )

lazy val jsdocs = project
  .in(file("jsdocs"))
  .settings(
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) },
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.1.0"
  )
  .dependsOn(root.js)
  .enablePlugins(ScalaJSPlugin)
  .enablePlugins(NoPublishPlugin)

lazy val docs = project
  .in(file("myproject-docs")) // important: it must not be docs/
  .settings(
    mdocJS := Some(jsdocs),
    //mdocOut := new File("docs"),
    mdocIn := new File("raw_docs"),
    mdocVariables ++= Map(
      "js-opt" -> "fast",
      "js-batch-mode" -> "true",
      "js-html-header" ->
        """
<script crossorigin type="text/javascript" src="https://cdn.jsdelivr.net/npm/vega@5"></script>
<script crossorigin type="text/javascript" src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
<script crossorigin type="text/javascript" src="https://cdn.jsdelivr.net/npm/vega-embed@6"></script>
"""
    ),
    libraryDependencies ++= Seq(
      ("org.scalanlp" %% "breeze" % "2.0")
        .exclude("org.scala-lang.modules", "scala-collection-compat_2.13")
        .exclude("org.typelevel", "cats-kernel_2.13")
    ),
    laikaConfig ~= { _.withRawContent },
    tlSiteHeliumConfig ~= {
      // Actually, this *disables* auto-linking, to avoid duplicates with mdoc
      _.site.autoLinkJS()
    },
    laikaTheme := tlSiteHeliumConfig.value.all
      .metadata(
        title = Some("Dedav4s"),
        language = Some("en"),
        description = Some("Declarative data visualisation for scala"),
        authors = Seq("Simon Parten"),
        date = Some(Instant.now)
      )
      .site
      .darkMode
      .themeColors(
        primary = Color.hex("1c44b2"),
        secondary = Color.hex("b26046"),
        primaryMedium = Color.hex("2962ff"),
        primaryLight = Color.hex("e6f4f3"),
        text = Color.hex("000000"),
        background = Color.hex("ffffff"),
        bgGradient = (Color.hex("3788ac"), Color.hex("fff5e6"))
      )
      .site
      .themeColors(
        primary = Color.hex("3788ac"),
        secondary = Color.hex("b26046"),
        primaryMedium = Color.hex("2962ff"),
        primaryLight = Color.hex("fff5e6"),
        text = Color.hex("000000"),
        background = Color.hex("ffffff"),
        bgGradient = (Color.hex("3788ac"), Color.hex("fff5e6"))
      )
      .site
      .topNavigationBar(
        homeLink = IconLink.internal(Root / "README.md", HeliumIcon.home),
        navLinks = Seq(IconLink.external("https://github.com/Quafadas/dedav4s", HeliumIcon.github)),
        highContrast = false
      )
      .build
  )
  .dependsOn(core.jvm)
  .enablePlugins(TypelevelSitePlugin)
  .enablePlugins(NoPublishPlugin)
