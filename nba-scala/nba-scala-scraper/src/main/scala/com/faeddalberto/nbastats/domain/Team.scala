package com.faeddalberto.nbastats.domain

class Team (val name :String, val division :String, val url :String, val prefix_1 :String, val prefix_2 :String) {

  override def toString = {
    "name: %s, division: %s, url: %s, prefix_1: %s, prefix_2: %s".format(name, division, url, prefix_1, prefix_2)
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Team]

  override def equals(other: Any): Boolean = other match {
    case that: Team =>
      (that canEqual this) &&
        name == that.name &&
        division == that.division &&
        url == that.url &&
        prefix_1 == that.prefix_1 &&
        prefix_2 == that.prefix_2
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name, division, url, prefix_1, prefix_2)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
