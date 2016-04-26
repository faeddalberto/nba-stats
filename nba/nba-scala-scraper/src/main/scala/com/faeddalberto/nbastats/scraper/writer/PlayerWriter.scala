package com.faeddalberto.nbastats.scraper.writer

import java.io.{BufferedWriter, File, FileWriter}

import com.faeddalberto.nbastats.scraper.domain.PlayerBio

class PlayerWriter {

  private val new_line = "\n"
  private val separator = ","
  private val players_file_name = ".csv"
  private val header = "id,full name, dob, country, drafted"

  def writeToFile(playerBio :PlayerBio, path :String) = {

    val file = new File(path, playerBio.id + players_file_name)

    if (!file. exists) {
      val writer = new BufferedWriter(new FileWriter(file))
      writer write header + new_line
      val id = playerBio id
      val name = playerBio.fullName.replaceAll(",", " ")
      val dob = playerBio dob
      val country = playerBio.country.replaceAll(",", " - ")
      val drafted = playerBio.drafted.replaceAll(",", " ")

      if (country == "n.a." || drafted == "n.a.") {
        println("To fix: " + id)
      }

      writer write (id + separator + name + separator + dob + separator + country + separator + drafted + new_line)

      writer close

      println (s"Player ${playerBio.fullName} Saved Successfully")
    }
  }
}
