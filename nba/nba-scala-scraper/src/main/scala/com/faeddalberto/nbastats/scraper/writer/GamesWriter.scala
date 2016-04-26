package com.faeddalberto.nbastats.scraper.writer

import java.io.{BufferedWriter, File, FileWriter}

import com.faeddalberto.nbastats.scraper.domain.Game

import scala.collection.mutable.ArrayBuffer

class GamesWriter {

  private val new_line = "\n"
  private val separator = ","

  private val header = {
    "id, season, season type, date, home team, home team score, visit team, visit team score"
  }

  def writeToFile(team :String, year :Int, games :ArrayBuffer[Game], path :String) {

    val file = new File(path, team.replaceAll(" ", "-")+"_"+year+"_games.csv")

    if (!file. exists) {
      val writer = new BufferedWriter(new FileWriter(file))
      writer write header + new_line
      for (game <- games) {
        val id = game matchId
        val season = game season
        val season_years = season - 1 + "-" + season
        val seasonType = game seasonType
        val date = game date
        val homeTeam = game homeTeam
        val homeTeamScore = game homeTeamScore
        val visitTeam = game visitTeam
        val visitTeamScore = game visitTeamScore

        writer write (id + separator + season_years + separator + seasonType + separator + date + separator + homeTeam
          + separator + homeTeamScore + separator + visitTeam + separator + visitTeamScore + new_line)
      }
      writer close

      println (s"$team's season $year games saved successfully")
    }
  }

}
