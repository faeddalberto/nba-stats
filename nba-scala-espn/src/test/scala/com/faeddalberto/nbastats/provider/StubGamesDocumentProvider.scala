package com.faeddalberto.nbastats.provider

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object StubGamesDocumentProvider extends DocumentProvider{
  override def provideDocument(url: String): Document = {

    if (url.contains("chicago-bulls")) {
      return Jsoup.parse(hmtlPortionBulls, "UTF-8")
    }
    return Jsoup.parse(htmlPortionLakers, "UTF-8")
  }

  val hmtlPortionBulls :String =
    "<div class=\"mod-container mod-table mod-no-header-footer\">" +
      "<div class=\"mod-content\">" +
        "<table cellspacing=\"1\" cellpadding=\"3\" class=\"tablehead\">" +
         "<tbody>" +
          "<tr class=\"stathead\">" +
            "<td colspan=\"9\">2015 Regular Season Schedule</td>" +
          "</tr>" +
          "<tr class=\"colhead\">" +
            "<td>OCTOBER</td>" +
            "<td>OPPONENT</td>" +
            "<td>RESULT</td>" +
            "<td>W-L</td>" +
            "<td align=\"left\" title=\"Points Leader\">HI POINTS</td>" +
            "<td align=\"left\" title=\"Rebounds Leader\">HI REBOUNDS</td>" +
            "<td align=\"left\" title=\"Assists Leader\">HI ASSISTS</td>" +
          "</tr>" +
          "<tr class=\"oddrow team-46-18\">" +
            "<td>Wed, Oct 29</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status\">@</li>" +
                "<li class=\"team-logo-small logo-nba-small\"><a href=\"http://espn.go.com/nba/team/_/name/ny/new-york-knicks\"><img src=\"http://a.espncdn.com/combiner/i?img=/i/teamlogos/nba/500/scoreboard/NY.png&amp;h=80&amp;w=80\"></a></li>" +
                "<li class=\"team-name\"><a href=\"http://espn.go.com/nba/team/_/name/ny/new-york-knicks\">NY Knicks</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status win\"><span class=\"greenfont\">W</span></li>" +
                "<li class=\"score\"><a href=\"/nba/recap?id=400578302\">104-80</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>1-0</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/3986/taj-gibson\">T. Gibson</a> 22</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/996/pau-gasol\">P. Gasol</a> 11</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/3192/aaron-brooks\">A. Brooks</a> 6</td>" +
          "</tr>" +
          "<tr class=\"evenrow team-46-5\">" +
            "<td>Fri, Oct 31</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status\">vs</li>" +
                "<li class=\"team-logo-small logo-nba-small\"><a href=\"http://espn.go.com/nba/team/_/name/cle/cleveland-cavaliers\"><img src=\"http://a.espncdn.com/combiner/i?img=/i/teamlogos/nba/500/scoreboard/Cle.png&amp;h=80&amp;w=80\"></a></li>" +
                "<li class=\"team-name\"><a href=\"http://espn.go.com/nba/team/_/name/cle/cleveland-cavaliers\">Cleveland</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status loss\"><span class=\"redfont\">L</span></li>" +
                "<li class=\"score\"><a href=\"/nba/recap?id=400578314\">114-108 OT</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>1-1</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/1966/lebron-james\">L. James</a> 36</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/3449/kevin-love\">K. Love</a> 16</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/1708/mike-dunleavy\">M. Dunleavy</a> 8</td>" +
          "</tr>" +
          "<tr class=\"colhead\">" +
            "<td>NOVEMBER</td>" +
            "<td>OPPONENT</td>" +
            "<td>RESULT</td>" +
            "<td>W-L</td>" +
            "<td align=\"left\" title=\"Points Leader\">HI POINTS</td>" +
            "<td align=\"left\" title=\"Rebounds Leader\">HI REBOUNDS</td>" +
            "<td align=\"left\" title=\"Assists Leader\">HI ASSISTS</td>" +
          "</tr>" +
          "<tr class=\"oddrow team-46-16\">" +
            "<td>Sat, Nov 1</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status\">@</li>" +
                "<li class=\"team-logo-small logo-nba-small\"><a href=\"http://espn.go.com/nba/team/_/name/min/minnesota-timberwolves\"><img src=\"http://a.espncdn.com/combiner/i?img=/i/teamlogos/nba/500/scoreboard/Min.png&amp;h=80&amp;w=80\"></a></li>" +
                "<li class=\"team-name\"><a href=\"http://espn.go.com/nba/team/_/name/min/minnesota-timberwolves\">Minnesota</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status win\"><span class=\"greenfont\">W</span></li>" +
                "<li class=\"score\"><a href=\"/nba/recap?id=400578327\">106-105</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>2-1</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/2394/kevin-martin\">K. Martin</a> 33</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/3224/joakim-noah\">J. Noah</a> 11</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/4011/ricky-rubio\">R. Rubio</a> 17</td>" +
          "</tr>" +
        "</tbody>" +
      "</table>" +
    "</div>" +
  "</div>"


  val htmlPortionLakers :String =
    "<div class=\"mod-container mod-table mod-no-header-footer\">" +
      "<div class=\"mod-content\">" +
        "<table cellspacing=\"1\" cellpadding=\"3\" class=\"tablehead\">" +
          "<tr class=\"stathead\">" +
            "<td colspan=\"9\">2016 Regular Season Schedule</td>" +
          "</tr>" +
          "<tr class=\"colhead\">" +
            "<td>OCTOBER</td>" +
            "<td>OPPONENT</td>" +
            "<td>RESULT</td>" +
            "<td>W-L</td>" +
            "<td align=\"left\" title=\"Points Leader\">HI POINTS</td>" +
            "<td align=\"left\" title=\"Rebounds Leader\">HI REBOUNDS</td>" +
            "<td align=\"left\" title=\"Assists Leader\">HI ASSISTS</td>" +
          "</tr>" +
          "<tr class=\"oddrow team-46-16\">" +
            "<td>Wed, Oct 28</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status\">vs</li>" +
                "<li class=\"team-logo-small logo-nba-small\">" + "<a href=\"http://espn.go.com/nba/team/_/name/min/minnesota-timberwolves\"><img src=\"http://a.espncdn.com/combiner/i?img=/i/teamlogos/nba/500/scoreboard/Min.png&h=80&w=80\"></a></li>" +
                "<li class=\"team-name\"><a href=\"http://espn.go.com/nba/team/_/name/min/minnesota-timberwolves\">Minnesota</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status loss\"><span class=\"redfont\">L</span></li>" +
                "<li class=\"score\"><a href=\"/nba/recap?id=400827904\">112-111</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>0-1</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/4011/ricky-rubio\">R. Rubio</a> 28</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/3136195/karl-anthony-towns\">K. Towns</a> 12</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/4011/ricky-rubio\">R. Rubio</a> 14</td>" +
          "</tr>" +
          "<tr class=\"evenrow team-46-23\">" +
            "<td>Fri, Oct 30</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status\">@</li>" +
                "<li class=\"team-logo-small logo-nba-small\"><a href=\"http://espn.go.com/nba/team/_/name/sac/sacramento-kings\"><img src=\"http://a.espncdn.com/combiner/i?img=/i/teamlogos/nba/500/scoreboard/Sac.png&h=80&w=80\"></a></li>" +
                "<li class=\"team-name\"><a href=\"http://espn.go.com/nba/team/_/name/sac/sacramento-kings\">Sacramento</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>" +
              "<ul class=\"game-schedule\">" +
                "<li class=\"game-status loss\"><span class=\"redfont\">L</span></li>" +
                "<li class=\"score\"><a href=\"/nba/recap?id=400827918\">132-114</a></li>" +
              "</ul>" +
            "</td>" +
            "<td>0-2</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/2528426/jordan-clarkson\">J. Clarkson</a> 22</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/4258/demarcus-cousins\">D. Cousins</a> 11</td>" +
            "<td colspan=\"1\"><a href=\"http://espn.go.com/nba/player/_/id/3026/rajon-rondo\">R. Rondo</a> 8</td>" +
          "</tr>" +
        "</table>" +
      "</div>" +
    "</div>"

}
