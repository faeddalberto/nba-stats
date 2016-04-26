package com.faeddalberto.nbastats.scraper.domain.statistics

class Rebounds(val rebounds :Int) extends Stat {

  override def toString = s"REB: $rebounds"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Rebounds]

  override def equals(other: Any): Boolean = other match {
    case that: Rebounds =>
      (that canEqual this) &&
        rebounds == that.rebounds
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(rebounds)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
