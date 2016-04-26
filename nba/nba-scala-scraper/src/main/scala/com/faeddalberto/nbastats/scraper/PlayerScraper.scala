package com.faeddalberto.nbastats.scraper

import java.io.File

import com.faeddalberto.nbastats.scraper.domain.PlayerBio
import com.faeddalberto.nbastats.scraper.playerfinder.PlayerFactory
import com.faeddalberto.nbastats.scraper.provider.HtmlDocumentProvider
import com.faeddalberto.nbastats.scraper.writer.PlayerWriter

import scala.collection.mutable
import scala.collection.mutable._

object PlayerScraper extends App {

  val data_path :String = args(0)
  val players_path :String = args(1)

  val f :File = new File(data_path)
  val these = f.listFiles

  val statsFiles = ArrayBuffer[File]()
  for(thisFile <- these) {
    if (thisFile.isDirectory) {
      val seasonFiles = thisFile.listFiles();
      for (seasonFile <- seasonFiles) {
        if (seasonFile.isDirectory && seasonFile.getName.split("/").last.toString.equals("games-stats")) {
          val statsFile = seasonFile.getCanonicalFile.listFiles()
          statsFiles ++= statsFile
        }
      }
    }
  }

  var playersId = Set[Int]()
  for (statsFile <- statsFiles) {
    readFile(statsFile, playersId)
  }

  var playersIdToBio = mutable.Map[Int, PlayerBio]()
  for (playerId <- playersId) {
    if (!new File(players_path, playerId + ".csv").exists()) {
      println(playerId)
      if (playerId != 2874) {
        val playerFactory = new PlayerFactory(new HtmlDocumentProvider)
        playersIdToBio(playerId) = playerFactory.getPlayerBio(playerId)
        new PlayerWriter().writeToFile(playersIdToBio(playerId), players_path)
      }
    }
  }



  def readFile(file :File, playersId :Set[Int]): Unit = {
    if (file.getName.endsWith(".csv")) {
      val bufferedSource = io.Source.fromFile(file)
      var i = 0
      for (line <- bufferedSource.getLines) {
        if (i != 0) {
          val cols = line.split(",").map(_.trim)
          //println(cols(1))
          playersId += cols(1).toInt
        }
        i += 1
      }
      bufferedSource.close
    }
  }
}
