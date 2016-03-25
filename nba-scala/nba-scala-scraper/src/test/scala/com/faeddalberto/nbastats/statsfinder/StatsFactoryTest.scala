package com.faeddalberto.nbastats.statsfinder

import com.faeddalberto.nbastats.domain.statistics._
import com.faeddalberto.nbastats.domain.{Position, Player, Game}
import com.faeddalberto.nbastats.provider.StubGameStatsDocumentProvider
import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

class StatsFactoryTest extends FlatSpec with Matchers {

  "StatsFactory.getGamesStats" should "return a map with games and stats for each player" in {
    val statsFactory = new StatsFactory(StubGameStatsDocumentProvider)
    val game :Game = Game("400578302", new LocalDate(2015,10,29), "NY Knicks", 80, "Chicago Bulls", 104)

    val stats = statsFactory.getGamesStats(Array[Game](game))

    stats.size shouldBe 1
    val playersStats =  stats(game)
    playersStats.size shouldBe 26

    val jNoah = playersStats(0)

    jNoah.player shouldEqual new Player(id = 3224, name = "J. Noah", team = "Chicago Bulls", role = Position.withName("C"))
    jNoah.stats. minutesPlayed shouldEqual new MinutesPlayed(20)
    jNoah.stats. fieldGoals shouldEqual new FieldGoals().made(1).attempted(4)
    jNoah.stats. threePoints shouldEqual new ThreePoints().made(0).attempted(0)
    jNoah.stats. freeThrows shouldEqual new FreeThrows().made(1).attempted(2)
    jNoah.stats. offensiveRebounds shouldEqual new OffensiveRebounds(2)
    jNoah.stats. defensiveRebounds shouldEqual new DefensiveRebounds(4)
    jNoah.stats. rebounds shouldEqual new Rebounds(6)
    jNoah.stats. assists shouldEqual new Assists(2)
    jNoah.stats. steals shouldEqual new Steals(0)
    jNoah.stats. blocks shouldEqual new Blocks(3)
    jNoah.stats. turnovers shouldEqual new Turnovers(2)
    jNoah.stats. personalFauls shouldEqual new PersonalFauls(2)
    jNoah.stats. plusMinus shouldEqual new PlusMinus(+3)
    jNoah.stats. points shouldEqual new Points(3)
  }

}
