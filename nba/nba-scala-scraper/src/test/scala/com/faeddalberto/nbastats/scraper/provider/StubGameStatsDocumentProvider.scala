package com.faeddalberto.nbastats.scraper.provider

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.io.Source

object StubGameStatsDocumentProvider extends DocumentProvider {

  override def provideDocument(url: String): Document = Jsoup.parse(htmlPortion, "UTF-8");

  val htmlPortion = Source.fromURL(getClass().getResource("/gameStatsHtmlPortion.txt")).mkString
}
