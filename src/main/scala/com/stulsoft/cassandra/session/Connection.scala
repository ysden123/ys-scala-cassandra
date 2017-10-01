/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.session

import com.datastax.driver.core.{Cluster, Session}
import com.stulsoft.cassandra.util.Config

import scala.util.{Failure, Success, Try}

/**
  * Connection
  *
  * @author Yuriy Stul
  */
object Connection {
  lazy private val cluster = {
    val b = Cluster.builder()

    Config.hosts.forEach(host => b.addContactPoint(host))

    b.build()
  }

  /**
    * Returns a new Session for specified key space
    *
    * @param keySpace the key space
    * @return the new Session for specified key space
    */
  def openSession(keySpace: String): Try[Session] = {
    try {
      val session = cluster.connect(keySpace)
      Success(session)
    }
    catch {
      case e: Exception => Failure(e)
    }
  }

  /**
    * Closes a Session
    *
    * @param session the Session to close
    */
  def closeSession(session: Option[Session]): Unit = {
    if (session.isDefined) {
      session.get.close()
    }
  }

  /**
    * Closes cluster
    */
  def closeCluster(): Unit = {
    cluster.close()
  }

  /**
    * Creates key space
    *
    * @param keySpace key space name
    */
  def createKeySpace(keySpace: String): Unit = {
    try {
      val session = cluster.connect()
      val query = s"create keyspace if not exists  $keySpace with replication = {'class':'SimpleStrategy', 'replication_factor':1};"
      session.execute(query)
      session.close()
    }
    catch {
      case e: Exception => println(s"Error in createKeySpace: ${e.getMessage}")
    }
  }

  /**
    * Deletes a key space
    * @param keySpace the key space
    */
  def deleteKeySpace(keySpace:String):Unit={
    try {
      val session = cluster.connect()
      val query = s"drop keyspace if exists  $keySpace;"
      session.execute(query)
      session.close()
    }
    catch {
      case e: Exception => println(s"Error in createKeySpace: ${e.getMessage}")
    }
  }
}
