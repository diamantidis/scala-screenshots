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
* [sbt] (1.3.13)
* [ChromeDriver] (81.0.4044.69)
 
## Installation

* Download the project by executing the following commands:
    ```shell script
    git clone -b source https://github.com/diamantidis/scala-screenshots.git
    cd scala-screenshots
    ```
* If you want to install the script globally, the repo contains a Makefile with a target to install. This target use [sbt-native-packager] to generate an archive and then create a symlink to `/usr/local/bin`.

    ```bash
    make install
    ```
* Otherwise, you can use SBT to run the program from the project directory. See [Usage] section. 

## Usage

* To configure the program, create a `json` file with the following format:
    ```json
    {
        "host": "the host",
        "urls": [
            "a url to take screenshot for"
        ],
        "dimensions": [
            {
                "width": 1920,
                "height": 1080
            }
        ]
    }
    ```
    For more details about this file, refer to the section [Configuration].

* Run 
    * From project directory With SBT:
        ```shell script
        sbt "run --config config.json"
        ```
    * From any directory, after installing using `make install`:
        ```shell script
        scala-screenshots --config config.json
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

## Configuration
To configure `Scala-screenshot`, create a json file and pass it as a value for the `--config` argument. The following parameters can be configured:
* Parameters
  * **`host`**: The host of the website.
  * **`urls`**: A list of strings with the urls to take screenshots for.
  * **`dimensions`**: A list of `Dimension` objects. Each object should have a **`width`** and a **`height`** key with a numeric value. The script will take screenshot for each of these dimensions.

* An example:
    ```json
    {
        "host": "https://diamantidis.github.io",
        "urls": [
            "2020/04/19/scala-script-for-fullsize-screenshots-for-different-screen-dimensions"
        ],
        "dimensions": [
            {
                "width": 1920,
                "height": 1080
            },
            {
                "width": 414,
                "height": 896
            }
        ]
    }
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

[sbt-native-packager]: https://github.com/sbt/sbt-native-packager
[Usage]: #usage
[Configuration]: #configuration

[LICENSE]: LICENSE

[Twitter]: https://twitter.com/diamantidis_io
[LinkedIn]: http://linkedin.com/in/ioannis-diamantidis
[Email]: mailto:diamantidis@outlook.com
