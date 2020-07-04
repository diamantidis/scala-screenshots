import java.io.File
import java.util.Calendar
import org.apache.commons.io.FileUtils
import org.openqa.selenium.{Dimension, OutputType}
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import utils.{
  ConfigMapper,
  ConfigMapperFailure,
  DriverManager,
  FileHandler => SFileHandler,
  FileManager,
  Helpers,
  OptionFailure,
  OptionParser
}
import java.util.logging.FileHandler

object Main extends App {

  case class Universe()
      extends ConfigMapper.Live
      with OptionParser.Live
      with FileManager.Live {}
  val universe = Universe()

  val optionParams = universe.getOptionParser.parse(args) match {
    case Right(value) => value
    case Left(value) =>
      println(value.description)
      sys.exit(1)
  }

  val configFile = optionParams.get('config) match {
    case Some(value) => value
    case _ =>
      println("Cannot find option config")
      sys.exit(1)
  }

  val file = new java.io.File(configFile)
  val fileManager = universe.getFileManager

  val fileHandler = new SFileHandler()

  val configMap = fileManager.contentToMap(file, fileHandler) match {
    case Right(value) => value
    case Left(value) =>
      println(value.description)
      sys.exit(1)
  }

  val config = universe.getConfigMapper.parse(configMap) match {
    case Right(value) => value
    case Left(errors: List[ConfigMapperFailure]) =>
      errors.map(_.description).map(println)
      sys.exit(1)
  }

  val host = config.host
  val dimensions = config.dimensions
  val urls = config.urls

  val url =
    s"${host}/${urls.head}"

  val jarPath = getClass
    .getProtectionDomain()
    .getCodeSource()
    .getLocation()
    .toURI()
    .getPath()
  val chromedriverDir = DriverManager.chromedriverDir(new File(jarPath))
  System.setProperty("webdriver.chrome.driver", chromedriverDir)

  // Setup webdriver
  val chromeOptions = new ChromeOptions()
  chromeOptions.addArguments("headless")
  val webDriver = new ChromeDriver(chromeOptions)

  // Compute the directory where the screenshots will be saved
  val calendar = Calendar.getInstance()
  val baseDir = "./screenshots"
  val day = "%02d".format(calendar.get(Calendar.DATE))
  val month = "%02d".format(calendar.get(Calendar.MONTH) + 1)
  val year = calendar.get(Calendar.YEAR)

  val directory = s"$baseDir/$year-$month-$day"

  // Compute the name of the file
  val filename = Helpers.getFilename(url, host)

  webDriver.get(url)
  Thread.sleep(500)

  dimensions.foreach { dimension =>
    // Set initial window size
    val width = dimension.width
    val originalHeight = dimension.height

    val originalDimension = new Dimension(width, originalHeight)
    webDriver.manage.window.setSize(originalDimension)

    // Get body height and update the window's height to match this value
    val body = webDriver.findElementByTagName("body")
    val height = body.getSize.height

    val newDimension = new Dimension(width, height)
    webDriver.manage.window.setSize(newDimension)

    // Get screenshot
    val file = webDriver.getScreenshotAs(OutputType.FILE)

    // Save screenshot
    val destination = s"$directory/$filename(${width}x$originalHeight).png"
    val destFile = new File(destination)
    FileUtils.copyFile(file, destFile)
  }

  webDriver.quit()
}
