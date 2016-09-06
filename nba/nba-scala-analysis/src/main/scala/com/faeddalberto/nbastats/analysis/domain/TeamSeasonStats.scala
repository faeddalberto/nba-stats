package com.faeddalberto.nbastats.analysis.domain


case class TeamSeasonStats(team :String, season :Int, won :Long, lost :Long, winPercentage :Double, homeWins :Long, awayWins :Long)
