package utils

import java.io.File
import java.io.FilenameFilter

object DriverManager {

  var defaultLocation = "resources/chromedriver"

  def chromedriverDir[F <: File](jarPath: F): String = {

    val jarRootDir = jarPath.getParentFile().getParentFile()

    val fileIsMissing = jarRootDir
      .listFiles(new FilenameFilter {
        def accept(dir: File, name: String) = name.equals("resources")
      })
      .map { list =>
        print(list)
        list
      }
      .flatMap(_.listFiles(new FilenameFilter {
        def accept(dir: File, name: String) = name.equals("chromedriver")
      }))
      .map { list =>
        print(list)
        list
      }
      .isEmpty

    val chromedriverPath = jarRootDir + "/resources/chromedriver"

    return if (fileIsMissing) defaultLocation
    else chromedriverPath
  }
}
