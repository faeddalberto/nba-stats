package com.faeddalberto.nbastats.scraper.domain.statistics

class PlusMinus(val plusMinus :Int) extends Stat{

  override def toString = s"+/-: $plusMinus"

  def canEqual(other: Any): Boolean = other.isInstanceOf[PlusMinus]

  override def equals(other: Any): Boolean = other match {
    case that: PlusMinus =>
      (that canEqual this) &&
        plusMinus == that.plusMinus
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(plusMinus)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
