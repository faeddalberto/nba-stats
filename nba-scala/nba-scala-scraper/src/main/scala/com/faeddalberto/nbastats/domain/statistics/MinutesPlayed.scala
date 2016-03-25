package com.faeddalberto.nbastats.domain.statistics

class MinutesPlayed(val minutesPlayed: Int) extends Stat {

  override def toString = s"MIN: $minutesPlayed"

  def canEqual(other: Any): Boolean = other.isInstanceOf[MinutesPlayed]

  override def equals(other: Any): Boolean = other match {
    case that: MinutesPlayed =>
      (that canEqual this) &&
        minutesPlayed == that.minutesPlayed
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(minutesPlayed)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
