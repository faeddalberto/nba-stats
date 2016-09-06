package com.faeddalberto.nbastats.scraper.gamefinder

import com.faeddalberto.nbastats.scraper.domain.{Game, Team}
import com.faeddalberto.nbastats.scraper.provider.DocumentProvider
import org.joda.time.LocalDate
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.jsoup.select.Elements

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class GameFactory(documentProvider :DocumentProvider) {

  private val unordered_info_list = "ul li"
  private val team_games = "tr[class~=(?i)(oddrow|evenrow)]"
  private val game_data = "td"
  private val postponed = "Postponed"
  private val canceled = "Canceled"

  private val base_url_season = "http://espn.go.com/nba/team/schedule/_/name/%s/year/%d/seasontype/%d/%s";
  private val dtf :DateTimeFormatter = DateTimeFormat forPattern "yyyy MMM dd"

  def getAllTeamsSeasonGamesResults(teams :Array[Team], year :Int) :mutable.Map[Team, ArrayBuffer[Game]] = {
    val allGames = mutable.Map[Team, ArrayBuffer[Game]]()

    for (team <- teams) {
      allGames(team) = getSeasonGamesResultsForTeam(team, year)
    }
    allGames
  }

  def getSeasonGamesResultsForTeam(team :Team, year :Int) :ArrayBuffer[Game] = {
    var teamGames = ArrayBuffer[Game]()

    for (i <- 2 to 3) {
      val doc = documentProvider.provideDocument(base_url_season format(team.prefix_1, year, i, team.prefix_2))

      val seasonType :String = doc.select ("ul.ui-tabs li.active").text.split(" ")(1)

      breakable {

        if ((i == 3) && seasonType.equals("Regular")) break()

        val games = doc select team_games iterator

        while (games hasNext) {
          val game = games next
          val data = game select game_data

          val date = getGameDate(year, data)
          if (date.isEqual(today) || date.isAfter(today)) break

          val isHomeTeam = if (text(data, 1, 0) equals "vs") true else false
          val otherTeamName = text(data, 1, 2)
          if (isPostponedOrCanceled(data, 2)) {
            //skip this game
          } else {
            val score = text(data, 2, 1).split(" ")(0)
            val matchId = data.get(2).select(unordered_info_list).get(1).select("a").attr("href").split("/").last
            val won = if (text(data, 2, 0) equals "W") true else false

            teamGames += Game(matchId, year, seasonType, date, isHomeTeam, team.name, otherTeamName, score, won)
          }
        }
      }

    }
    teamGames
  }

  private def text(columns :Elements, colIndex :Int, listIndex :Int) :String = {
    columns.get(colIndex).select(unordered_info_list).get(listIndex).text
  }

  private def isPostponedOrCanceled(columns :Elements, columnIndex:Int) :Boolean = {
    columns.get(columnIndex).text.equals(postponed) || columns.get(columnIndex).text.equals(canceled)
  }

  private def getGameDate(year :Int, columns :Elements) :LocalDate = {
    var date = dtf.parseLocalDate(year + columns.get(0).text().split(",")(1))
    val newYear = (date.monthOfYear().get >= 10 ) && (date.monthOfYear().get <= 12)
    if (newYear) date = date.minusYears(1)
    date
  }

  def today :LocalDate = LocalDate.now
}
