package com.faeddalberto.nbastats.analysis.context

import java.io.FileInputStream
import java.util.Properties

import org.apache.spark.{SparkConf, SparkContext}

object ContextCreator {

  var sc :SparkContext =_

  val (contactPoints, masterHost, masterPort, keyspace) =
    try {
      val propertiesPath  = getClass.getResource("/cluster.properties")
      val prop = new Properties()
      prop.load(new FileInputStream(propertiesPath.getPath))
      (
        prop.getProperty("cassandra.contact-points"),
        prop.getProperty("spark.master.host"),
        prop.getProperty("spark.master.port"),
        prop.getProperty("cassandra.keyspace")
      )
    } catch { case e: Exception =>
      e.printStackTrace()
      sys.exit(1)
    }

  private val conf = new SparkConf()
    .set("spark.cassandra.connection.host", contactPoints)
    .setAppName("NBA Analytics")
    .setMaster(masterHost)
    .set("sp‌​ark.driver.port", masterPort)

  def getSparkContext() : SparkContext = {
    if (sc == null) {
      sc = new SparkContext(conf)
    }
      sc
  }
}
