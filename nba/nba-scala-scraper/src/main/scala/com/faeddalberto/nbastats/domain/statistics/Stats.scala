package com.faeddalberto.nbastats.domain.statistics

class Stats private (var minutesPlayed :MinutesPlayed,
                      var fieldGoals :FieldGoals,
                      var threePoints :ThreePoints,
                      var freeThrows :FreeThrows,
                      var offensiveRebounds :OffensiveRebounds,
                      var defensiveRebounds :DefensiveRebounds,
                      var rebounds :Rebounds,
                      var assists :Assists,
                      var steals :Steals,
                      var blocks :Blocks,
                      var turnovers :Turnovers,
                      var personalFauls :PersonalFauls,
                      var plusMinus: PlusMinus,
                      var points: Points
                      ) {



    def set(statName :String, statValue :String) = statName match {
    case "min" =>
      minutesPlayed = new MinutesPlayed(statValue toInt)
    case "fg" =>
      val text = statValue split "-"
      fieldGoals = new FieldGoals().made(text(0).toInt).attempted(text(1) toInt).asInstanceOf[FieldGoals]
    case "3pt" =>
      val text = statValue split "-"
      threePoints = new ThreePoints().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[ThreePoints]
    case "ft" =>
      val text = statValue split "-"
      freeThrows = new FreeThrows().made(text(0) toInt).attempted(text(1) toInt).asInstanceOf[FreeThrows]
    case "oreb" =>
      offensiveRebounds = new OffensiveRebounds(statValue toInt)
    case "dreb" =>
      defensiveRebounds = new DefensiveRebounds(statValue toInt)
    case "reb" =>
      rebounds = new Rebounds(statValue toInt)
    case "ast" =>
      assists = new Assists(statValue toInt)
    case "stl" =>
      steals = new Steals(statValue toInt)
    case "blk" =>
      blocks = new Blocks(statValue toInt)
    case "to" =>
      turnovers = new Turnovers(statValue toInt)
    case "pf" =>
      personalFauls = new PersonalFauls(statValue toInt)
    case "plusminus" =>
      plusMinus = new PlusMinus(statValue toInt)
    case "pts" =>
      points = new Points(statValue toInt)
  }

}

object Stats {

  def init() :Stats = {
    val minutesPlayed = new MinutesPlayed(0)
    val fieldGoals = new FieldGoals().made(0).attempted(0).asInstanceOf[FieldGoals]
    val threePoints = new ThreePoints().made(0).attempted(0).asInstanceOf[ThreePoints]
    val freeThrows = new FreeThrows().made(0).attempted(0).asInstanceOf[FreeThrows]
    val offensiveRebounds = new OffensiveRebounds(0)
    val defensiveRebounds = new DefensiveRebounds(0)
    val rebounds = new Rebounds(0)
    val assists = new Assists(0)
    val steals = new Steals(0)
    val blocks = new Blocks(0)
    val turnovers = new Turnovers(0)
    val personalFauls = new PersonalFauls(0)
    val plusMinus = new PlusMinus(0)
    val points = new Points(0)

    new Stats(minutesPlayed, fieldGoals, threePoints, freeThrows, offensiveRebounds, defensiveRebounds, rebounds,
      assists, steals, blocks, turnovers, personalFauls, plusMinus, points)
  }


  def didNotPlay :Stats = {
    init()
  }


}