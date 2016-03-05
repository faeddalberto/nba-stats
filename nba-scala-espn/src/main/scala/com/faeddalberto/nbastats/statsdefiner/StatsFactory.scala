package com.faeddalberto.nbastats.statsdefiner

import com.faeddalberto.nbastats.domain.statistics._
import com.faeddalberto.nbastats.domain.{Game, Player, PlayerGameStats}
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

  def getGamesStats(games :Array[Game]) :mutable.Map[Game, ArrayBuffer[PlayerGameStats]] = {
    val allGamesStats = mutable.Map[Game, ArrayBuffer[PlayerGameStats]]()

    for (forGame <- games) {
      allGamesStats(forGame) = getGameStats(forGame)
    }

    allGamesStats
  }

  def getGameStats(forGame :Game) :ArrayBuffer[PlayerGameStats] = {
    val gameStats = ArrayBuffer[PlayerGameStats]()
    val doc :Document = documentProvider.provideDocument(base_url format forGame.matchId)

    val table = doc select "table.mod-data"
    val bodies = table select "tbody"

    for (i <- 0 to 3) {
      val team = if (i == 0 || i == 1) forGame.visitTeam else forGame.homeTeam
      gameStats ++= playersStatsByTeam(bodies.get(i) select players_stats, forGame.matchId, team)
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
        statsData(data, playerGameStats)
      }

    }
    playerGameStats
  }

  private def statsData(choice :Element, playerGameStats: PlayerGameStats) :Unit = choice.className() match {
    case "min" =>
      val min = new MinutesPlayed(choice.text toInt)
      playerGameStats minutesPlayed = min
    case "fg" =>
        val text = choice.text split "-"
        val fg = new FieldGoals().made(text(0).toInt).attempted(text(1) toInt).asInstanceOf[FieldGoals]
      playerGameStats fieldGoals = fg
    case "3pt" =>
        val text = choice.text split "-"
        val threePts = new ThreePoints().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[ThreePoints]
      playerGameStats threePoints = threePts
    case "ft" =>
        val text = choice.text split "-"
        val ft = new FreeThrows().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[FreeThrows]
      playerGameStats freeThrows = ft
    case "oreb" =>
        val oreb = new OffensiveRebounds(choice.text toInt)
      playerGameStats offensiveRebounds = oreb
    case "dreb" =>
        val dreb = new DefensiveRebounds(choice.text toInt)
      playerGameStats defensiveRebounds = dreb
    case "reb" =>
        val reb = new Rebounds(choice.text toInt)
      playerGameStats rebounds = reb
    case "ast" =>
        val ast = new Assists(choice.text toInt)
      playerGameStats assists = ast
    case "stl" =>
        val stl = new Steals(choice.text toInt)
      playerGameStats steals = stl
    case "blk" =>
        val blk = new Blocks(choice.text toInt)
      playerGameStats blocks = blk
    case "to" =>
        val to = new Turnovers(choice.text toInt)
      playerGameStats turnovers = to
    case "pf" =>
        val pf = new PersonalFauls(choice.text toInt)
      playerGameStats personalFauls = pf
    case "plusminus" =>
        val pm = new PlusMinus(choice.text toInt)
      playerGameStats plusMinus = pm
    case "pts" =>
        val pts = new Points(choice.text toInt)
      playerGameStats points = pts
  }

  private def getPlayer(td :Element, team :String) :Player = {
    val a = td select "a"
    val position = td.select("span") text
    val playerId = a.attr("href").split("/").last toInt
    val name = a text

    new Player(playerId, name, team, position)
  }
}
