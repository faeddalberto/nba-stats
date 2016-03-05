package com.faeddalberto.nbastats.domain

import org.joda.time.LocalDate

class Game(val matchId :String, val date :LocalDate,
           val homeTeam :String, val homeTeamScore :Int,
           val visitTeam :String, val visitTeamScore :Int) {

  override def toString = {
    s"matchId: $matchId, date: $date | $homeTeam [$homeTeamScore] - [$visitTeamScore] $visitTeam"
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Game]

  override def equals(other: Any): Boolean = other match {
    case that: Game =>
      (that canEqual this) &&
        matchId == that.matchId &&
        date == that.date &&
        homeTeam == that.homeTeam &&
        homeTeamScore == that.homeTeamScore &&
        visitTeam == that.visitTeam &&
        visitTeamScore == that.visitTeamScore
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(matchId, date, homeTeam, homeTeamScore, visitTeam, visitTeamScore)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
