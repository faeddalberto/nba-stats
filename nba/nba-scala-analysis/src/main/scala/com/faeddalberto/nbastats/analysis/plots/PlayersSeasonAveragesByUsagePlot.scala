package com.faeddalberto.nbastats.analysis.plots

import com.faeddalberto.nbastats.analysis.dataanalyzers.PlayerStatsAnalyser.getPlayersAveragesSortedByUsageForSeason
import com.quantifind.charts.Highcharts._

object PlayersSeasonAveragesByUsagePlot {

  def visualizeTop10UsedPlayersStatsForSeason(season :Int): Unit = {
    val mostUsedPlayersStats = getPlayersAveragesSortedByUsageForSeason(season)

    val avgMinsPlayedColumn = column(mostUsedPlayersStats.map(_.averageMinutesPlayed).toList)
    hold
    val avgPointsColumn = column(mostUsedPlayersStats.map(_.averagePoints).toList)
    hold
    val avgAssistsColumn = column(mostUsedPlayersStats.map(_.averageAssists).toList)
    hold
    val avgReboundsColumn = column(mostUsedPlayersStats.map(_.averageRebounds).toList)

    title("Season Averages for Top 10 used players in the league")
    legend(List("Avg Minutes Played", "Avg Points", "Avg Assists", "Avg Rebounds"))

    xAxis("Players Stats")

    val namedColumns = avgMinsPlayedColumn.copy(xAxis = avgMinsPlayedColumn.xAxis.map {
      axisArray => axisArray.map {
        _.copy(axisType = Option("category"),
          categories = Option(mostUsedPlayersStats.map(_.playerName)))
      }
    })

    plot(namedColumns)
  }

  def main(args: Array[String]): Unit = {
    visualizeTop10UsedPlayersStatsForSeason(2016)
  }
}
