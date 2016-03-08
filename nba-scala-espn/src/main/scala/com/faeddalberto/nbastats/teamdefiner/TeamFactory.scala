package com.faeddalberto.nbastats.teamdefiner

import com.faeddalberto.nbastats.domain.Team
import com.faeddalberto.nbastats.provider.DocumentProvider

import scala.collection.mutable.ArrayBuffer

class TeamFactory(documentProvider :DocumentProvider) {

  val teams_list = "ul.medium-logos li"
  val team_page_link = "h5 a"
  val url = "http://espn.go.com/nba/teams"

  def getAllTeams:ArrayBuffer[Team] = {
    val doc = documentProvider.provideDocument(url)

    val teamList = doc.select (teams_list) iterator

    var teams = ArrayBuffer[Team]()

    while(teamList hasNext) {
      val team = teamList next
      val teamPageLink = team select team_page_link

      val url = teamPageLink attr "href"

      val prefixes = getPrefixes(url, 2)

      teams += new Team(teamPageLink.text, url, prefixes(0), prefixes(1))
    }
    teams
  }

  def getPrefixes(url :String, element :Int) :Array[String] = {
    url split "/" takeRight element
  }
}
