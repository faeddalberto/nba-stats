package com.faeddalberto.nbastats.scraper.writer

import java.io.{FileWriter, BufferedWriter, File}

import com.faeddalberto.nbastats.scraper.domain.PlayerMatchStats

import scala.collection.mutable.ArrayBuffer

class StatsWriter {

  private val new_line = "\n"
  private val separator = ","

  private val header = "match id, player id, season, name, team, role, minutes played, field goals, three points, free throws, offensive rebounds, defensive rebounds, rebounds, assists, steals, blocks, turnovers, personal fauls, plus/minus, points"

  def writeToFile(matchId :String, playersMatchStats :ArrayBuffer[PlayerMatchStats], path :String) = {

    val file = new File(path, s"$matchId"+"_stats.csv")

    if (!file. exists) {
      val writer = new BufferedWriter(new FileWriter(file))
      writer write header + new_line

      for (playerStats <- playersMatchStats) {
        val matchId = playerStats matchId
        val playerId = playerStats.player.id
        val season = playerStats.player.season
        val playerName = playerStats.player.name
        val playerTeam = playerStats.player.team
        val playerRole = playerStats.player.role
        val minutesPlayed = playerStats.stats.minutesPlayed.minutesPlayed
        val fg = playerStats.stats.fieldGoals.attempted + "-" + playerStats.stats.fieldGoals.made
        val tps = playerStats.stats.threePoints.attempted + "-" + playerStats.stats.threePoints.made
        val ft = playerStats.stats.freeThrows.attempted + "-" + playerStats.stats.freeThrows.made
        val ofr = playerStats.stats.offensiveRebounds.offensiveRebounds
        val der = playerStats.stats.defensiveRebounds.defensiveRebounds
        val reb = playerStats.stats.rebounds.rebounds
        val ass = playerStats.stats.assists.assists
        val stl = playerStats.stats.steals.steals
        val blk = playerStats.stats.blocks.blocks
        val to = playerStats.stats.turnovers.turnovers
        val pf = playerStats.stats.personalFauls.personalFauls
        val pm = playerStats.stats.plusMinus.plusMinus
        val pts = playerStats.stats.points.points

        writer write (matchId + separator + playerId + separator + season + separator + playerName + separator + playerTeam
          + separator + playerRole + separator + minutesPlayed + separator + fg + separator + tps + separator + ft
          + separator + ofr + separator + der + separator +reb + separator + ass + separator + stl + separator + blk
          + separator + to + separator + pf + separator + pm + separator + pts + new_line)
      }
      writer close

      println (s"$matchId's stats saved successfully")
    }
  }

}
