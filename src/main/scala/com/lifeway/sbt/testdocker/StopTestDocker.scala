package com.lifeway.sbt.testdocker

import sbt.File
import sbt.Keys._
import TestDockerKeys.DockerConfig

import scala.sys.process._

object StopTestDocker {
  def apply(dockerConfig: Seq[DockerConfig], streamz: TaskStreams): Unit = {
    dockerConfig.foreach { config =>
      streamz.log.info(s"Stopping test-docker on port ${config.hostPort}")
      s"docker stop sbt-testdocker-${config.hostPort}".!
    }
  }
}
