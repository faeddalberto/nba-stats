package com.faeddalberto.nbastats.provider

import org.jsoup.nodes.Document

trait DocumentProvider {

  def provideDocument(url :String) :Document
}
