/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.util

import com.datastax.driver.core.{ResultSet, ResultSetFuture, Session}
import com.google.common.util.concurrent.{FutureCallback, Futures}
import com.stulsoft.cassandra.session.Connection.cluster
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{Future, Promise}

/**
  * @author Yuriy Stul
  */
object DbUtils extends LazyLogging {
  /**
    * Creates key space
    *
    * @param keySpace key space name
    */
  def createKeySpace(keySpace: String): Future[ResultSet] = {
    try {
//      logger.debug(s"getting connection")
      val session = cluster.connect()
//      logger.debug(s"session: $session")
      val query = s"create keyspace if not exists  $keySpace with replication = {'class':'SimpleStrategy', 'replication_factor':1};"
      val resultSetFuture = session.executeAsync(query)
      waitAndCloseSession(resultSetFuture, session)
    }
    catch {
      case e: Exception =>
        val msg = s"Error in createKeySpace: ${e.getMessage}"
        logger.error(msg)
        Future.failed(new RuntimeException(msg))
    }
  }

  /**
    * Deletes a key space
    *
    * @param keySpace the key space
    * @return Future with ResultSet
    */
  def deleteKeySpace(keySpace: String): Future[ResultSet] = {
    try {
//      logger.debug(s"getting connection")
      val session = cluster.connect()
//      logger.debug(s"session: $session")
      val query = s"drop keyspace if exists  $keySpace;"
      val resultSetFuture = session.executeAsync(query)
      waitAndCloseSession(resultSetFuture, session)
    }
    catch {
      case e: Exception =>
        val msg = s"Error in deleteKeySpace: ${e.getMessage}"
        logger.error(msg)
        Future.failed(new RuntimeException(msg))
    }
  }

  /**
    * Waits resultSetFuture completion then closes session
    *
    * @param resultSetFuture the ResultSetFuture (Java, Guava)
    * @param session         the session
    * @return Future[ResultSet]
    */
  def waitAndCloseSession(resultSetFuture: ResultSetFuture, session: Session): Future[ResultSet] = {
    require(resultSetFuture != null, "resultSetFuture should be defined")
    require(session != null, "session should be defined")
    val promise = Promise[ResultSet]
    Futures.addCallback(resultSetFuture, new FutureCallback[ResultSet] {
      def onSuccess(result: ResultSet): Unit = {
//        logger.debug("Closing connection onSuccess")
        session.close()
//        logger.debug("Closed connection onSuccess")
        promise success result
      }

      def onFailure(err: Throwable): Unit = {
//        logger.debug("Closing connection onFailure")
        session.close()
//        logger.debug("Closed connection onFailure")
        promise failure err
      }
    })
    promise.future
  }
}
