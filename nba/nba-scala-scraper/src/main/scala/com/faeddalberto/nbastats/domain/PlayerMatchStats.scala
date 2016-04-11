package com.faeddalberto.nbastats.domain

import com.faeddalberto.nbastats.domain.statistics._

class PlayerMatchStats(val player: Player, val matchId :String, val stats: Stats) {

  override def toString = s"PlayerMatchStats($player, $matchId, $stats)"

  def canEqual(other: Any): Boolean = other.isInstanceOf[PlayerMatchStats]

  override def equals(other: Any): Boolean = other match {
    case that: PlayerMatchStats =>
      (that canEqual this) &&
        player == that.player &&
        matchId == that.matchId &&
        stats == that.stats
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(player, matchId, stats)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
