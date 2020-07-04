package utils

import models.{Config, ScreenDimension}
import scala.util.{Failure, Success, Try}

trait ConfigMapper {
  def getConfigMapper: ConfigMapper.Service
}

sealed trait ConfigMapperFailure {
  self: Throwable =>
  def description: String
}

object ConfigMapperFailure {

  case object MissingHost
      extends Exception("'host' key is missing from the config file")
      with ConfigMapperFailure {
    val description = "'host' key is missing from the config file"
  }

  case object MissingDimensions
      extends Exception("'dimensions' key is missing from the config file")
      with ConfigMapperFailure {
    val description = "'dimensions' key is missing from the config file"
  }

  case object MissingURLs
      extends Exception("'urls' key is missing from the config file")
      with ConfigMapperFailure {
    val description = "'urls' key is missing from the config file"
  }
}

object ConfigMapper {

  trait Service {

    def parse(
      config: Map[String, Any]
    ): Either[List[ConfigMapperFailure], Config]
  }

  private case class LiveService() extends ConfigMapper.Service {

    override final def parse(
      config: Map[String, Any]
    ): Either[List[ConfigMapperFailure], Config] = {

      val hostParam: Either[ConfigMapperFailure, String] =
        config.get("host").asInstanceOf[Option[String]] match {
          case Some(value) => Right(value)
          case None        => Left(ConfigMapperFailure.MissingHost)
        }

      val dimensionsParam: Either[ConfigMapperFailure, List[Map[String, Any]]] =
        config
          .get("dimensions")
          .asInstanceOf[Option[List[Map[String, Any]]]] match {
          case Some(value) => Right(value)
          case None        => Left(ConfigMapperFailure.MissingDimensions)
        }

      val urlsParam: Either[ConfigMapperFailure, List[String]] =
        config.get("urls").asInstanceOf[Option[List[String]]] match {
          case Some(value) => Right(value)
          case None        => Left(ConfigMapperFailure.MissingURLs)
        }

      val result = List(hostParam, dimensionsParam, urlsParam).partitionMap(
        identity
      ) match {
        case (
              nil,
              List(
                host: String,
                dimensions: List[Map[String, Any]],
                urls: List[String]
              )
            ) =>
          var screenDimensions = dimensions
            .flatMap { x =>
              Try {
                val result = for {
                  width <- convertAnyToInt(x.get("width"))
                  height <- convertAnyToInt(x.get("height"))
                } yield ScreenDimension(width, height)

                result
              } match {
                case Success(value: Some[ScreenDimension]) => Some(value.get)
                case _                                     => None
              }
            }

          Right(Config(host, urls, screenDimensions))
        case (left, _) => Left(left)
      }

      return result
    }

    private def convertAnyToInt(data: Any): Option[Int] =
      return data match {
        case Some(i: Int)    => Some(i)
        case Some(f: Float)  => Some(f.toInt)
        case Some(d: Double) => Some(d.toInt)
        case _               => None
      }
  }

  trait Live extends ConfigMapper {
    override def getConfigMapper: Service = ConfigMapper.LiveService()
  }
}
