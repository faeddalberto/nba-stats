package com.faeddalberto.nbastats.analysis.domain

case class PlayerStatsByMonth(season :Int, month :Int, playerTeam :String, playerName :String, gamesPlayed :Long,
                              averagePoints :Double, averageAssists :Double, averageRebounds :Double, averageMinutesPlayed :Double)
