package com.faeddalberto.nbastats.writer

import java.io.{FileWriter, BufferedWriter, File}

import com.faeddalberto.nbastats.domain.Team

import scala.collection.mutable.ArrayBuffer

class TeamsWriter {

  private val new_line = "\n"
  private val separator = ","
  private val teams_file_name = "teams.csv"
  private val header = "name, conference, division, url, prefix_1, prefix_2"

  def writeToFile(teams :ArrayBuffer[Team], path :String) = {

    val file = new File(path, teams_file_name)

    if (!file. exists) {
      val writer = new BufferedWriter(new FileWriter(file))
      writer write header + new_line
      for (team <- teams) {
        val name = team name
        val conference = team.conference + " CONFERENCE"
        val division = team.division + " Division"
        val url = team url
        val prefix1 = team prefix_1
        val prefix2 = team prefix_2

        writer write (name + separator + conference + separator + division + separator + url + separator + prefix1 + separator + prefix2 + new_line)
      }
      writer close

      println ("Teams Saved Successfully")
    }
  }
}
