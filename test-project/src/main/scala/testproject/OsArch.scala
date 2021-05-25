package testproject

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("os", "arch")
@js.native
object OsArch extends js.Object {
  def apply(): String = js.native
}
