sbt-testdocker
===============

Support for running docker containers in tests. This project offers an simple alternative to [Test Containers](https://github.com/testcontainers/testcontainers-scala)
or for when Test Containers will not work for your use-case (Scala.js or Scala Native builds).

[![Apache2.0 license](https://img.shields.io/github/license/Lifeway/CloudGenesis)](LICENSE)

Installation
------------
Add the following to your `project/plugins.sbt` file:

For sbt 1.0:
```
addSbtPlugin("com.lifeway" % "sbt-testdocker" % "1.0.0")
```

Usage
-----

To use Test Docker in your project you can call `start-test-docker` and `stop-test-docker` directly in `sbt`.

Configuration
-------------

To have test-docker automatically startup a container and stop it after tests are complete. In this example, we are 
starting up dynamodb-local and binding it to port 8000 on the host. When the tests are complete, the dynamodb-local
container will be shutdown.

```
testDockerConfig := Some(DockerConfig("amazon/dynamodb-local", 8000, 8000)),
testDockerStart  := testDockerStart.dependsOn(Test / compile).value,
Test / test      := (Test / test).dependsOn(testDockerStart).value,
Test / testOnly  := (Test / testOnly).dependsOn(testDockerStart).evaluated,
Test / testQuick := (Test / testQuick).dependsOn(testDockerStart).evaluated,
Test / testOptions += testDockerCleanup.value
```

Thanks
-----

This work was based on the [sbt-dynamodb] (https://github.com/localytics/sbt-dynamodb) plugin by localytics.
