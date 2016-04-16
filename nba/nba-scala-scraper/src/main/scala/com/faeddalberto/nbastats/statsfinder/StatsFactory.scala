package com.faeddalberto.nbastats.statsfinder

import com.faeddalberto.nbastats.domain.statistics._
import com.faeddalberto.nbastats.domain.{Position, Game, Player, PlayerMatchStats}
import com.faeddalberto.nbastats.playerfinder.PlayerFactory
import com.faeddalberto.nbastats.provider.{HtmlDocumentProvider, DocumentProvider}
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class StatsFactory(documentProvider: DocumentProvider) {

  private val team_stats = "TEAM"
  private val player_name = "name"
  private val did_not_play = "dnp"
  private val player_stats = "td"
  private val players_stats = "tr"

  private val base_url = "http://espn.go.com/nba/boxscore?gameId=%s"
  private val playerfactory = new PlayerFactory(new HtmlDocumentProvider())

  def getGamesStats(games :Array[Game]) :mutable.Map[Game, ArrayBuffer[PlayerMatchStats]] = {
    val allGamesStats = mutable.Map[Game, ArrayBuffer[PlayerMatchStats]]()

    for (aGame <- games) {
      allGamesStats(aGame) = getGameStats(aGame)
    }

    allGamesStats
  }

  def getGameStats(aGame :Game) :ArrayBuffer[PlayerMatchStats] = {
    val gameStats = ArrayBuffer[PlayerMatchStats]()
    val doc :Document = documentProvider.provideDocument(base_url format aGame.matchId)

    val table = doc select "table.mod-data"
    val bodies = table select "tbody"
    val teamNames = doc select "div.table-caption"
    val homeTeam = getTeam(teamNames, 0)
    val visitorTeam = getTeam(teamNames, 1)

    for (i <- 0 to 3) {
      val team = if (i == 0 || i == 1) homeTeam  else visitorTeam
      gameStats ++= playersStatsByTeam(bodies.get(i) select players_stats, aGame, team)
    }

    gameStats
  }

  private def playersStatsByTeam(stats :Elements, game :Game, team :String) :ArrayBuffer[PlayerMatchStats] = {
    val statsIt = stats iterator
    var playersGameStats = ArrayBuffer[PlayerMatchStats]()
    breakable {
      while(statsIt hasNext) {
        playersGameStats += playerStats(statsIt.next select player_stats, game, team)
      }
    }
    playersGameStats
  }

  private def playerStats(dataElems :Elements, game: Game, team :String) :PlayerMatchStats = {
    val statsIt = dataElems iterator
    var player :Player = null
    var stats :Stats = Stats.init()
    var playerGameStats :PlayerMatchStats = null
    while (statsIt hasNext) {
      val stat = statsIt next

      if (stat.text equals team_stats) break

      if (stat.className equals player_name) {
        player = getPlayer(stat, team, game.season)
      } else if (stat.className.equals(did_not_play) || stat.text().startsWith("--")) {
        stats = Stats didNotPlay
      } else {
        stats set(stat className, stat text)
      }

      playerGameStats = new PlayerMatchStats(player, game.matchId, stats)
    }

    playerGameStats
  }

  private def getPlayer(td :Element, team :String, season :Int) :Player = {
    val a = td select "a"
    var position = td.select("span") text
    val playerId = a.attr("href").split("/").last toInt
    val name = a text

    new Player(playerId, season, name, team, Position.withName(position))
  }

  private def getTeam(teamNames: Elements, team :Int) :String = {
    teamNames.get(team).text().split(" ") (0)
  }
}
