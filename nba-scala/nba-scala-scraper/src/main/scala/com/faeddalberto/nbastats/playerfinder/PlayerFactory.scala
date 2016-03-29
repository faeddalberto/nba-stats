package com.faeddalberto.nbastats.playerfinder

import com.faeddalberto.nbastats.domain.Position.Position
import com.faeddalberto.nbastats.domain.{Bio, Player, Position}
import com.faeddalberto.nbastats.provider.DocumentProvider
import org.joda.time.LocalDate
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.SortedSet

class PlayerFactory(var documentProvider :DocumentProvider) {

  private val dtf :DateTimeFormatter = DateTimeFormat forPattern "MMM dd, yyyy"

  private val player_name = "div.mod-content h1"
  private val player_contracts = "tr[class~=(?i)(oddrow|evenrow)]"
  private val key = "span"
  private val general_info = "ul.general-info"
  private val born_and_drafted = "ul.player-metadata li"
  private val height_and_weight_wt = "li:not(.first):not(.last)"
  private val height_and_weight_wot = "li.last"
  private val player_with_team = "li:not(.first):not(.last)"
  private val num_and_position = "li.first"
  private val current_team = "li.last a"
  private val player_bio = "div.player-bio"
  private val player_stats = "div.mod-player-stats table"
  private val baseUrl = "http://espn.go.com/nba/player/_/id/%d/%s"

  def getPlayer(playerLink :String) :Player = {

    val doc = documentProvider.provideDocument(playerLink)

    val playerId = playerLink.split("/").last toInt
    val name = getPlayerName(doc)
    val playerBio = doc select player_bio
    val bio = getGeneralInfo(playerBio)
    val number = getNumber(playerBio)
    val position = getPosition(playerBio)
    val currentTeam = getCurrentTeam(playerBio)
    //val career = getPlayerContracts(doc select player_stats get(0))

    new Player(playerId, name, currentTeam, position)
  }

  private def getPlayerName(doc :Document) :String = {
    doc select player_name text
  }

  private def getGeneralInfo(playerBio :Elements) :Bio = {
    val generalInfo = playerBio select general_info
    var height = "0"
    var weight = "0"
    if (playerWithoutTeam(generalInfo)) {
      height = generalInfo.select(height_and_weight_wot).text.split(",")(0).trim
      weight = generalInfo.select(height_and_weight_wot).text.split(",")(1).trim
    } else {
      height = generalInfo.select(height_and_weight_wt).text.split(",")(0).trim
      weight = generalInfo.select(height_and_weight_wt).text.split(",")(1).trim
    }

    val bornAndDrafted = getBornAndDrafted(playerBio)
    val dob = getDob(bornAndDrafted)
    val country = getCountry(bornAndDrafted)
    val drafted = bornAndDrafted.get(1).text
    Bio(dob, country, height, weight, drafted)
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

  private def getPlayerContracts(playerStats :Element) :SortedSet[String] = {
    val playerContracts = playerStats select player_contracts iterator
    var career = SortedSet[String]()
    while (playerContracts hasNext) {
      val yearAndTeam = playerContracts.next()
      val year = yearAndTeam.select("tr").select("td").get(0).text
      val team = yearAndTeam.select("tr").select("td li.team-name a").text
      career += year + " | " + team
    }

    career
  }

  private def getNumber(generalInfo :Elements) :String = {
    val numAndPos = generalInfo.select(num_and_position).text.split(" ")
    if(numAndPos.size == 1)
      "-"
    else {
      numAndPos(0).replace("#", "")
    }
  }

  private def getPosition(generalInfo :Elements) :Position = {
    val numAndPos = generalInfo.select(num_and_position).text.split(" ")
    if (numAndPos.size == 1) {
      Position.withName(generalInfo.select(num_and_position).text.split(" ")(0))
    } else {
      Position.withName(generalInfo.select(num_and_position).text.split(" ")(1))
    }
  }

  private def getCurrentTeam(playerBio :Elements) :String = {
    val currentTeam = playerBio select current_team
    if (currentTeam == null)
      "n.a."
    else
      currentTeam text
  }

  private def getBornAndDrafted(playerBio :Elements) :Elements = {
    val bornAndDrafted = playerBio select born_and_drafted
    bornAndDrafted.get(0).children().remove
    bornAndDrafted.get(1).children().remove
    bornAndDrafted
  }

  private def getDob(bornAndDrafted :Elements) :LocalDate = {
    dtf.parseLocalDate(bornAndDrafted.get(0).text.split("in")(0).trim)
  }

  private def getCountry(bornAndDrafted: Elements) :String = {
    val countryAndAge = bornAndDrafted.get(0).text.split("in")(1)
    countryAndAge.split("\\(")(0).trim
  }
}
