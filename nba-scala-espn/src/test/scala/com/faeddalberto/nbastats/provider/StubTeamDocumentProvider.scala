package com.faeddalberto.nbastats.provider

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object StubTeamDocumentProvider extends DocumentProvider {

  override def provideDocument(url: String): Document = Jsoup.parse(htmlPortion, "UTF-8");

  val htmlPortion :String =
    "<ul class=\"medium-logos\">" +
      "<li class=\"first\">" +
        "<div class=\"logo-nba-medium nba-medium-bos\">" +
          "<h5>" +
            "<a href=\"http://espn.go.com/nba/team/_/name/bos/boston-celtics\" class=\"bi\">Boston Celtics</a>" +
          "</h5>" +
        "</div>" +
      "</li>" +
      "<li class=\"alt\">" +
        "<div class=\"logo-nba-medium nba-medium-bkn\">" +
          "<h5>" +
            "<a href=\"http://espn.go.com/nba/team/_/name/bkn/brooklyn-nets\" class=\"bi\">Brooklyn Nets</a>" +
          "</h5>" +
        "</div>" +
      "</li>" +
      "<li class=\"\">" +
        "<div class=\"logo-nba-medium nba-medium-nyk\">" +
          "<h5>" +
            "<a href=\"http://espn.go.com/nba/team/_/name/ny/new-york-knicks\" class=\"bi\">New York Knicks</a>" +
          "</h5>" +
        "</div>" +
      "</li>" +
    "</ul>"
}
