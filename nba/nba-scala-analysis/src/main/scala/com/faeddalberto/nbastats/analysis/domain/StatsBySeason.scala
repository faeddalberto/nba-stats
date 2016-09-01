package com.faeddalberto.nbastats.analysis.domain

import com.datastax.spark.connector.mapper.JavaBeanColumnMapper
import com.faeddalberto.nbastats.model.domain.MadeAttemptedStat

case class StatsBySeason(season :Int, month :Int, playerTeam :String, playerName :String, opponentTeam :String,
                         gameId :java.util.UUID, playerId :java.util.UUID, assists:Int, blocks :Int, date :java.util.Date,
                         defensiveRebounds :Int, fieldGoals :MadeAttemptedStat, freeThrows :MadeAttemptedStat, minsPlayed :Int,
                         offensiveRebounds :Int, personalFauls :Int, plusMinus :Int, points :Int, steals :Int, totalRebounds :Int,
                         turnovers :Int, threePoints :MadeAttemptedStat)

object StatsBySeason {
  implicit object Mapper extends JavaBeanColumnMapper[StatsBySeason](
    Map("season" -> "season",
      "month" -> "month",
      "playerTeam" -> "player_team",
      "playerName" -> "player_name",
      "opponentTeam" -> "opponent_team",
      "gameId" -> "game_id",
      "playerId" -> "player_id",
      "assists" -> "ast",
      "blocks" -> "bks",
      "date" -> "date",
      "defensiveRebounds" -> "def_reb",
      "fieldGoals" -> "fg_ma",
      "freeThrows" -> "ft_ma",
      "minsPlayed" -> "mins_played",
      "offensiveRebounds" -> "off_reb",
      "personalFauls" -> "pf",
      "plusMinus" -> "pm",
      "points" -> "pts",
      "steals" -> "stl",
      "totalRebounds" -> "tot_reb",
      "turnovers" -> "tov",
      "threePoints" -> "tp_ma")
  )
}