package com.faeddalberto.nbastats.analysis.domain

case class StatsByMonth(season :Int, month :Int, playerTeam :String, playerName :String, gamesPlayed :Long,
                        averagePoints :Double, averageAssists :Double, averageRebounds :Double, averageMinutesPlayed :Double)
