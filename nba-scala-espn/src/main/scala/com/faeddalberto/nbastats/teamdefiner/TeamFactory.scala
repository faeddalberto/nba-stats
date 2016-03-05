package com.faeddalberto.nbastats.teamdefiner

import java.util
import com.faeddalberto.nbastats.domain.Team
import com.faeddalberto.nbastats.provider.DocumentProvider
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.mutable.ArrayBuffer

class TeamFactory(documentProvider :DocumentProvider) {

  val url = "http://espn.go.com/nba/teams"

  def getAllTeams:ArrayBuffer[Team] = {
    val doc :Document = documentProvider.provideDocument(url)

    val ul :Elements = doc.select("ul.medium-logos")
    val lis :util.Iterator[Element] = ul.select("li") iterator

    var teams = ArrayBuffer[Team]()

    while(lis hasNext) {
      val li :Element = lis next
      val a :Elements = li select "h5 a"

      val url = a attr "href"

      val prefixes :Array[String] = getPrefixes(url, 2)

      teams += new Team(a.text(), url, prefixes(0), prefixes(1))
    }
    teams
  }

  def getPrefixes(url :String, element :Int) :Array[String] = {
    url split "/" takeRight element
  }
}
