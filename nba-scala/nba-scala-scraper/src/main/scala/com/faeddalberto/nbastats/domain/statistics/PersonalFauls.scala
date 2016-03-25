package com.faeddalberto.nbastats.domain.statistics

class PersonalFauls(val personalFauls :Int) extends Stat {

  override def toString = s"PF: $personalFauls"

  def canEqual(other: Any): Boolean = other.isInstanceOf[PersonalFauls]

  override def equals(other: Any): Boolean = other match {
    case that: PersonalFauls =>
      (that canEqual this) &&
        personalFauls == that.personalFauls
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(personalFauls)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
