#! /bin/bash

cat version.sbt

sbt clean +scalajs-env-jsdom-nodejs/publishSigned sonatypeBundleUpload sonatypeReleaseAll
