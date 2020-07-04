package utils

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.EitherValues
import models.{Config, ScreenDimension}

class ConfigMapperTest extends AnyFunSuite {
  test("parse with valid data should return a config model") {
    val configMapper = new ConfigMapper.Live {}

    val data: Map[String, Any] = Map(
      "host" -> "some host",
      "dimensions" -> List(Map("width" -> 124.0, "height" -> 124)),
      "urls" -> List("/url1", "/url2")
    )

    val result = configMapper.getConfigMapper.parse(data)

    val expected = Config(
      "some host",
      List("/url1", "/url2"),
      List(ScreenDimension(124, 124))
    )
    assert(expected == result.right.get)
  }

  test("parse with invalid dimension should return a config model") {
    val configMapper = new ConfigMapper.Live {}

    val data: Map[String, Any] = Map(
      "host" -> "some host",
      "dimensions" -> List(
        Map("width" -> 124.0, "height" -> 124),
        Map("width" -> 124.0, "height" -> "String")
      ),
      "urls" -> List("/url1", "/url2")
    )

    val result = configMapper.getConfigMapper.parse(data)

    val expected = Config(
      "some host",
      List("/url1", "/url2"),
      List(ScreenDimension(124, 124))
    )
    assert(expected == result.right.get)
  }

  test("parse with float width or height should return a config model") {
    val configMapper = new ConfigMapper.Live {}

    val data: Map[String, Any] = Map(
      "host" -> "some host",
      "dimensions" -> List(
        Map("width" -> 124.toFloat, "height" -> 124.toFloat)
      ),
      "urls" -> List("/url1", "/url2")
    )

    val result = configMapper.getConfigMapper.parse(data)

    val expected = Config(
      "some host",
      List("/url1", "/url2"),
      List(ScreenDimension(124, 124))
    )
    assert(expected == result.right.get)
  }

  test("parse with missing host should return an error") {
    val configMapper = new ConfigMapper.Live {}

    val data: Map[String, Any] = Map(
      "dimensions" -> List(Map("width" -> 124.0, "height" -> 124)),
      "urls" -> List("/url1", "/url2")
    )

    val result = configMapper.getConfigMapper.parse(data)

    val expected = Left(List(ConfigMapperFailure.MissingHost))
    assert(expected == result)
  }

  test("parse with missing host & dimension should return a list of errors") {
    val configMapper = new ConfigMapper.Live {}

    val data: Map[String, Any] = Map(
      "urls" -> List("/url1", "/url2")
    )

    val result = configMapper.getConfigMapper.parse(data)

    val expected = Left(
      List(
        ConfigMapperFailure.MissingHost,
        ConfigMapperFailure.MissingDimensions
      )
    )
    assert(expected == result)
  }

  test("parse with all params missing should return a list of errors") {
    val configMapper = new ConfigMapper.Live {}

    val data: Map[String, Any] = Map()

    val result = configMapper.getConfigMapper.parse(data)

    val expected = Left(
      List(
        ConfigMapperFailure.MissingHost,
        ConfigMapperFailure.MissingDimensions,
        ConfigMapperFailure.MissingURLs
      )
    )
    assert(expected == result)
  }
}
