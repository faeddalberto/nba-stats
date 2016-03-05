package com.faeddalberto.nbastats.gamedefiner

import com.faeddalberto.nbastats.domain.Team
import com.faeddalberto.nbastats.provider.StubGamesDocumentProvider
import org.scalatest.{FlatSpec, Matchers}

class GameFactoryTest extends FlatSpec with Matchers {

  "GameFactory.getAllGamesOfTheYear" should "return a map containing matches played by each team" in {
    val bulls :Team = new Team("Chicago Bulls", "http://espn.go.com/nba/team/_/name/chi/chicago-bulls", "chi", "chicago-bulls")
    val lakers :Team = new Team("Los Angeles Lakers", "http://espn.go.com/nba/team/_/name/LAL/los-angeles-lakers", "LAL", "los-angeles-lakers")

    val gameFactory = new GameFactory(StubGamesDocumentProvider)
    val bullsAndLakersGames = gameFactory.getAllTeamsSeasonGamesResults(Array[Team](bulls, lakers), 2016)

    bullsAndLakersGames.size should equal (2)
    bullsAndLakersGames(bulls).length should equal (3)
    bullsAndLakersGames(lakers).length should equal (2)
  }

  "GameFactory.getSeasonGamesForTeam" should "return an array containing matches played by bulls" in {
    val bulls :Team = new Team("Chicago Bulls", "http://espn.go.com/nba/team/_/name/chi/chicago-bulls", "chi", "chicago-bulls")

    val gameFactory = new GameFactory(StubGamesDocumentProvider)
    val bullsGames = gameFactory.getSeasonGamesResultsForTeam(bulls, 2016)

    bullsGames(0).toString shouldBe "matchId: 400578302, date: 2015-10-29 | NY Knicks [80] - [104] Chicago Bulls"
    bullsGames(1).toString shouldBe "matchId: 400578314, date: 2015-10-31 | Cleveland [108] - [114] Chicago Bulls"
    bullsGames(2).toString shouldBe "matchId: 400578327, date: 2015-11-01 | Minnesota [105] - [106] Chicago Bulls"
  }
}
