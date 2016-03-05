package com.faeddalberto.nbastats.domain.statistics

import com.faeddalberto.nbastats.domain.PlayerGameStats

class Stats {

  def set(statName :String, statValue :String, playerGameStats: PlayerGameStats) = statName match {
    case "min" =>
      playerGameStats minutesPlayed = new MinutesPlayed(statValue toInt)
    case "fg" =>
      val text = statValue split "-"
      playerGameStats fieldGoals = new FieldGoals().made(text(0).toInt).attempted(text(1) toInt).asInstanceOf[FieldGoals]
    case "3pt" =>
      val text = statValue split "-"
      playerGameStats threePoints = new ThreePoints().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[ThreePoints]
    case "ft" =>
      val text = statValue split "-"
      playerGameStats freeThrows = new FreeThrows().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[FreeThrows]
    case "oreb" =>
      playerGameStats offensiveRebounds = new OffensiveRebounds(statValue toInt)
    case "dreb" =>
      playerGameStats defensiveRebounds = new DefensiveRebounds(statValue toInt)
    case "reb" =>
      playerGameStats rebounds = new Rebounds(statValue toInt)
    case "ast" =>
      playerGameStats assists = new Assists(statValue toInt)
    case "stl" =>
      playerGameStats steals = new Steals(statValue toInt)
    case "blk" =>
      playerGameStats blocks = new Blocks(statValue toInt)
    case "to" =>
      playerGameStats turnovers = new Turnovers(statValue toInt)
    case "pf" =>
      playerGameStats personalFauls = new PersonalFauls(statValue toInt)
    case "plusminus" =>
      playerGameStats plusMinus = new PlusMinus(statValue toInt)
    case "pts" =>
      playerGameStats points = new Points(statValue toInt)
  }
}