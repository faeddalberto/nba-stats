package com.faeddalberto.nbastats.playerfinder

import com.faeddalberto.nbastats.provider.StubPlayerDocumentProvider
import org.scalatest.{FlatSpec, Matchers}

class PlayerFactoryTest extends FlatSpec with Matchers {

  "PlayerFactory.getPlayer" should "return an instance of a player given id and mame" in {
    val playerFactory = new PlayerFactory(StubPlayerDocumentProvider)

    val player = playerFactory.getPlayer("http://espn.go.com/nba/player/_/id/6430")
  }
}
