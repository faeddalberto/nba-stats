package com.faeddalberto.nbastats.analysis.domain

case class PlayerStatsByUsage(season :Int, playerName :String, gamesPlayed :Long, averagePoints :Double,
                              averageAssists :Double, averageRebounds :Double, averageMinutesPlayed :Double)
