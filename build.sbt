scalaVersion := "2.13.1"

name := "scala-screenshots"
organization := "io.github.diamantidis"
version := "1.0"

libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "3.141.59"
libraryDependencies += "commons-io"              % "commons-io"    % "2.6"
libraryDependencies += "org.scalatest"           %% "scalatest"    % "3.1.2" % Test
