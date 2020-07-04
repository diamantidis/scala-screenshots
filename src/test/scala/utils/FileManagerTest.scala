package utils

import org.scalatest.funsuite.AnyFunSuite
import java.io.File
import org.scalamock.scalatest.MockFactory

class NoArgsFile() extends File("somePath") {}

class FileManagerTest extends AnyFunSuite with MockFactory {
  test("should return a map for a file with valid JSON format") {
    val fileManager = new FileManager.Live {}

    var configPath = "/usr/share/bin/location/path/bin/scala"
    val file = mock[NoArgsFile]
    (file.exists _).expects().returning(true).once()
    val fileHandler = mock[FileHandler]
    val content = "{\"key\": \"value\"}"
    (fileHandler.getContent _).expects(*).returning(content).anyNumberOfTimes()

    val result = fileManager.getFileManager.contentToMap(file, fileHandler)

    assert(Right(Map("key" -> "value")) == result)
  }

  test("should return MissingFile if the file is missing") {

    val fileManager = new FileManager.Live {}
    val file = mock[NoArgsFile]
    (file.exists _).expects().returning(false).once()
    val fileHandler = mock[FileHandler]

    val result = fileManager.getFileManager.contentToMap(file, fileHandler)

    assert(Left(FileManagerFailure.MissingFile) == result)
  }

  test("should return InvalidFormat for a file with invalid JSON format") {

    val fileManager = new FileManager.Live {}
    val file = mock[NoArgsFile]
    (file.exists _).expects().returning(true).once()
    val fileHandler = mock[FileHandler]
    val content = "raw string"
    (fileHandler.getContent _).expects(*).returning(content).anyNumberOfTimes()

    val result = fileManager.getFileManager.contentToMap(file, fileHandler)

    assert(Left(FileManagerFailure.InvalidFormat) == result)
  }
}
