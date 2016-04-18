package com.faeddalberto.nbastats.domain

import org.joda.time.LocalDate


class PlayerBio private(val id :Int, val fullName :String, val dob :LocalDate, val country :String, val drafted :String) {

  override def toString = s"Name: $fullName, Born: $dob in $country. Drafted in $drafted"
}

object PlayerBio {

  def apply(id :Int, fullName :String, dob :LocalDate, country :String, drafted :String) :PlayerBio = {
    new PlayerBio(id, fullName, dob, country, drafted)
  }
}