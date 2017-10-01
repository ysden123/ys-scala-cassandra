/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.session

import com.datastax.driver.core.Session
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Success

/**
  * @author Yuriy Stul
  */
class ConnectionTest extends FlatSpec with Matchers {

  behavior of "ConnectionTest"

  "openSession" should "openSession" in {
    val session = Connection.openSession("system")
    session shouldBe a[Success[_]]
    session.get shouldBe a[Session]
    Connection.closeSession(Some(session.get))
  }

  "closeSession" should "close session" in {
    val session = Connection.openSession("system")
    session shouldBe a[Success[_]]
    Connection.closeSession(Some(session.get))
  }

  "closeCluster" should "close cluster" in {
    val session = Connection.openSession("system")
    session shouldBe a[Success[_]]
    Connection.closeSession(Some(session.get))
    Connection.closeCluster()
  }
}
