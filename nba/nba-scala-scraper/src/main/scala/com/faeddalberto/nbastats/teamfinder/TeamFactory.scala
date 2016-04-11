package com.faeddalberto.nbastats.teamfinder

import com.faeddalberto.nbastats.domain.{Division, Team}
import com.faeddalberto.nbastats.provider.DocumentProvider
import org.jsoup.select.Elements

import scala.collection.mutable.ArrayBuffer

class TeamFactory(documentProvider :DocumentProvider) {

  private val teams_by_division = "div.mod-teams-list-medium";
  private val division = "div.mod-header h4"
  private val teams_list = "ul.medium-logos li"
  private val team_page_link = "h5 a"
  private val url = "http://espn.go.com/nba/teams"

  def getAllTeams:ArrayBuffer[Team] = {
    val doc = documentProvider.provideDocument(url)

    val teamsByDivisionList = doc.select (teams_by_division) iterator

    var teams = ArrayBuffer[Team]()

    while(teamsByDivisionList hasNext) {
      val teamsByDivision = teamsByDivisionList next

      val teamList = teamsByDivision select teams_list
      val divisionName = teamsByDivision select division text

      teams ++= teamsOfDivision(teamList, divisionName)
    }

    teams
  }

  def teamsOfDivision(teamList :Elements, division :String) :ArrayBuffer[Team] = {
    var teams = ArrayBuffer[Team]()
    val teamListIt = teamList iterator

    while (teamListIt hasNext) {
      val team = teamListIt next
      val teamPageLink = team select team_page_link

      val url = teamPageLink attr "href"

      val prefixes = getPrefixes(url, 2)

      teams += new Team(teamPageLink.text, Division.withName(division), url, prefixes(0), prefixes(1))
    }
    teams

  }

  def getPrefixes(url :String, element :Int) :Array[String] = {
    url split "/" takeRight element
  }
}
