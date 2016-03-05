package com.faeddalberto.nbastats.domain.statistics

import com.faeddalberto.nbastats.domain.PlayerGameStats

class Stats {

}

object Stats {

  def apply(stat: String, playerGameStats: PlayerGameStats) = stat match {
    case "min" =>
      playerGameStats minutesPlayed = new MinutesPlayed(stat toInt)
    case "fg" =>
      val text = stat split "-"
      playerGameStats fieldGoals = new FieldGoals().made(text(0).toInt).attempted(text(1) toInt).asInstanceOf[FieldGoals]
    case "3pt" =>
      val text = stat split "-"
      playerGameStats threePoints = new ThreePoints().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[ThreePoints]
    case "ft" =>
      val text = stat split "-"
      playerGameStats freeThrows = new FreeThrows().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[FreeThrows]
    case "oreb" =>
      playerGameStats offensiveRebounds = new OffensiveRebounds(stat toInt)
    case "dreb" =>
      playerGameStats defensiveRebounds = new DefensiveRebounds(stat toInt)
    case "reb" =>
      playerGameStats rebounds = new Rebounds(stat toInt)
    case "ast" =>
      playerGameStats assists = new Assists(stat toInt)
    case "stl" =>
      playerGameStats steals = new Steals(stat toInt)
    case "blk" =>
      playerGameStats blocks = new Blocks(stat toInt)
    case "to" =>
      playerGameStats turnovers = new Turnovers(stat toInt)
    case "pf" =>
      playerGameStats personalFauls = new PersonalFauls(stat toInt)
    case "plusminus" =>
      playerGameStats plusMinus = new PlusMinus(stat toInt)
    case "pts" =>
      playerGameStats points = new Points(stat toInt)
  }
}