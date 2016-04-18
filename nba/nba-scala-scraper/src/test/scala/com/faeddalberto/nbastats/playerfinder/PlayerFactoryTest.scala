package com.faeddalberto.nbastats.playerfinder

import com.faeddalberto.nbastats.domain.PlayerBio
import com.faeddalberto.nbastats.provider.StubPlayerDocumentProvider
import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

class PlayerFactoryTest extends FlatSpec with Matchers {

  "PlayerFactory.getPlayer" should "return an instance of a player bio" in {
    val playerFactory = new PlayerFactory(StubPlayerDocumentProvider)

    val playerBio = playerFactory.getPlayerBio(6430)

    playerBio.toString shouldEqual PlayerBio(6430, "Jimmy Butler", new LocalDate(1989, 9, 14), "Houston, TX", "2011: 1st Rnd, 30th by CHI").toString
  }
}
