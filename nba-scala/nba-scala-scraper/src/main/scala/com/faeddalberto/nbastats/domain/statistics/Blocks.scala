package com.faeddalberto.nbastats.domain.statistics

class Blocks(val blocks :Int) extends Stat{

  override def toString = s"BLK: $blocks"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Blocks]

  override def equals(other: Any): Boolean = other match {
    case that: Blocks =>
      (that canEqual this) &&
        blocks == that.blocks
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(blocks)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
