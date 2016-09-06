package com.faeddalberto.nbastats.analysis.dataanalyzers

import com.faeddalberto.nbastats.analysis.context.ContextCreator
import com.faeddalberto.nbastats.analysis.domain.TeamSeasonStats

object TeamGamesAnalyzer {

  val regularSeasonGames = 82

  def getTeamStatsBySeason(team :String, season :Int) :TeamSeasonStats = {

    val csc = ContextCreator.getCassandraSQLContext()

    val teamHomeGames = csc.sql("SELECT * from game WHERE season = " + season +
                        " AND home_team = '" + team + "' AND season_type = 'REGULAR_SEASON'").cache()
    //teamHomeGames.show()
    val teamHomeWins = teamHomeGames.filter(teamHomeGames("home_team_score") > teamHomeGames("visitor_team_score"))
    val homeWinsCount = teamHomeWins.count()
    teamHomeGames.unpersist()

    val teamAwayGames = csc.sql("SELECT * from game WHERE season = " + season +
                        " AND visitor_team = '" + team + "' AND season_type = 'REGULAR_SEASON'").cache()
    //teamAwayGames.show()
    val teamAwayWins = teamAwayGames.filter(teamAwayGames("visitor_team_score") > teamAwayGames("home_team_score"))
    val awayWinsCount = teamAwayWins.count()
    teamAwayGames.unpersist()

    //val totalWins = homeWinsCount + awayWinsCount

    //new TeamSeasonStats(team, season, totalWins, regularSeasonGames - totalWins, totalWins / regularSeasonGames, homeWinsCount, awayWinsCount)
    new TeamSeasonStats(team, season, 0, 0, 0, 0, 0)

  }

  def main(args: Array[String]) :Unit = {

    println(getTeamStatsBySeason("Cleveland Cavaliers", 2016))
  }
}
