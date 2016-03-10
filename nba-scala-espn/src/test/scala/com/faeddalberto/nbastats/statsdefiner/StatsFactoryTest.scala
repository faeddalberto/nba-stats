package com.faeddalberto.nbastats.statsdefiner

import com.faeddalberto.nbastats.domain.statistics._
import com.faeddalberto.nbastats.domain.{Position, Player, Game}
import com.faeddalberto.nbastats.provider.StubGameStatsDocumentProvider
import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

class StatsFactoryTest extends FlatSpec with Matchers {

  "StatsFactory.getGamesStats" should "return a map with games and stats for each player" in {
    val statsFactory = new StatsFactory(StubGameStatsDocumentProvider)
    val game :Game = Game.getGame("400578302", new LocalDate(2015,10,29), "NY Knicks", 80, "Chicago Bulls", 104)

    val stats = statsFactory.getGamesStats(Array[Game](game))

    stats.size shouldBe 1
    val playersStats =  stats(game)
    playersStats.size shouldBe 26

    val jNoah = playersStats(0)

    jNoah.player shouldEqual new Player(id = 3224, name = "J. Noah", team = "Chicago Bulls", role = Position.withName("C"))
    jNoah.minutesPlayed shouldEqual new MinutesPlayed(20)
    jNoah.fieldGoals shouldEqual new FieldGoals().made(1).attempted(4)
    jNoah.threePoints shouldEqual new ThreePoints().made(0).attempted(0)
    jNoah.freeThrows shouldEqual new FreeThrows().made(1).attempted(2)
    jNoah.offensiveRebounds shouldEqual new OffensiveRebounds(2)
    jNoah.defensiveRebounds shouldEqual new DefensiveRebounds(4)
    jNoah.rebounds shouldEqual new Rebounds(6)
    jNoah.assists shouldEqual new Assists(2)
    jNoah.steals shouldEqual new Steals(0)
    jNoah.blocks shouldEqual new Blocks(3)
    jNoah.turnovers shouldEqual new Turnovers(2)
    jNoah.personalFauls shouldEqual new PersonalFauls(2)
    jNoah.plusMinus shouldEqual new PlusMinus(+3)
    jNoah.points shouldEqual new Points(3)
  }

}
