package testproject

import org.junit.Test
import org.junit.Assert._

class LibTest {
  @Test def dummy_library_should_append_an_element(): Unit = {
    def count = Lib.getElementsByTagName("p").length

    val oldCount = count
    Lib.appendDocument("foo")
    assertEquals(1, count - oldCount)
  }

  @Test def osArch_should_works(): Unit = {
    assertNotNull(OsArch())
  }
}
