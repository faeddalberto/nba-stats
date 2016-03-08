package com.faeddalberto.nbastats.gamedefiner

import com.faeddalberto.nbastats.domain.{Game, Team}
import com.faeddalberto.nbastats.provider.DocumentProvider
import org.joda.time.LocalDate
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.jsoup.select.Elements

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class GameFactory(documentProvider :DocumentProvider) {

  val unordered_info_list = "ul li"
  val team_games = "tr[class~=(?i)(oddrow|evenrow)]"
  val game_data = "td"
  val base_url = "http://espn.go.com/nba/team/schedule/_/name/%s/year/%d/%s"
  val dtf :DateTimeFormatter = DateTimeFormat forPattern "yyyy MMM dd"

  def getAllTeamsSeasonGamesResults(teams :Array[Team], year :Int) :mutable.Map[Team, ArrayBuffer[Game]] = {
    val allGames = mutable.Map[Team, ArrayBuffer[Game]]()

    for (team <- teams) {
      allGames(team) = getSeasonGamesResultsForTeam(team, year)
    }
    allGames
  }

  def getSeasonGamesResultsForTeam(team :Team, year :Int) :ArrayBuffer[Game] = {
    var teamGames = ArrayBuffer[Game]()
    val doc = documentProvider.provideDocument(base_url format (team.prefix_1, year, team.prefix_2))

    breakable {
      val games = doc select team_games iterator

      while (games hasNext) {
        val game = games next
        val data = game select game_data

        val date = getGameDate(year, data)
        if (date.isEqual(today) || date.isAfter(today)) break

        val isHomeTeam = if (text(data, 1, 0) equals "vs") true else false
        val otherTeamName = text(data, 1, 2)
        val score = text(data, 2, 1).split(" ")(0)
        val matchId = data.get(2).select(unordered_info_list).get(1).select("a").attr("href").split("id=")(1)
        val won = if (text(data, 2, 0) equals "W") true else false

        teamGames += Game.getGame(matchId, date, isHomeTeam, team.name, otherTeamName, score, won)
      }
    }
    teamGames
  }

  private def text(columns :Elements, colIndex :Int, listIndex :Int) :String = {
    columns.get(colIndex).select(unordered_info_list).get(listIndex).text
  }

  private def getGameDate(year :Int, columns :Elements) :LocalDate = {
    var date = dtf.parseLocalDate(year + columns.get(0).text().split(",")(1))
    val newYear = (date.monthOfYear().get >= 10 ) && (date.monthOfYear().get <= 12)
    if (newYear) date = date.minusYears(1)
    date
  }

  def today :LocalDate = LocalDate.now
}
