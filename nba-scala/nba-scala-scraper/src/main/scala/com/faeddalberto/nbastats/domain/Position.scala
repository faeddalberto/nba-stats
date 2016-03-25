package com.faeddalberto.nbastats.domain

object Position extends Enumeration {

    type Position = Value

    val POINT_GUARD = Value(1, "PG")
    val SHOOTING_GUARD = Value(2, "SG")
    val SMALL_FORWARD = Value(3, "SF")
    val POWER_FORWARD = Value(4, "PF")
    val CENTER = Value(5, "C")

}
