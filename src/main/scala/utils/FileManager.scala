package utils

import java.io.File
import scala.util.parsing.json._
import scala.util.{Failure, Success, Try}

trait FileManager {
  def getFileManager: FileManager.Service
}

sealed trait FileManagerFailure {
  self: Throwable =>
  def description: String
}

object FileManagerFailure {

  case object MissingFile
      extends Exception("File is missing")
      with FileManagerFailure {
    val description = "File is missing"
  }

  case object InvalidFormat
      extends Exception("File should have a json format")
      with FileManagerFailure {
    val description = "File should have a json format"
  }
}

object FileManager {

  trait Service {

    def contentToMap[F <: File](
      file: F,
      fileHandler: FileHandler
    ): Either[FileManagerFailure, Map[String, Any]]
  }

  private case class LiveService() extends FileManager.Service {

    override final def contentToMap[F <: File](
      file: F,
      fileHandler: FileHandler
    ): Either[FileManagerFailure, Map[String, Any]] = {
      val fileExists = file.exists

      if (!fileExists)
        return Left(FileManagerFailure.MissingFile)
      val result = Try {
        val json = fileHandler.getContent(file)
        val result = JSON.parseFull(json.mkString)

        val resultMap = result.asInstanceOf[Option[Map[String, Any]]]
        resultMap
      } match {
        case Success(value: Some[Map[String, Any]]) => Right(value.get)
        case _                                      => Left(FileManagerFailure.InvalidFormat)
      }

      result
    }
  }

  trait Live extends FileManager {
    override final lazy val getFileManager: Service = FileManager.LiveService()
  }
}
