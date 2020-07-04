package utils

import org.scalatest.funsuite.AnyFunSuite
import java.io.File
import java.io.FilenameFilter
import org.scalamock.scalatest.MockFactory

class DriverManagerTest extends AnyFunSuite with MockFactory {
  private var jarPath = "/usr/share/bin/location/path/bin/scala"

  class NoArgsFile() extends File(jarPath) {}

  test("should return chromedriver's dir if there is one on the parent dir") {
    val file = mock[NoArgsFile]
    (file.getParentFile _).expects().returning(file).anyNumberOfTimes()
    (file
      .listFiles(_: FilenameFilter))
      .expects(*)
      .returning(Array(file))
      .anyNumberOfTimes()

    (file.getPath _)
      .expects()
      .returning("/usr/share/bin/location/path")
      .anyNumberOfTimes()

    val expected = "/usr/share/bin/location/path/resources/chromedriver"
    assert(expected == DriverManager.chromedriverDir(file))
  }

  test("should return default chromedriver if there it is missing") {
    var jarPath = "/usr/share/bin/location/path/bin/scala"
    val file = mock[NoArgsFile]
    (file.getParentFile _).expects().returning(file).anyNumberOfTimes()
    (file
      .listFiles(_: FilenameFilter))
      .expects(*)
      .returning(Array.empty)
      .anyNumberOfTimes()

    (file.getPath _).expects().returning("string").anyNumberOfTimes()
    val defaulyLocation = "resources/chromedriver"
    assert(defaulyLocation == DriverManager.chromedriverDir(file))
  }
}
