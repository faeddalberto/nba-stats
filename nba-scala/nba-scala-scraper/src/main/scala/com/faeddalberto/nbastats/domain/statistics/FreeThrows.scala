package com.faeddalberto.nbastats.domain.statistics

class FreeThrows extends MadeOutOfStat with Stat{

  override def toString = s"FTM-A: $made-$attempted"
}
