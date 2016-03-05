package com.faeddalberto.nbastats.domain

class Player (val id :Int, val name :String, val team :String, val role :String) {

  override def toString = {
    s"Id: $id, Name: $name, Team: $team, Role: $role"
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Player]

  override def equals(other: Any): Boolean = other match {
    case that: Player =>
      (that canEqual this) &&
        id == that.id &&
        name == that.name &&
        team == that.team &&
        role == that.role
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, name, team, role)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
