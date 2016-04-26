package com.faeddalberto.nbastats.scraper

import java.io.File

import com.faeddalberto.nbastats.scraper.domain.{PlayerMatchStats, Game, Team}
import com.faeddalberto.nbastats.scraper.gamefinder.GameFactory
import com.faeddalberto.nbastats.scraper.provider.HtmlDocumentProvider
import com.faeddalberto.nbastats.scraper.domain.{Team, Game}
import com.faeddalberto.nbastats.scraper.statsfinder.StatsFactory
import com.faeddalberto.nbastats.scraper.teamfinder.TeamFactory
import com.faeddalberto.nbastats.scraper.writer.{StatsWriter, GamesWriter, TeamsWriter}

import scala.collection.mutable.ArrayBuffer

object Scraper extends App {

  val year :Int = args(0) toInt
  val path :String = args(1)

  val teams = getAndSaveTeams()

  println(s"Getting $year stats")

  var games = ArrayBuffer[Game]()
  for (team <- teams) {
    println(team name)
    val gamesScraper = new GameFactory(new HtmlDocumentProvider)
    val teamSeasonGames = gamesScraper.getSeasonGamesResultsForTeam(team, year)
    saveTeamGames (team name, teamSeasonGames)

    games ++= teamSeasonGames
  }

  val playersMatchesStats = ArrayBuffer[PlayerMatchStats]()
  for (game <- games) {

    val fileName = game.matchId + "_stats.csv"
    if (!alreadySaved(path + "/games-stats", fileName)) {
      println(game matchId)
      val statsScraper = new StatsFactory(new HtmlDocumentProvider)
      val playersStats = statsScraper.getGameStats(game)
      savePlayersMatchesStats(game.matchId, playersStats)
      playersMatchesStats ++= playersStats
    }
  }

  def getAndSaveTeams() :ArrayBuffer[Team] = {
    val teamsScraper = new TeamFactory(new HtmlDocumentProvider)
    val teams = teamsScraper getAllTeams

    new TeamsWriter writeToFile (teams, path + "/teams")

    teams
  }

  def saveTeamGames(team :String, games :ArrayBuffer[Game]) = {
    new GamesWriter writeToFile(team, year, games, path + "/games")
  }

  def alreadySaved(path :String, fileName :String) :Boolean = {
    val file = new File(path, fileName)
    if (file exists) true else false
  }

  def savePlayersMatchesStats(matchId :String, playersStatsPerGame :ArrayBuffer[PlayerMatchStats]) = {
    new StatsWriter writeToFile(matchId, playersStatsPerGame, path + "/games-stats")
  }
}
