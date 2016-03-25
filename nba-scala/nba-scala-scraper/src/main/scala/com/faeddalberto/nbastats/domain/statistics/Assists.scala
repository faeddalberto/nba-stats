package com.faeddalberto.nbastats.domain.statistics

class Assists(val assists :Int) extends Stat {

  override def toString = s"AST: $assists"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Assists]

  override def equals(other: Any): Boolean = other match {
    case that: Assists =>
      (that canEqual this) &&
        assists == that.assists
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(assists)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}