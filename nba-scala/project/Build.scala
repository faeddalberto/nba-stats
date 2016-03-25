import sbt._

object NBAStatsBuild extends Build {

  lazy val root = Project(id = "nba-scala",
    base = file(".")) aggregate(scraper, ws) dependsOn(scraper, ws)

  // sub-project in the nba-scala-scraper subdirectory
  lazy val scraper = Project(id = "nba-scraper",
    base = file("nba-scala-scraper"))

  // sub-project in the nba-scala-ws subdirectory
  lazy val ws = Project(id = "nba-ws",
    base = file("nba-scala-ws"))
}