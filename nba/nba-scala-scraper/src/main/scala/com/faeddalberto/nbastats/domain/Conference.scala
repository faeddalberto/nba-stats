package com.faeddalberto.nbastats.domain

import com.faeddalberto.nbastats.domain.Division._

object Conference {

  sealed trait Conference{val divisions :Set[Division]}

  case object EASTERN extends Conference {
    override val divisions = Set(Atlantic, Central, Southeast)
  }

  case object WESTERN extends Conference {
    override val divisions = Set(Pacific, Northwest, Soutwest)
  }

  val elements = Set(EASTERN, WESTERN)

  def apply(division :Division) :Conference = {

    if (EASTERN.divisions.contains(division))
      EASTERN
    else if (WESTERN.divisions.contains(division))
      WESTERN
    else
      throw new IllegalArgumentException("Cannot identify Conference for division " + division)

  }
}
