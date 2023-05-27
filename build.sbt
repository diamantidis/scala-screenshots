import com.typesafe.sbt.SbtNativePackager.Universal

scalaVersion := "2.13.6"

name := "scala-screenshots"
organization := "io.github.diamantidis"
maintainer := "diamantidis@outlook.com"
version := "1.0"

libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "4.9.1"
libraryDependencies += "commons-io"              % "commons-io"    % "2.11.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "5.1.0" % Test

enablePlugins(JavaAppPackaging)

mappings in Universal += {
  file("resources/chromedriver") -> "resources/chromedriver"
}
