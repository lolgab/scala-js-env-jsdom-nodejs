#! /bin/bash

sbt clean +scalajs-env-jsdom-nodejs/publishSigned sonatypeBundleUpload sonatypeReleaseAll
