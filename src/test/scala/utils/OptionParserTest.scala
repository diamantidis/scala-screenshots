package utils

import org.scalatest.funsuite.AnyFunSuite

class OptionParserTest extends AnyFunSuite {
  test("parse options") {
    val optionParser = new OptionParser.Live {}

    val scenarios = List(
      Map(
        'input -> List[String]("--config", "screenshot.json"),
        'expected -> Right(Map('config -> "screenshot.json"))
      ),
      Map(
        'input -> List[String](),
        'expected -> Left(OptionFailure.NoArgument)
      ),
      Map(
        'input -> List[String]("--someotherOption", "screenshot.json"),
        'expected -> Left(OptionFailure.InvalidArgument)
      ),
      Map(
        'input -> List[String]("--config"),
        'expected -> Left(OptionFailure.InvalidArgument)
      ),
      Map(
        'input -> List[String]("--config", "screenshot.json", "--someOption"),
        'expected -> Left(OptionFailure.InvalidArgument)
      )
    )

    for (scenario <- scenarios) {
      val input = scenario.get('input).get.asInstanceOf[List[String]]
      val actual = optionParser.getOptionParser.parse(input)
      val expected = scenario.get('expected).get
      assert(expected == actual)
    }
  }
}
