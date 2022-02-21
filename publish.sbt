/* ThisBuild / organization := "io.github.quafadas"
ThisBuild / organizationName := "quafadas"
ThisBuild / organizationHomepage := Some(
  url("https://github.com/Quafadas/dedav4s")
)

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/Quafadas/dedav4s"),
    "scm:git@github.quafadas/dedav4s.git"
  )
)
publishConfiguration := publishConfiguration.value.withOverwrite(true)

ThisBuild / developers := List(
  Developer(
    id = "quafadas",
    name = "Simon Parten",
    email = "quafadas@gmail.com",
    url = url("https://www.google.com")
  )
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
ThisBuild / publishMavenStyle := true
ThisBuild / versionScheme := Some("early-semver")

ThisBuild / licenses := List(
  "Apache 2.0" -> new URL("https://www.apache.org/licenses/LICENSE-2.0")
) */