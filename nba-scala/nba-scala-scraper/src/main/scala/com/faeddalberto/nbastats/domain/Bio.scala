package com.faeddalberto.nbastats.domain

import org.joda.time.LocalDate


class Bio private(val dob :LocalDate, val country :String, val height :String, val weight :String, val drafted :String) {

  override def toString = s"Bio(Born : $dob in $country\n height=$height, weight=$weight, drafted=$drafted)"
}

object Bio {

  def apply(dob :LocalDate, country :String, height :String, weight :String, drafted :String) :Bio = {
    new Bio(dob, country, height, weight, drafted)
  }
}