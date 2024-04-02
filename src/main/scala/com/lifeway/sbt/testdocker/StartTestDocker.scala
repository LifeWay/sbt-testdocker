package com.lifeway.sbt.testdocker

import com.lifeway.sbt.testdocker.TestDockerKeys.DockerConfig

import java.net.Socket
import sbt.File
import sbt.Keys.*

import scala.sys.process.*
import scala.util.Try

object StartTestDocker {
  private def isHostPortActive(port: Int): Boolean = Try(new Socket("localhost", port).close()).isSuccess
  def apply(
      dockerConfig: Seq[DockerConfig],
      streamz: TaskStreams
  ): Unit = {
    if (dockerConfig.length == 0) streamz.log.error("DOCKER WILL NOT BE STARTING. CONFIGURATION NOT PROVIDED")
    dockerConfig.foreach { config =>
      val containerImage = config.image
      val containerPort  = config.containerPort
      val hostPort       = config.hostPort
      val arg =
        s"docker run -d -p ${hostPort}:${containerPort} --name sbt-testdocker-$hostPort --rm $containerImage"

      if (isHostPortActive(hostPort)) {
        streamz.log.warn(s"An existing process is already using $hostPort")
      } else {
        streamz.log.info("Starting testdocker image")
        Process(arg).run()
        var attempts = 0
        val r        = attempts = attempts + 1

        do {
          streamz.log.info(s"Waiting for testdocker to boot on port $hostPort")
          Thread.sleep(250)
          attempts = attempts + 1
        } while (!isHostPortActive(hostPort) && attempts <= 240)
      }
    }
  }

}
