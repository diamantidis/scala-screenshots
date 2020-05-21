# Screenshots

![Scala CI](https://github.com/diamantidis/scala-screenshots/workflows/Scala%20CI/badge.svg)
[![codecov](https://codecov.io/gh/diamantidis/scala-screenshots/branch/develop/graph/badge.svg)](https://codecov.io/gh/diamantidis/scala-screenshots)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Follow @diamantidis_io on Twitter](https://img.shields.io/twitter/follow/diamantidis_io.svg?logo=twitter&style=flat&color=blue)](https://twitter.com/diamantidis_io)


A Scala project to take full size screenshot of a webpage in different dimensions.

This project was build as a demo for the post ["A Scala script to take full size webpage screenshots for different screen dimensions"] that I published on my [personal blog].
You can always refer to the post if you want to find more information about this project. 

## Requirements
* [Scala] (2.13.1)
* [sbt] (1.3.10)
* [ChromeDriver] (81.0.4044.69)
 
## How to run from the command line
* Download the project by executing the following commands:
    ```shell script
    git clone -b source https://github.com/diamantidis/scala-screenshots.git
    cd scala-screenshots
    ```
* Open [Main.scala] and replace the following lines:
    ```scala
    val dimensions = List(
      ScreenDimension(1920, 1080), // Laptop
      ScreenDimension(414, 896) // iPhone XR
    )
    
    val Host = "https://diamantidis.github.io"
    val url = s"${Host}/2020/04/19/scala-script-for-fullsize-screenshots-for-different-screen-dimensions"
    ```
* Run 
    ```shell script
    sbt run
    ```
* Run tests
    ```shell script
    sbt clean test
    ```
* Run tests with coverage
    ```shell script
    sbt clean coverage test coverageReport
    ```
* Run `scalafmt` task
    ```shell script
    sbt scalafmtAll
    ```

## License

This project is licensed under the terms of the MIT license. See the [LICENSE] file.

## Contact me

* [Twitter]
* [LinkedIn]
* [Email]


["A Scala script to take full size webpage screenshots for different screen dimensions"]: https://diamantidis.github.io/2020/04/19/scala-script-for-fullsize-screenshots-for-different-screen-dimensions
[personal blog]: https://diamantidis.github.io/
[Scala]: https://www.scala-lang.org/
[sbt]: https://www.scala-sbt.org/
[ChromeDriver]: https://sites.google.com/a/chromium.org/chromedriver/

[Main.scala]: src/main/scala/Main.scala
[LICENSE]: LICENSE

[Twitter]: https://twitter.com/diamantidis_io
[LinkedIn]: http://linkedin.com/in/ioannis-diamantidis
[Email]: mailto:diamantidis@outlook.com