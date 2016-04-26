package com.faeddalberto.nbastats.scraper.teamfinder

import com.faeddalberto.nbastats.scraper.domain.Conference.EASTERN
import com.faeddalberto.nbastats.scraper.domain.Division._
import com.faeddalberto.nbastats.scraper.domain.Team
import com.faeddalberto.nbastats.scraper.provider.StubTeamDocumentProvider
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ArrayBuffer

class TeamFactoryTest extends FlatSpec with Matchers {

  "TeamFactory.getAllTeams" should "return a list of 3 NBA teams" in {
    val provider = StubTeamDocumentProvider

    val factory: TeamFactory = new TeamFactory(provider)

    val teams: ArrayBuffer[Team] = factory.getAllTeams

    val teamNames = new ArrayBuffer[String]
    for (elem <- teams) {
      teamNames += elem.name
      elem.division shouldBe Atlantic
      elem.conference shouldBe EASTERN
    }


    teams.size should equal (5)
    teamNames should contain allOf ("Boston Celtics", "Brooklyn Nets", "New York Knicks", "Philadelphia 76ers", "Toronto Raptors")

  }

  "TeamFactory.getPrefixes" should "return an array of 2 elements with the team's prefixes" in {
    val url = "http://espn.go.com/nba/team/_/name/ny/new-york-knicks"

    val not_used_provider = StubTeamDocumentProvider
    val factory = new TeamFactory(not_used_provider)
    val prefixes = factory.getPrefixes(url, 2)

    prefixes.length should equal (2)
    prefixes(0) shouldBe "ny"
    prefixes(1) shouldBe "new-york-knicks"
  }
}
