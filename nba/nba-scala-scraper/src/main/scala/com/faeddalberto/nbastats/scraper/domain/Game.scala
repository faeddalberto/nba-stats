package com.faeddalberto.nbastats.scraper.domain

import org.joda.time.LocalDate

class Game private(val matchId :String, val season :Int, val seasonType :String, val date :LocalDate,
                    val homeTeam :String, val homeTeamScore :Int,
                    val visitTeam :String, val visitTeamScore :Int) {

  override def toString = {
    s"matchId: $matchId, season: $season $seasonType [$date] | $homeTeam [$homeTeamScore] - [$visitTeamScore] $visitTeam"
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Game]

  override def equals(other: Any): Boolean = other match {
    case that: Game =>
      (that canEqual this) &&
        matchId == that.matchId &&
        season == that.season &&
        seasonType == that.seasonType &&
        date == that.date &&
        homeTeam == that.homeTeam &&
        homeTeamScore == that.homeTeamScore &&
        visitTeam == that.visitTeam &&
        visitTeamScore == that.visitTeamScore
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(matchId, season, seasonType, date, homeTeam, homeTeamScore, visitTeam, visitTeamScore)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object Game {

    def apply(matchId :String, season :Int, seasonType :String, date :LocalDate,
                homeTeam :String, homeTeamScore :Int,
                visitTeam :String, visitTeamScore :Int) :Game = {
      new Game(matchId, season, seasonType, date, homeTeam, homeTeamScore, visitTeam, visitTeamScore)
    }

    def apply(matchId :String, season :Int, seasonType :String, date :LocalDate, isHomeTeam :Boolean, mainTeamName :String,
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

    new Game(matchId, season, seasonType, date, homeTeamName, homeTeamScore, visitTeamName, visitTeamScore)
  }
}