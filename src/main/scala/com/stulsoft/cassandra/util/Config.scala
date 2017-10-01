/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.util

import java.io.File

import com.typesafe.config.ConfigFactory

/** Configuration
  *
  * @author Yuriy Stul
  */
object Config {
  /**
    * List of Cassandra's hosts
    */
  lazy val hosts = cassandra.getStringList("hosts")
  /**
    * Port number
    */
  lazy val port = cassandra.getInt("port")
  private lazy val cassandra = ConfigFactory.parseFile(new File("application.conf")).withFallback(ConfigFactory.load()).getConfig("cassandra")
}
