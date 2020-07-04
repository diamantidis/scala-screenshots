package utils

import org.scalatest.funsuite.AnyFunSuite

class HelpersTest extends AnyFunSuite {
  test("Get file name") {

    val host = "https://diamantidis.github.io"
    val scenarios = List(
      Map(
        'url -> s"${host}/2020/04/19/scala-script-for-fullsize-screenshots-for-different-screen-dimensions",
        'expected -> "2020-04-19-scala-script-for-fullsize-screenshots-for-different-screen-dimensions"
      ),
      Map(
        'url -> s"${host}/",
        'expected -> "homepage"
      ),
      Map(
        'url -> s"${host}/faq/",
        'expected -> "faq"
      ),
      Map(
        'url -> s"${host}/ARCHIVE/",
        'expected -> "archive"
      )
    )
    for (scenario <- scenarios) {

      val actual = Helpers.getFilename(scenario.get('url).get, host)
      val expected = scenario.get('expected).get
      assert(actual == expected)
    }
  }
}
