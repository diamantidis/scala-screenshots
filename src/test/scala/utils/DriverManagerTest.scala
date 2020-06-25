package utils

import org.scalatest.funsuite.AnyFunSuite
import java.io.File
import java.io.FilenameFilter

class DriverManagerTest extends AnyFunSuite {

  class MockFile(jarPath: String) extends java.io.File(jarPath) {

    override def getParentFile(): File =
      new MockFile("/usr/share/bin/location/path")

    override def listFiles(filter: FilenameFilter): Array[File] =
      Array(new MockFile("/usr/share/bin/location/path"))
  }

  test("Assert ChromeDriver Location") {
    var jarPath = "/usr/share/bin/location/path/bin/scala"
    val mockFile = new MockFile(jarPath)
    val expected = "/usr/share/bin/location/path/resources/chromedriver"
    assert(expected == DriverManager.chromedriverDir(mockFile))
  }
}
