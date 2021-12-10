Global / semanticdbEnabled := true
Global / onChangedBuildSource := ReloadOnSourceChanges


lazy val root = project
  .in(file("."))
  .settings(
    name := "dedav4s",
    description := "Declarative data viz for scala",
    version := "0.0.1-SNAPSHOT",
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
