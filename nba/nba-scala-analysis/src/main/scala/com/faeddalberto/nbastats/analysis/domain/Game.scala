package com.faeddalberto.nbastats.analysis.domain

case class Game(gameId :java.util.UUID, date :java.util.Date, homeTeam :String, homeTeamScore :Int,
                season :Int, seasonType :String, visitorTeam :String, visitorTeamScore :Int)
