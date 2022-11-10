package com.lifeway.sbt.testdocker

import java.io.File

import sbt.Keys._
import sbt._

import scala.concurrent.duration._

object TestDockerKeys {
  case class DockerConfig(image: String, containerPort: Int, hostPort: Int)
  lazy val testDockerConfig = settingKey[Option[DockerConfig]]("Container image, container port, and host port to use.")

  lazy val testDockerStart   = TaskKey[Unit]("start-test-docker")
  lazy val testDockerStop    = TaskKey[Unit]("stop-test-docker")
  lazy val testDockerCleanup = TaskKey[Tests.Cleanup]("test-docker-test-cleanup")

  lazy val baseTestDockerSettings = Seq(
    testDockerConfig := None,
    testDockerStart := StartTestDocker(
      testDockerConfig.value,
      streams.value
    ),
    testDockerStop := StopTestDocker(testDockerConfig.value, streams.value),
    testDockerCleanup := {
      val testDockerConfigValue = testDockerConfig.value
      val streamsValue          = streams.value
      Tests.Cleanup(() => StopTestDocker(testDockerConfigValue, streamsValue))
    }
  )
}
