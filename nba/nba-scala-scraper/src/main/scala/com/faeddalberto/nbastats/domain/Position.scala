package com.faeddalberto.nbastats.domain

object Position extends Enumeration {

    type Position = Value

    val GUARD = Value("G")
    val POINT_GUARD = Value("PG")
    val SHOOTING_GUARD = Value("SG")
    val GUARD_FOWRARD = Value("GF")

    val FORWARD = Value("F")
    val POWER_FORWARD = Value("PF")
    val SMALL_FORWARD = Value("SF")
    val FORWARD_CENTER = Value("FC")
    val CENTER = Value("C")



}
