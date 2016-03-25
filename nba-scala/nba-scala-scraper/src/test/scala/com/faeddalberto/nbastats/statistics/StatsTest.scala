package com.faeddalberto.nbastats.statistics

import com.faeddalberto.nbastats.domain.statistics._
import com.faeddalberto.nbastats.domain.{Position, Player, PlayerMatchStats}
import org.scalatest.{FlatSpec, Matchers}

class StatsTest extends FlatSpec with Matchers{

  "Stats.set" should "set stats values to given PlayerGameStat instance object" in {

    val mins = ("min","26")
    val reb = ("reb", "6")
    val points = ("pts", "27")
    val fieldGoals = ("fg", "8-11")
    val threePoints = ("3pt", "3-5")
    val freeThrows = ("ft", "2-4")

    val player = new Player(id = 234543, name = "D. Rose", team = "Chicago Bulls", role = Position.withName("PG"))

    val stats = Stats.init()
    stats.set(mins _1, mins _2)
    stats.set(reb _1, reb _2)
    stats.set(points _1, points _2)
    stats.set(fieldGoals _1, fieldGoals _2)
    stats.set(threePoints _1, threePoints _2)
    stats.set(freeThrows _1, freeThrows _2)

    var playerStats = new PlayerMatchStats(player, "23432", stats)

    playerStats.stats. fieldGoals shouldEqual new FieldGoals().made(8).attempted(11)
    playerStats.stats. minutesPlayed shouldEqual new MinutesPlayed(26)
    playerStats.stats. rebounds shouldEqual new Rebounds(6)
    playerStats.stats. points shouldEqual new Points(27)
    playerStats.stats. threePoints shouldEqual new ThreePoints().made(3).attempted(5)
    playerStats.stats. freeThrows shouldEqual new FreeThrows().made(2).attempted(4)
  }
}

