package com.faeddalberto.nbastats.analysis.dataanalyzers

import com.faeddalberto.nbastats.analysis.context.ContextCreator
import com.faeddalberto.nbastats.analysis.dataanalyzers.TableLoader.loadTable
import com.faeddalberto.nbastats.analysis.domain.{PlayerStatsByMonth, PlayerStatsByUsage}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.cassandra.CassandraSQLContext
import org.apache.spark.sql.functions._

object PlayerStatsAnalyser {

  def getPlayersPercentagesByPosition() = {
    val statsBySeason = loadTable("nba", "stats_by_season")
    val teamRoster = loadTable("nba", "team_roster_by_year")

    statsBySeason
      .join(teamRoster, statsBySeason("player_id") === teamRoster("player_id"), "inner")
      .filter("season = 2016 AND year = 2016 AND team = 'Golden State Warriors'")
      .groupBy("role")
      .agg(Map("pts" ->"avg", "tot_reb" -> "avg", "ast" ->"avg")).show()

  }

  def getPlayersAveragesSortedByUsageForSeason(season :Int) :Array[PlayerStatsByUsage] = {

    val statsBySeason = loadTable("nba", "stats_by_season")

    statsBySeason
      .filter("season = " + season)
      .groupBy("season", "player_name")
      .agg(Map("*" -> "count", "pts" -> "avg", "ast" -> "avg", "tot_reb" -> "avg", "mins_played" -> "avg"))
      .withColumnRenamed("count(1)", "games_played")
      .withColumnRenamed("avg(pts)", "average_points")
      .withColumnRenamed("avg(ast)", "average_assists")
      .withColumnRenamed("avg(tot_reb)", "average_rebounds")
      .withColumnRenamed("avg(mins_played)", "average_minutes_played")
      .where("games_played >= 10")
      .orderBy(desc("average_minutes_played"))
      .limit(10)
      .select("season", "player_name", "games_played",
        "average_points", "average_assists", "average_rebounds", "average_minutes_played")
      .rdd
      .map(row => new PlayerStatsByUsage(
                      row.getInt(0), row.getString(1), row.getLong(2), row.getDouble(3),
                        row.getDouble(4), row.getDouble(5), row.getDouble(6)))
      .collect()
  }

  def getPlayerAveragesThroughoutSeasonByMonth(team :String, player :String, season :Int) :Array[PlayerStatsByMonth] = {

    val statsBySeason = loadTable("nba", "stats_by_season")

    statsBySeason
      .filter("season = " + season + " AND player_team = '" + team + "' AND player_name = '" + player + "'")
      .groupBy("season", "month", "player_team", "player_name")
      .agg(Map( "*" -> "count", "pts" -> "avg", "ast" -> "avg", "tot_reb" -> "avg", "mins_played" -> "avg")
          )
      .withColumnRenamed("count(1)", "games_played")
      .withColumnRenamed("avg(pts)", "average_points")
      .withColumnRenamed("avg(ast)", "average_assists")
      .withColumnRenamed("avg(tot_reb)", "average_rebounds")
      .withColumnRenamed("avg(mins_played)", "average_minutes_played")
      .orderBy("month")
      .select("season", "month", "player_team", "player_name", "games_played",
              "average_points", "average_assists", "average_rebounds", "average_minutes_played")
      .rdd
      .map(row => PlayerStatsByMonth(
                  row.getInt(0), row.getInt(1), row.getString(2), row.getString(3),
                    row.getLong(4), row.getDouble(5), row.getDouble(6), row.getDouble(7), row.getDouble(8)))
      .collect()
  }

  def getSeasonPerformancesByPointsHigherThen(season :Int, points :Int) : DataFrame = {

    val csc :CassandraSQLContext = ContextCreator.getCassandraSQLContext()

    val seasonStats = csc.sql("SELECT * FROM stats_by_season WHERE season =" + season + " AND pts >= " + points)

    seasonStats
  }

  def getSeasonPerformancesByReboundsHigherThen(season :Int, rebounds :Int) : DataFrame = {

    val csc :CassandraSQLContext = ContextCreator.getCassandraSQLContext()

    val seasonStats = csc.sql("SELECT * FROM stats_by_season WHERE season =" + season + " AND tot_reb >= " + rebounds)

    seasonStats
  }

  def getSeasonPerformancesByAssistsHigherThen(season :Int, assists :Int) : DataFrame = {

    val csc :CassandraSQLContext = ContextCreator.getCassandraSQLContext()

    val seasonStats = csc.sql("SELECT * FROM stats_by_season WHERE season =" + season + " AND ast >= " + assists)

    seasonStats
  }

  def getTripleDoublesOnPointsReboundsAssists(season :Int) : DataFrame = {

    val csc :CassandraSQLContext = ContextCreator.getCassandraSQLContext()

    val seasonStats = csc.sql("SELECT * FROM stats_by_season WHERE season =" + season + " AND pts >= 10 AND tot_reb >= 10 AND ast >= 10")

    seasonStats
  }


  def main(args: Array[String]): Unit = {
    val pointsStats = getSeasonPerformancesByPointsHigherThen(2016, 45)
    pointsStats.show(15)

    val reboundsStats = getSeasonPerformancesByReboundsHigherThen(2016, 20)
    reboundsStats.show(15)

    val assistsStats = getSeasonPerformancesByAssistsHigherThen(2016, 15)
    assistsStats.show(15)

    val tripleDoubles2016 = getTripleDoublesOnPointsReboundsAssists(2016)
    tripleDoubles2016.show(15)

    val seasonPlayerAveragesPerMonth = getPlayerAveragesThroughoutSeasonByMonth("Oklahoma City Thunder", "Russell Westbrook", 2016)
    for (playerAvgsPerMonth <- seasonPlayerAveragesPerMonth) {print(playerAvgsPerMonth + "\n")}

    getPlayersPercentagesByPosition()
  }

}
