addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.1.1")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.2")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.13")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.1")

libraryDependencies += "org.scala-js" %% "scalajs-env-nodejs" % "1.0.1"

unmanagedSourceDirectories in Compile +=
  baseDirectory.value.getParentFile / "jsdom-nodejs-env/src/main/scala"
