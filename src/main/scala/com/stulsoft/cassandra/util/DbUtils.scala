/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.util

import com.datastax.driver.core.{ResultSet, ResultSetFuture, Session}
import com.google.common.util.concurrent.{FutureCallback, Futures}
import com.stulsoft.cassandra.model.ColumnDefinition
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
      waitResultAndCloseSession(resultSetFuture, session)
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
      val session = cluster.connect()
      val query = s"drop keyspace if exists  $keySpace;"
      val resultSetFuture = session.executeAsync(query)
      waitResultAndCloseSession(resultSetFuture, session)
    }
    catch {
      case e: Exception =>
        val msg = s"Error in deleteKeySpace: ${e.getMessage}"
        logger.error(msg)
        Future.failed(new RuntimeException(msg))
    }
  }

  /**
    * Waits result and closes session
    *
    * @param resultSetFuture the result set future (Java, Guava)
    * @param session         the session
    * @return Future[ResultSet] (Scala)
    */
  def waitResultAndCloseSession(resultSetFuture: ResultSetFuture, session: Session): Future[ResultSet] = {
    require(resultSetFuture != null, "resultSetFuture should be defined")
    require(session != null, "session should be defined")
    waitAndCloseSession(resultSetFuture, Some(session))
  }

  /**
    * Creates a table
    *
    * @param session        the session
    * @param name           the table name
    * @param colDefinitions column definitions
    * @return Future[ResultSet]
    */
  def createTable(session: Session, name: String, colDefinitions: Seq[ColumnDefinition]): Future[ResultSet] = {
    try {
      val columns = colDefinitions.map(col => {
        s"""${col.name}  ${col.colType} ${if (col.primaryKey) "primary key" else ""}"""
      }).mkString(",")
      val query = s"create table if not exists $name (" + columns + ");"

      val resultSetFuture = session.executeAsync(query)
      waitResult(resultSetFuture)
    }
    catch {
      case e: Exception =>
        val msg = s"Error in createTable: ${e.getMessage}"
        logger.error(msg)
        Future.failed(new RuntimeException(msg))
    }
  }

  /**
    * Waits result
    *
    * @param resultSetFuture the result set future (Java, Guava)
    * @return Future[ResultSet] (Scala)
    */
  def waitResult(resultSetFuture: ResultSetFuture): Future[ResultSet] = {
    require(resultSetFuture != null, "resultSetFuture should be defined")
    waitAndCloseSession(resultSetFuture, None)
  }

  /**
    * Waits resultSetFuture completion then closes session
    *
    * @param resultSetFuture the ResultSetFuture (Java, Guava)
    * @param session         the session
    * @return Future[ResultSet] (Scala)
    */
  private def waitAndCloseSession(resultSetFuture: ResultSetFuture, session: Option[Session]): Future[ResultSet] = {
    require(resultSetFuture != null, "resultSetFuture should be defined")
    val promise = Promise[ResultSet]
    Futures.addCallback(resultSetFuture, new FutureCallback[ResultSet] {
      def onSuccess(result: ResultSet): Unit = {
        session match {
          case Some(x: Session) => x.close()
          case _ =>
        }
        promise success result
      }

      def onFailure(err: Throwable): Unit = {
        session match {
          case Some(x: Session) => x.close()
          case _ =>
        }
        promise failure err
      }
    })
    promise.future
  }

  /**
    * Deletes a table
    *
    * @param session the session
    * @param name    the table name
    * @return Future[ResultSet]
    */
  def dropTable(session: Session, name: String): Future[ResultSet] = {
    try {
      val query = s"drop table if exists $name"
      val resultSetFuture = session.executeAsync(query)
      waitResult(resultSetFuture)
    }
    catch {
      case e: Exception =>
        val msg = s"Error in dropTable: ${e.getMessage}"
        logger.error(msg)
        Future.failed(new RuntimeException(msg))
    }
  }

  import scala.reflect.runtime.universe._

  def dataMembers[T](e: T)(implicit tag: TypeTag[T]): Set[(String,String)] = {
    val classAccessors = typeOf[T].members.filter(m => !m.isMethod && m.name.toString.trim != "tableName")

    classAccessors.map{ ca =>
      val tt = ca.typeSignature
      ca.typeSignature.toString match{
        case "String" => (ca.name.toString.trim, "text")
        case "Option[String]" => (ca.name.toString.trim, "text")
        case "Int" => (ca.name.toString.trim, "int")
        case "Option[Int]" => (ca.name.toString.trim, "int")
        case _ =>
          throw new RuntimeException(s"${ca.name.toString} has unsupported type ${ca.typeSignature}")
      }
    }.toSet
  }
}
