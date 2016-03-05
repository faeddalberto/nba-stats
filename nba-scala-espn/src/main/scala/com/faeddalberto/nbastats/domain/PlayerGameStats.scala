package com.faeddalberto.nbastats.domain

import com.faeddalberto.nbastats.domain.statistics._

case class PlayerGameStats(matchId :String) {

  var player :Player = _

  var minutesPlayed :MinutesPlayed = _

  var fieldGoals :FieldGoals = _

  var threePoints :ThreePoints = _

  var freeThrows :FreeThrows = _

  var offensiveRebounds :OffensiveRebounds = _

  var defensiveRebounds :DefensiveRebounds = _

  var rebounds :Rebounds = _

  var assists :Assists = _

  var steals :Steals = _

  var blocks :Blocks = _

  var turnovers :Turnovers = _

  var personalFauls :PersonalFauls = _

  var plusMinus :PlusMinus = _

  var points :Points = _

  override def toString =
    s"$player \n $minutesPlayed \n $fieldGoals \n $threePoints \n $freeThrows \n $offensiveRebounds" +
    s" \n $defensiveRebounds \n $rebounds \n $assists \n $steals \n $blocks \n $turnovers \n" +
    s" $personalFauls \n $plusMinus \n $points"

  def didNotPlay(didNotPlay :PlayerGameStats) :PlayerGameStats = {
    didNotPlay minutesPlayed = new MinutesPlayed(0)
    didNotPlay fieldGoals = new FieldGoals().made(0).attempted(0).asInstanceOf[FieldGoals]
    didNotPlay threePoints = new ThreePoints().made(0).attempted(0).asInstanceOf[ThreePoints]
    didNotPlay freeThrows = new FreeThrows().made(0).attempted(0).asInstanceOf[FreeThrows]
    didNotPlay offensiveRebounds = new OffensiveRebounds(0)
    didNotPlay defensiveRebounds = new DefensiveRebounds(0)
    didNotPlay rebounds = new Rebounds(0)
    didNotPlay assists = new Assists(0)
    didNotPlay steals = new Steals(0)
    didNotPlay blocks = new Blocks(0)
    didNotPlay turnovers = new Turnovers(0)
    didNotPlay personalFauls = new PersonalFauls(0)
    didNotPlay plusMinus = new PlusMinus(0)
    didNotPlay points = new Points(0)

    didNotPlay
  }
}
