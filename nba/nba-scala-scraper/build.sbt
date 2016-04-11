organization := "org.faeddalberto"

name := "nba-scala-scraper"

version := "1.0"

scalaVersion := "2.11.7"
crossPaths := false

libraryDependencies ++= Seq(
  "org.jsoup" %  "jsoup" % "1.8.3",
  "com.github.nscala-time" %%  "nscala-time" % "1.4.0",
  "org.scalatest" %%  "scalatest" % "3.0.0-M15" % "test",
  "org.scalamock" %%  "scalamock-scalatest-support" % "3.2.2" % "test"
)
