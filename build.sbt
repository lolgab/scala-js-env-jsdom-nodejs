import sbt._
import Keys._
import com.jsuereth.sbtpgp.SbtPgp.autoImport._
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleaseStateTransformations._


inThisBuild(Seq(
  version := "2.0.0-SNAPSHOT",
  organization := "net.exoego",

  scalaVersion := crossScalaVersions.value.head,
  scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings"),

  homepage := scmInfo.value.map(_.browseUrl),
  developers := List(
    Developer(
      id = "exoego",
      name = "TATSUNO Yasuhiro",
      email = "ytatsuno.jp@gmail.com",
      url = url("https://www.exoego.net")
    )
  ),
  licenses += ("BSD New",
      url("https://github.com/scala-js/scala-js-env-jsdom-nodejs/blob/master/LICENSE")),
  scmInfo := Some(ScmInfo(
      url("https://github.com/exoego/scala-js-env-jsdom-nodejs"),
      "scm:git:git@github.com:exoego/scala-js-env-jsdom-nodejs.git",
      Some("scm:git:git@github.com:exoego/scala-js-env-jsdom-nodejs.git")))
))

val commonSettings = Def.settings(
  publishMavenStyle := true,
  pomIncludeRepository := { _ => false },

  publishTo in ThisBuild := sonatypePublishToBundle.value,
  publishArtifact in Test := false,
  publishArtifact in (Compile, packageDoc) := true,
  publishArtifact in (Compile, packageSrc) := true,
  sonatypeTimeoutMillis := 3 * 60 * 60 * 1000,
  publishConfiguration := publishConfiguration.value.withOverwrite(true),
  publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
  publishArtifact in packageDoc := false,
  sources in (Compile, doc) := Seq.empty,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    setReleaseVersion,
    commitReleaseVersion,
    runClean,
    releaseStepCommandAndRemaining("+publishSigned"),
    releaseStepCommand("sonatypeBundleRelease"),
    setNextVersion,
    commitNextVersion
  )
)

lazy val root: Project = project.in(file(".")).
  settings(
    publishArtifact in Compile := false,
    publish := {},
    publishLocal := {},

    clean := clean.dependsOn(
      clean in `scalajs-env-jsdom-nodejs`,
      clean in `test-project`
    ).value
  )

lazy val `scalajs-env-jsdom-nodejs`: Project = project.in(file("jsdom-nodejs-env")).
  settings(
    commonSettings,

    libraryDependencies ++= Seq(
      "org.scala-js" %% "scalajs-js-envs" % scalaJSVersion,
      "org.scala-js" %% "scalajs-env-nodejs" % scalaJSVersion,

      "com.novocode" % "junit-interface" % "0.11" % "test",
      "org.scala-js" %% "scalajs-js-envs-test-kit" % scalaJSVersion % "test"
    )
  )

lazy val `test-project`: Project = project.
  enablePlugins(ScalaJSPlugin).
  enablePlugins(ScalaJSJUnitPlugin).
  settings(
    scalaJSUseMainModuleInitializer := true,
    jsEnv := new net.exoego.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
  )
