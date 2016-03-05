package com.faeddalberto.nbastats.domain.statistics

class Turnovers(val turnovers :Int) extends Stat {

  override def toString = s"TO: $turnovers"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Turnovers]

  override def equals(other: Any): Boolean = other match {
    case that: Turnovers =>
      (that canEqual this) &&
        turnovers == that.turnovers
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(turnovers)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
