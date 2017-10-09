/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.util

import java.util.concurrent.TimeUnit

import com.stulsoft.cassandra.session.Connection
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}


/** Unit tests for DbUtils
  *
  * @author Yuriy Stul
  */
class DbUtilsTest extends FlatSpec with Matchers {
  val UNIT_TEST_KEY_SPACE = "ys_unit_test_2"
  behavior of "DbUtilsTest"

  "createKeySpace" should "create Key Space" in {
    val f = DbUtils.createKeySpace(UNIT_TEST_KEY_SPACE)
    f.onComplete {
      case Success(_) =>
        succeed
      case Failure(err) =>
        fail(err.getMessage)
    }
    Await.ready(f, Duration(10, TimeUnit.SECONDS))
  }

  "createTable" should "create table" in {
    val session = Connection.openSession(UNIT_TEST_KEY_SPACE) match {
      case Success(x) => x
      case Failure(e) => fail(e.getMessage)
    }
    val t = TestEntity("fhgfhg",21321)
//    val f = DbUtils.createTable(session, TestEntity, DbUtils.dataMembers(TestEntity))
    val f = DbUtils.createTable(session, t, DbUtils.dataMembers[TestEntity]())

    f.onComplete {
      case Success(_) =>
        succeed
      case Failure(err) =>
        fail(err.getMessage)
    }
    Await.ready(f, Duration(10, TimeUnit.SECONDS))
  }

  "dropTable" should "delete table" in {
    val session = Connection.openSession(UNIT_TEST_KEY_SPACE) match {
      case Success(x) => x
      case Failure(e) => fail(e.getMessage)
    }
    val f = DbUtils.dropTable(session, "test_table")
    f.onComplete {
      case Success(_) =>
        succeed
      case Failure(err) =>
        fail(err.getMessage)
    }
    Await.ready(f, Duration(10, TimeUnit.SECONDS))
  }

  "deleteKeySpace" should "delete Key Space" in {
    val f = DbUtils.deleteKeySpace(UNIT_TEST_KEY_SPACE)
    f.onComplete {
      case Success(_) =>
        succeed
      case Failure(err) =>
        fail(err.getMessage)
    }
    Await.ready(f, Duration(20, TimeUnit.SECONDS))
  }
}
