package com.faeddalberto.nbastats.domain.statistics

class Points(val points :Int) extends Stat {

  override def toString = s"PTS: $points"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Points]

  override def equals(other: Any): Boolean = other match {
    case that: Points =>
      (that canEqual this) &&
        points == that.points
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(points)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
