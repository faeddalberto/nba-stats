package com.faeddalberto.nbastats.scraper.provider

import org.jsoup.nodes.Document

trait DocumentProvider {

  def provideDocument(url :String) :Document
}
