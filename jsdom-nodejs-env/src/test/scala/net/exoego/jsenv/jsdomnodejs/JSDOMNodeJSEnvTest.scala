package net.exoego.jsenv.jsdomnodejs

import scala.concurrent.duration._

import org.junit.Test

import org.scalajs.jsenv.test.kit.TestKit

class JSDOMNodeJSEnvTest {
  private val kit = new TestKit(new JSDOMNodeJSEnv, 1.minute)

  @Test
  def historyAPI: Unit = {
    kit.withRun(
        // language=JavaScript
        """
        console.log(window.location.href);
        window.history.pushState({}, "", "/foo");
        console.log(window.location.href);
        """) {
      _.expectOut("http://localhost/\n")
        .expectOut("http://localhost/foo\n")
    }
  }

  @Test
  def nodejsRequire: Unit = {
    kit.withRun(
      // language=JavaScript
      """
      const fs = require("fs");
      console.log(fs != null);
      console.log(typeof fs.write === "function");
      """) {  run =>
      run.expectOut("true\n")
    }
  }
}
