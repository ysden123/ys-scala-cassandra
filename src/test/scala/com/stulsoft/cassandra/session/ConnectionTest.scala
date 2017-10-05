/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.session

import java.util.concurrent.TimeUnit

import com.datastax.driver.core.Session
import com.stulsoft.cassandra.util.DbUtils
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/** Unit tests for Connection
  *
  * @author Yuriy Stul
  */
class ConnectionTest extends FlatSpec with Matchers with BeforeAndAfter {

  val UNIT_TEST_KEY_SPACE = "ys_unit_test"
  behavior of "ConnectionTest"

  "createKeySpace" should "create key space" in {
    val f = DbUtils.createKeySpace(UNIT_TEST_KEY_SPACE)
    f.onComplete {
      case Success(_) =>
        succeed
      case Failure(err) =>
        fail(err.getMessage)
    }
    Await.ready(f, Duration(10, TimeUnit.SECONDS))
  }

  "openSession" should "openSession" in {
    val session = Connection.openSession(UNIT_TEST_KEY_SPACE)
    session shouldBe a[Success[_]]
    session.get shouldBe a[Session]
    Connection.closeSession(Some(session.get))
  }

  "closeSession" should "close session" in {
    val session = Connection.openSession(UNIT_TEST_KEY_SPACE)
    session shouldBe a[Success[_]]
    Connection.closeSession(Some(session.get))
  }

  "deleteKeySpace" should "delete key space" in {
    val f = DbUtils.deleteKeySpace(UNIT_TEST_KEY_SPACE)
    f.onComplete {
      case Success(_) =>
        succeed
      case Failure(err) =>
        fail(err.getMessage)
    }
    Await.ready(f, Duration(10, TimeUnit.SECONDS))
  }

  "closeCluster" should "close cluster" in {
    Connection.closeCluster()
  }
}
