package com.faeddalberto.nbastats.analysis.domain

case class StatsByUsage(season :Int, playerName :String, gamesPlayed :Long, averagePoints :Double,
                        averageAssists :Double, averageRebounds :Double, averageMinutesPlayed :Double)
