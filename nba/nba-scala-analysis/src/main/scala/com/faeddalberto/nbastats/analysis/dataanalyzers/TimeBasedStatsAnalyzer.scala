package com.faeddalberto.nbastats.analysis.dataanalyzers

import com.faeddalberto.nbastats.analysis.context.ContextCreator
import com.datastax.spark.connector._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.cassandra.CassandraSQLContext
import com.faeddalberto.nbastats.analysis.domain.StatsBySeason

object TimeBasedStatsAnalyser {

  def getTeamPlayerStatsBySeasonAndMonth(team :String, player :String, season :Int, month :Int) = {

    val sc = ContextCreator.getSparkContext()

    val playerStatsBySeasonAndMonth = sc.cassandraTable[StatsBySeason]("nba", "stats_by_season")
            .where("season = ?", season)
            .where("month = ?", month)
            .where("player_team = ?", team)
            .where("player_name = ?", player)

    playerStatsBySeasonAndMonth.foreach(println)
  }

  def getSeasonPerformanceByPointsHigherThen(season :Int, points :Int) : DataFrame = {

    val csc :CassandraSQLContext = ContextCreator.getCassandraSQLContext()

    val seasonStats = csc.sql("SELECT * FROM stats_by_season WHERE season =" + season + " AND pts >= " + points)

    seasonStats
  }

  def main(args: Array[String]): Unit = {
    val stats = getSeasonPerformanceByPointsHigherThen(2015, 45)
    stats.show(15)

    val playerStats = getTeamPlayerStatsBySeasonAndMonth("Cleveland Cavaliers", "Kyrie Irving", 2015, 3)
  }

}
