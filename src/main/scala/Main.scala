import java.io.File
import java.util.Calendar
import org.apache.commons.io.FileUtils
import org.openqa.selenium.{Dimension, OutputType}
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import utils.{DriverManager, Helpers}

object Main extends App {

  case class ScreenDimension(width: Int, height: Int)

  // Define dimensions and urls
  val dimensions = List(
    ScreenDimension(1920, 1080), // Laptop
    ScreenDimension(414, 896) // iPhone XR
  )

  val Host = "https://diamantidis.github.io"

  val url =
    s"${Host}/2020/04/19/scala-script-for-fullsize-screenshots-for-different-screen-dimensions"

  val jarPath = getClass
    .getProtectionDomain()
    .getCodeSource()
    .getLocation()
    .toURI()
    .getPath()
  val chromedriverDir = DriverManager.chromedriverDir(new File(jarPath))
  System.setProperty("webdriver.chrome.driver", chromedriverDir);

  // Setup webdriver
  val options = new ChromeOptions()
  options.addArguments("headless")
  val webDriver = new ChromeDriver(options)

  // Compute the directory where the screenshots will be saved
  val calendar = Calendar.getInstance()
  val baseDir = "./screenshots"
  val day = "%02d".format(calendar.get(Calendar.DATE))
  val month = "%02d".format(calendar.get(Calendar.MONTH) + 1)
  val year = calendar.get(Calendar.YEAR)

  val directory = s"$baseDir/$year-$month-$day"

  // Compute the name of the file
  val filename = Helpers.getFilename(url, Host)

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
