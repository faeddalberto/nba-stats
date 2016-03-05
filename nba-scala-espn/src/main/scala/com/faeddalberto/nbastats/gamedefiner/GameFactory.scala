package com.faeddalberto.nbastats.gamedefiner

import com.faeddalberto.nbastats.domain.{Game, Team}
import com.faeddalberto.nbastats.provider.DocumentProvider
import org.joda.time.LocalDate
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class GameFactory(documentProvider :DocumentProvider) {

  val table_odd_and_even_rows = "tr[class~=(?i)(oddrow|evenrow)]"
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
    val doc :Document = documentProvider.provideDocument(base_url format (team.prefix_1, year, team.prefix_2))

    breakable {
      val rows = doc select table_odd_and_even_rows iterator

      while (rows hasNext) {
        val row = rows next
        val data = row select "td"

        val date = getGameDate(year, data)
        if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) break

        val isHomeTeam = if (text(data, 1, 0) equals "vs") true else false
        val otherTeamName = text(data, 1, 2)
        val score = text(data, 2, 1).split(" ")(0)
        val matchId = data.get(2).select("ul li").get(1).select("a").attr("href").split("id=")(1)
        val won = if (text(data, 2, 0) equals "W") true else false

        teamGames += getGame(matchId, date, isHomeTeam, team.name, otherTeamName, score, won)
      }
    }
    teamGames
  }

  private def getGame(matchId :String, date :LocalDate, isHomeTeam :Boolean, mainTeamName :String,
              otherTeamName :String, score :String, won :Boolean) :Game = {
    var homeTeamName :String = null
    var homeTeamScore :Int = 0
    var visitTeamName :String = null
    var visitTeamScore :Int = 0

    val scoreSplit = score split "-"

    if (isHomeTeam) {
      if (won) {
        homeTeamName = mainTeamName
        visitTeamName = otherTeamName
        homeTeamScore = scoreSplit(0).toInt
        visitTeamScore = scoreSplit(1).toInt
      } else {
        homeTeamName = otherTeamName
        visitTeamName = mainTeamName
        homeTeamScore = scoreSplit(1).toInt
        visitTeamScore = scoreSplit(0).toInt
      }
    } else {
      if (won) {
        homeTeamName = otherTeamName
        visitTeamName = mainTeamName
        homeTeamScore = scoreSplit(1).toInt
        visitTeamScore = scoreSplit(0).toInt
      } else {
        homeTeamName = mainTeamName
        visitTeamName = otherTeamName
        homeTeamScore = scoreSplit(0).toInt
        visitTeamScore = scoreSplit(1).toInt
      }
    }

    new Game(matchId, date, homeTeamName, homeTeamScore, visitTeamName, visitTeamScore)
  }

  private def text(columns :Elements, colIndex :Int, listIndex :Int) :String = {
    columns.get(colIndex).select("ul li").get(listIndex).text
  }

  private def getGameDate(year :Int, columns :Elements) :LocalDate = {
    var date = dtf.parseLocalDate(year + columns.get(0).text().split(",")(1))
    val newYear = (date.monthOfYear().get >= 10 ) && (date.monthOfYear().get <= 12)
    if (newYear) date = date.minusYears(1)
    date
  }
}
