package com.faeddalberto.nbastats.analysis.plots

import com.faeddalberto.nbastats.analysis.dataanalyzers.TeamStatsAnalyser
import com.quantifind.charts.Highcharts._

import scala.collection.mutable.ListBuffer

object TeamsSeasonAveragePointsPlot {

  def visualizeAveragePointsScoredConcededHome(season :Int) = {
    val averages = TeamStatsAnalyser.getTeamsHomeAveragePointsMadeConceded(season)

    var teamNames = new ListBuffer[String]()
    for(teamAvgs <- averages) {
      scatter(Seq(teamAvgs.avgPointsScored), Seq(- teamAvgs.avgPointsConceded))

      hold

      teamNames += teamAvgs.team
    }
    xAxis("Average Points Scored")
    yAxis("Average Points Conceded")

    legend(teamNames)
    title("Team Season average points scored and conceded at home")
  }

  def visualizeAveragePointsScoredConcededAway(season :Int) = {
    val averages = TeamStatsAnalyser.getTeamsAwayAveragePointsMadeConceded(season)

    var teamNames = new ListBuffer[String]()
    for(teamAvgs <- averages) {
      scatter(Seq(teamAvgs.avgPointsScored), Seq(- teamAvgs.avgPointsConceded))

      hold

      teamNames += teamAvgs.team
    }
    xAxis("Average Points Scored")
    yAxis("Average Points Conceded")

    legend(teamNames)
    title("Team Season average points scored and conceded away")
  }

  def main(args: Array[String]): Unit = {
    visualizeAveragePointsScoredConcededHome(2016)
    //visualizeAveragePointsScoredConcededAway(2016)
  }
}
