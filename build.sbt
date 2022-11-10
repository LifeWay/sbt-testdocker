name         := "sbt-testdocker"
description  := "Support for running a Docker container during your tests"
organization := "com.lifeway"

scalaVersion := "2.12.16"

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    moduleName := "sbt-testdocker",
    pluginCrossBuild / sbtVersion := {
      scalaBinaryVersion.value match {
        case "2.12" => "1.2.8" // set minimum sbt version
      }
    }
  )

homepage := Some(url(s"https://github.com/Lifeway/sbt-testdocker"))

inThisBuild(
  List(
    organization := "com.lifeway",
    homepage     := Some(url(s"https://github.com/Lifeway/sbt-testdocker")),
    licenses     := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "rmmeans",
        "Ryan Means",
        "ryan.means@lifeway.com",
        url("https://www.lifeway.com")
      )
    )
  )
)

Test / publishArtifact := false
Test / logBuffered     := false
