package com.faeddalberto.nbastats.domain.statistics

abstract class MadeOutOfStat extends Stat {

  var made: Int = _
  var attempted :Int = _

  def made(made :Int) :MadeOutOfStat = {
    this.made = made
    this
  }

  def attempted(attempted :Int) :MadeOutOfStat = {
    this.attempted = attempted
    this
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[MadeOutOfStat]

  override def equals(other: Any): Boolean = other match {
    case that: MadeOutOfStat =>
      (that canEqual this) &&
        made == that.made &&
        attempted == that.attempted
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(made, attempted)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
