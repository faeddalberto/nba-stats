package com.faeddalberto.nbastats.teamdefiner

import java.util
import com.faeddalberto.nbastats.domain.Team
import com.faeddalberto.nbastats.provider.DocumentProvider
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.mutable.ArrayBuffer

class TeamFactory(documentProvider :DocumentProvider) {

  val teams_list = "li"
  val team_page_link = "h5 a"
  val url = "http://espn.go.com/nba/teams"

  def getAllTeams:ArrayBuffer[Team] = {
    val doc :Document = documentProvider.provideDocument(url)

    val ul :Elements = doc.select("ul.medium-logos")
    val teamList :util.Iterator[Element] = ul select teams_list iterator

    var teams = ArrayBuffer[Team]()

    while(teamList hasNext) {
      val team :Element = teamList next
      val teamPageLink :Elements = team select team_page_link

      val url = teamPageLink attr "href"

      val prefixes :Array[String] = getPrefixes(url, 2)

      teams += new Team(teamPageLink.text(), url, prefixes(0), prefixes(1))
    }
    teams
  }

  def getPrefixes(url :String, element :Int) :Array[String] = {
    url split "/" takeRight element
  }
}
