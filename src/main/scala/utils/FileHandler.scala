package utils

import java.io.File
import scala.io._

class FileHandler {

  def getContent[F <: File](file: File) = {
    val src = Source.fromFile(file)
    src.mkString
  }
}
