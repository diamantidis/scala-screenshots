import com.typesafe.sbt.SbtNativePackager.Universal

scalaVersion := "2.13.1"

name := "scala-screenshots"
organization := "io.github.diamantidis"
maintainer := "diamantidis@outlook.com"
version := "1.0"

libraryDependencies += "org.seleniumhq.selenium" % "selenium-java"            % "3.141.59"
libraryDependencies += "commons-io"              % "commons-io"               % "2.8.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
libraryDependencies += "org.scalatest"          %% "scalatest"                % "3.2.3" % Test
libraryDependencies += "org.scalamock"          %% "scalamock"                % "5.0.0" % Test

enablePlugins(JavaAppPackaging)

mappings in Universal += {
  file("resources/chromedriver") -> "resources/chromedriver"
}
