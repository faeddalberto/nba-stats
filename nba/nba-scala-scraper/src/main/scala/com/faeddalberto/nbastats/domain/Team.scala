package com.faeddalberto.nbastats.domain

import com.faeddalberto.nbastats.domain.Conference.Conference
import com.faeddalberto.nbastats.domain.Division.Division

class Team (val name :String, val division :Division, val url :String, val prefix_1 :String, val prefix_2 :String) {

  val conference :Conference = Conference(division)

  override def toString = s"$name, $conference, $division, $url, $prefix_1, $prefix_2"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Team]

  override def equals(other: Any): Boolean = other match {
    case that: Team =>
      (that canEqual this) &&
        conference == that.conference &&
        name == that.name &&
        division == that.division &&
        url == that.url &&
        prefix_1 == that.prefix_1 &&
        prefix_2 == that.prefix_2
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(conference, name, division, url, prefix_1, prefix_2)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
