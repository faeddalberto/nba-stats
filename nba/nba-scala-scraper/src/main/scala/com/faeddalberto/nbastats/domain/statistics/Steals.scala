package com.faeddalberto.nbastats.domain.statistics

class Steals(val steals :Int) extends Stat{

  override def toString = s"STL: $steals"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Steals]

  override def equals(other: Any): Boolean = other match {
    case that: Steals =>
      (that canEqual this) &&
        steals == that.steals
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(steals)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
