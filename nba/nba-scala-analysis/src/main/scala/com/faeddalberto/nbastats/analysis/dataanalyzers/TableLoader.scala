package com.faeddalberto.nbastats.analysis.dataanalyzers

import com.faeddalberto.nbastats.analysis.context.ContextCreator
import org.apache.spark.sql.DataFrame


object TableLoader {

  def loadTable(keyspace :String, table :String) :DataFrame = {

    val csc = ContextCreator.getCassandraSQLContext()

    val statsBySeason = csc.read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("keyspace" -> keyspace, "table" -> table))
      .load()

    statsBySeason
  }

}

