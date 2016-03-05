package com.faeddalberto.nbastats.domain.statistics

class DefensiveRebounds(val defensiveRebounds: Int) extends Stat {

  override def toString = s"DREB: $defensiveRebounds"

  def canEqual(other: Any): Boolean = other.isInstanceOf[DefensiveRebounds]

  override def equals(other: Any): Boolean = other match {
    case that: DefensiveRebounds =>
      (that canEqual this) &&
        defensiveRebounds == that.defensiveRebounds
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(defensiveRebounds)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
