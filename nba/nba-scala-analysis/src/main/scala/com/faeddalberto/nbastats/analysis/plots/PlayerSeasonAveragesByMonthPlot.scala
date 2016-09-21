package com.faeddalberto.nbastats.analysis.plots

import com.faeddalberto.nbastats.analysis.dataanalyzers.PlayerStatsAnalyser.getPlayerAveragesThroughoutSeasonByMonth
import com.quantifind.charts.Highcharts._

object PlayerSeasonAveragesByMonthPlot {

  def visualizePlayerAveragesByMonth(teamName :String, playerName :String, season :Int) = {
    val seasonPlayerAveragesPerMonth = getPlayerAveragesThroughoutSeasonByMonth(teamName, playerName, season)

    val months = seasonPlayerAveragesPerMonth.map(stats => stats.month).toList

    line(months, seasonPlayerAveragesPerMonth.map(stats => stats.averagePoints).toList)
    hold
    line(months, seasonPlayerAveragesPerMonth.map(stats => stats.averageAssists).toList)
    hold
    line(months, seasonPlayerAveragesPerMonth.map(stats => stats.averageRebounds).toList)
    hold
    line(months, seasonPlayerAveragesPerMonth.map(stats => stats.averageMinutesPlayed).toList)

    title("Player Season Stats by month")
    yAxis(seasonPlayerAveragesPerMonth(0).playerName)
    xAxis("Month")
    legend(List("Points", "Assists","Rebounds", "Mins Played"))
  }

  def main(args: Array[String]): Unit = {
    //visualizePlayerAveragesByMonth("Oklahoma City Thunder", "Russell Westbrook", 2016)

    //visualizePlayerAveragesByMonth("Cleveland Cavaliers", "Kyrie Irving", 2016)
    visualizePlayerAveragesByMonth("Cleveland Cavaliers", "LeBron James", 2016)
    //visualizePlayerAveragesByMonth("Oklahoma City Thunder", "Kevin Durant", 2016)
    //visualizePlayerAveragesByMonth("Golden State Warriors", "Stephen Curry", 2016)
    //visualizePlayerAveragesByMonth("Golden State Warriors", "Klay Thompson", 2016)
  }
}
