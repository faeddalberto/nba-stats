package com.faeddalberto.nbastats.analysis.dataanalyzers

import com.faeddalberto.nbastats.analysis.context.ContextCreator
import com.datastax.spark.connector._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.cassandra.CassandraSQLContext
import com.faeddalberto.nbastats.analysis.domain.{TeamSeasonPointsAverages, TeamSeasonStats}

object TeamStatsAnalyser {

  val REGULAR_SEASON_TOTAL_GAMES = 82

  def getTeamsHomeAveragePointsMadeConceded(season :Int) :Array[TeamSeasonPointsAverages] = {
    val csc = ContextCreator.getCassandraSQLContext()

    val gameBySeasonTeams = csc.read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("keyspace" -> "nba", "table" -> "game_by_season_teams"))
      .load()

    gameBySeasonTeams
      .filter("season = " + season)
      .groupBy("season", "home_team")
      .agg(Map("home_team_score" ->"avg", "visitor_team_score" ->"avg"))
      .withColumnRenamed("avg(home_team_score)", "average_scored_home")
      .withColumnRenamed("avg(visitor_team_score)", "average_conceded_home")
      .withColumnRenamed("home_team", "team")
      .rdd
      .map(row => new TeamSeasonPointsAverages(row.getInt(0), row.getString(1), row.getDouble(2), row.getDouble(3)))
      .collect()
  }

  def getTeamsAwayAveragePointsMadeConceded(season :Int) :Array[TeamSeasonPointsAverages] = {
    val csc = ContextCreator.getCassandraSQLContext()

    val gameBySeasonTeams = csc.read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("keyspace" -> "nba", "table" -> "game_by_season_teams"))
      .load()

    gameBySeasonTeams
      .filter("season = " + season)
      .groupBy("season", "visitor_team")
      .agg(Map("visitor_team_score" ->"avg", "home_team_score" ->"avg"))
      .withColumnRenamed("avg(home_team_score)", "average_conceded_away")
      .withColumnRenamed("avg(visitor_team_score)", "average_scored_away")
      .withColumnRenamed("visitor_team", "team")
      .rdd
      .map(row => new TeamSeasonPointsAverages(row.getInt(0), row.getString(1), row.getDouble(2), row.getDouble(3)))
      .collect()
  }

  def getTeamStatsBySeason(season :Int, team :String) : TeamSeasonStats = {

    val csc :CassandraSQLContext = ContextCreator.getCassandraSQLContext()

    val homeGamesWon = csc.sql("SELECT * FROM game_by_season_teams " +
      " WHERE season =" + season + " AND home_team = '" + team + "' AND home_team_score > visitor_team_score " +
      " AND season_type = 'REGULAR_SEASON'").cache()

    //homeGamesWon.agg(Map("home_team_score" -> "avg", "visitor_team_score" -> "avg")).show()
    homeGamesWon.orderBy("date").show()

    val homeWonCount = homeGamesWon.count()
    println("Home games won: " + homeWonCount)

    val awayGamesWon = csc.sql("SELECT * FROM game_by_season_teams " +
      " WHERE season =" + season + " AND visitor_team = '" + team + "' AND visitor_team_score > home_team_score " +
      " AND season_type = 'REGULAR_SEASON'").cache()

    awayGamesWon.orderBy("date").show()

    val awayWonCount = awayGamesWon.count()
    println("Away games won: " + awayWonCount)

    val totalWon = homeWonCount + awayWonCount
    val totalLost = REGULAR_SEASON_TOTAL_GAMES - totalWon

    val winPercentage = totalWon.toFloat / REGULAR_SEASON_TOTAL_GAMES.toFloat
    println("Win percentage: " + winPercentage)

    new TeamSeasonStats(team, season, totalWon, totalLost, winPercentage, homeWonCount, awayWonCount)
  }

  def main(args: Array[String]): Unit = {

    //getTeamStatsBySeason(2016, "Cleveland Cavaliers")

    getTeamsHomeAveragePointsMadeConceded(2016)
    getTeamsAwayAveragePointsMadeConceded(2016)

    //getTeamStatsBySeasonAndMonth("Cleveland Cavaliers", 2016, 3)
    //getTeamWinPercentagesByMonth(2016, 1)
  }

}
