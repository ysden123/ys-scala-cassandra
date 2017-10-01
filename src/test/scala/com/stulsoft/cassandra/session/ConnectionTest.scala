/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.session

import com.datastax.driver.core.Session
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.util.Success

/**
  * @author Yuriy Stul
  */
class ConnectionTest extends FlatSpec with Matchers with BeforeAndAfter {

  val UNIT_TEST_KEY_SPACE = "ys_unit_test"
  behavior of "ConnectionTest"

  "createKeySpace" should "create key space" in {
    Connection.createKeySpace(UNIT_TEST_KEY_SPACE)
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

  "deleteKeySpace" should "create key space" in {
    Connection.deleteKeySpace(UNIT_TEST_KEY_SPACE)
  }

  "closeCluster" should "close cluster" in {
    Connection.closeCluster()
  }
}
