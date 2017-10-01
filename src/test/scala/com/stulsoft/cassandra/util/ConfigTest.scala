/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.util

import org.scalatest.{FlatSpec, Matchers}

/** Unit tests for Config
  * @author Yuriy Stul
  */
class ConfigTest extends FlatSpec with Matchers {

  behavior of "ConfigTest"

  "hosts" should "returns collection of hosts" in {
    val hosts = Config.hosts
    hosts should have length 1
  }

  "port" should "return port 9042" in {
    val port = Config.port
    port shouldBe a [Integer]
    port shouldBe 9042
  }
}
