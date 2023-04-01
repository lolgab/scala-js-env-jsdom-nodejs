import sbt._
import Keys._
import com.jsuereth.sbtpgp.SbtPgp.autoImport._
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleaseStateTransformations._

lazy val scala213 = "2.13.4"
lazy val scala212 = "2.12.13"
lazy val scala211 = "2.11.12"

inThisBuild(Seq(
  organization := "net.exoego",
  scalaVersion := scala213,
  crossScalaVersions := Seq(scala213, scala212, scala211),
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

  ThisBuild / publishTo := sonatypePublishToBundle.value,
  Test / publishArtifact := false,
  Compile / packageDoc / publishArtifact := true,
  Compile / packageSrc / publishArtifact := true,
  sonatypeTimeoutMillis := 3 * 60 * 60 * 1000,
  publishConfiguration := publishConfiguration.value.withOverwrite(true),
  publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
  Compile / doc / sources := Seq.empty,
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
    Compile / publishArtifact := false,
    publish := {},
    publishLocal := {},
  ).aggregate(`scalajs-env-jsdom-nodejs`, `test-project`)

lazy val `scalajs-env-jsdom-nodejs`: Project = project.in(file("jsdom-nodejs-env")).
  settings(
    commonSettings,

    libraryDependencies ++= Seq(
      "org.scala-js" %% "scalajs-js-envs" % MetaBuildShared.scalajsJsEnvsVersion,
      "org.scala-js" %% "scalajs-env-nodejs" % MetaBuildShared.scalajsJsEnvsVersion,

      "com.novocode" % "junit-interface" % "0.11" % "test",
      "org.scala-js" %% "scalajs-js-envs-test-kit" % MetaBuildShared.scalajsJsEnvsVersion % "test"
    )
  )

lazy val `test-project`: Project = project.
  enablePlugins(ScalaJSPlugin).
  enablePlugins(ScalaJSJUnitPlugin).
  settings(
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)),
    jsEnv := new net.exoego.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
  )
