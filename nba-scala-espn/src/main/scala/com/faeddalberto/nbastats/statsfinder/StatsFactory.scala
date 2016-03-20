package com.faeddalberto.nbastats.statsfinder

import com.faeddalberto.nbastats.domain.statistics._
import com.faeddalberto.nbastats.domain.{Position, Game, Player, PlayerGameStats}
import com.faeddalberto.nbastats.provider.DocumentProvider
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class StatsFactory(documentProvider: DocumentProvider) {

  val team_stats = "TEAM"
  val player_name = "name"
  val did_not_play = "dnp"
  val player_stats = "td"
  val players_stats = "tr"
  val base_url = "http://espn.go.com/nba/boxscore?gameId=%s"
  val stats = new Stats()

  def getGamesStats(games :Array[Game]) :mutable.Map[Game, ArrayBuffer[PlayerGameStats]] = {
    val allGamesStats = mutable.Map[Game, ArrayBuffer[PlayerGameStats]]()

    for (aGame <- games) {
      allGamesStats(aGame) = getGameStats(aGame)
    }

    allGamesStats
  }

  def getGameStats(aGame :Game) :ArrayBuffer[PlayerGameStats] = {
    val gameStats = ArrayBuffer[PlayerGameStats]()
    val doc :Document = documentProvider.provideDocument(base_url format aGame.matchId)

    val table = doc select "table.mod-data"
    val bodies = table select "tbody"

    for (i <- 0 to 3) {
      val team = if (i == 0 || i == 1) aGame.visitTeam else aGame.homeTeam
      gameStats ++= playersStatsByTeam(bodies.get(i) select players_stats, aGame.matchId, team)
    }

    gameStats
  }

  private def playersStatsByTeam(stats :Elements, matchId :String, team :String) :ArrayBuffer[PlayerGameStats] = {
    val statsIt = stats iterator
    var playersGameStats = ArrayBuffer[PlayerGameStats]()
    breakable {
      while(statsIt hasNext) {
        playersGameStats += playerStats(statsIt.next select player_stats, matchId, team)
      }
    }
    playersGameStats
  }

  private def playerStats(dataElems :Elements, matchId: String, team :String) :PlayerGameStats = {
    val dataIt = dataElems iterator
    val playerGameStats = new PlayerGameStats(matchId)
    while (dataIt hasNext) {
      val data = dataIt next

      if (data.text equals team_stats) break

      if (data.className equals player_name) {
        playerGameStats player = getPlayer(data, team)
      } else if (data.className equals did_not_play) {
        playerGameStats didNotPlay _
      } else {
        val stat = (data.className, data.text)
        stats set(stat._1, stat._2, playerGameStats)
      }

    }
    playerGameStats
  }

  private def getPlayer(td :Element, team :String) :Player = {
    val a = td select "a"
    val position = td.select("span") text
    val playerId = a.attr("href").split("/").last toInt
    val name = a text

    new Player(playerId, name, team, Position.withName(position))
  }
}
