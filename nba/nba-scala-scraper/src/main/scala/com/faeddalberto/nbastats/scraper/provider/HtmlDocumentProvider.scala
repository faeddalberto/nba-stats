package com.faeddalberto.nbastats.scraper.provider

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HtmlDocumentProvider extends DocumentProvider {
  override def provideDocument(url: String): Document = Jsoup.connect(url) get
}
