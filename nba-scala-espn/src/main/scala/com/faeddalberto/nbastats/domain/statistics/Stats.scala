package com.faeddalberto.nbastats.domain.statistics

import com.faeddalberto.nbastats.domain.PlayerGameStats
import org.jsoup.nodes.Element

object Stats {
  def set(stat: Element, playerGameStats: PlayerGameStats) = stat.className match {
    case "min" =>
      playerGameStats minutesPlayed = new MinutesPlayed(stat.text toInt)
    case "fg" =>
      val text = stat.text split "-"
      playerGameStats fieldGoals = new FieldGoals().made(text(0).toInt).attempted(text(1) toInt).asInstanceOf[FieldGoals]
    case "3pt" =>
      val text = stat.text split "-"
      playerGameStats threePoints = new ThreePoints().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[ThreePoints]
    case "ft" =>
      val text = stat.text split "-"
      playerGameStats freeThrows = new FreeThrows().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[FreeThrows]
    case "oreb" =>
      playerGameStats offensiveRebounds = new OffensiveRebounds(stat.text toInt)
    case "dreb" =>
      playerGameStats defensiveRebounds = new DefensiveRebounds(stat.text toInt)
    case "reb" =>
      playerGameStats rebounds = new Rebounds(stat.text toInt)
    case "ast" =>
      playerGameStats assists = new Assists(stat.text toInt)
    case "stl" =>
      playerGameStats steals = new Steals(stat.text toInt)
    case "blk" =>
      playerGameStats blocks = new Blocks(stat.text toInt)
    case "to" =>
      playerGameStats turnovers = new Turnovers(stat.text toInt)
    case "pf" =>
      playerGameStats personalFauls = new PersonalFauls(stat.text toInt)
    case "plusminus" =>
      playerGameStats plusMinus = new PlusMinus(stat.text toInt)
    case "pts" =>
      playerGameStats points = new Points(stat.text toInt)
  }
}