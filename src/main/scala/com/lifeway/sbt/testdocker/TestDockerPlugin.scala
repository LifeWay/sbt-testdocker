package com.lifeway.sbt.testdocker

import sbt._
import Keys._

object TestDockerPlugin extends AutoPlugin {
  override val trigger              = allRequirements
  val autoImport                    = TestDockerKeys
  override lazy val projectSettings = TestDockerKeys.baseTestDockerSettings
}
