package com.faeddalberto.nbastats.scraper.provider

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.io.Source

object StubGamesDocumentProvider extends DocumentProvider{
  override def provideDocument(url: String): Document = {

    if (url.contains("chicago-bulls")) {
      return Jsoup.parse(hmtlPortionBulls, "UTF-8")
    }
    return Jsoup.parse(htmlPortionLakers, "UTF-8")
  }

  val hmtlPortionBulls :String = Source.fromURL(getClass().getResource("/bullsGamesHtmlPortion.txt")).mkString


  val htmlPortionLakers :String = Source.fromURL(getClass().getResource("/lakersGamesHtmlPortion.txt")).mkString

}
