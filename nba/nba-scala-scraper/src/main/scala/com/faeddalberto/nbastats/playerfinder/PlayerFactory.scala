package com.faeddalberto.nbastats.playerfinder

import com.faeddalberto.nbastats.domain.PlayerBio
import com.faeddalberto.nbastats.provider.DocumentProvider
import org.joda.time.LocalDate
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.jsoup.select.Elements

class PlayerFactory(var documentProvider :DocumentProvider) {

  private val dtf :DateTimeFormatter = DateTimeFormat forPattern "MMM dd, yyyy"

  private val player_name = "div.mod-content h1"
  private val player_contracts = "tr[class~=(?i)(oddrow|evenrow)]"
  private val key = "span"
  private val general_info = "ul.general-info"
  private val born_and_drafted = "ul.player-metadata li"
  private val player_with_team = "li:not(.first):not(.last)"
  private val player_bio = "div.player-bio"
  private val baseUrl = "http://espn.go.com/nba/player/_/id/%d/"

  def getPlayerBio(playerId :Int) :PlayerBio = {

    val doc = documentProvider.provideDocument(baseUrl format (playerId))

    val fullName = doc select player_name text
    val playerBio = doc select player_bio
    val bio = getGeneralInfo(playerId, fullName, playerBio)

    bio
  }

  private def getGeneralInfo(id :Int, name :String, playerBio :Elements) :PlayerBio = {

    val bornAndDrafted = getBornAndDrafted(playerBio)
    val dob = getDob(bornAndDrafted)
    val country = getCountry(bornAndDrafted)
    var drafted = bornAndDrafted.get(1).text
    if (drafted.split(",").size != 2) drafted = "n.a."
    PlayerBio(id, name, dob, country, drafted)
  }

  def playerWithoutTeam(generalInfo :Elements) :Boolean = {
    var returnValue = false
    if (generalInfo.select(player_with_team).size == 0) {
      returnValue = true
    } else {
      returnValue = false
    }
    returnValue
  }


  private def getBornAndDrafted(playerBio :Elements) :Elements = {
    val bornAndDrafted = playerBio select born_and_drafted
    bornAndDrafted.get(0).children().remove
    bornAndDrafted.get(1).children().remove
    bornAndDrafted
  }

  private def getDob(bornAndDrafted :Elements) :LocalDate = {
    dtf.parseLocalDate(bornAndDrafted.get(0).text.split("in")(0).split("\\(")(0).trim)
  }

  private def getCountry(bornAndDrafted: Elements) :String = {
    val countryAndAge = bornAndDrafted.get(0).text.split("in")
    if (countryAndAge.size == 1)
      return "n.a."
    else
      countryAndAge(1).split("\\(")(0).trim
  }
}
