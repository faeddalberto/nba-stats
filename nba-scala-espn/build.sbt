
organization := "org.faeddalberto"

name := "nba-scala-espn"

version := "1.0.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.jsoup" %  "jsoup" % "1.8.3",
  "com.github.nscala-time" %  "nscala-time_2.9.1" % "1.4.0",
  "org.scalatest" %  "scalatest_2.11" % "3.0.0-M15" % "test",
  "org.scalamock" %  "scalamock-scalatest-support_2.11" % "3.2.2"
)
