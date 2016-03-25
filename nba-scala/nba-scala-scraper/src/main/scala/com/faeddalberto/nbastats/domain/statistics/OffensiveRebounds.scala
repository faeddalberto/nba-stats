package com.faeddalberto.nbastats.domain.statistics

class OffensiveRebounds(val offensiveRebounds: Int) extends Stat {

  override def toString = s"OREB: $offensiveRebounds"

  def canEqual(other: Any): Boolean = other.isInstanceOf[OffensiveRebounds]

  override def equals(other: Any): Boolean = other match {
    case that: OffensiveRebounds =>
      (that canEqual this) &&
        offensiveRebounds == that.offensiveRebounds
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(offensiveRebounds)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
