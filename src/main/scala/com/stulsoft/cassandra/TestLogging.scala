/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra

import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
object TestLogging extends App with LazyLogging {
  logger.info("Just a test")

  case class C1(name: String) extends LazyLogging {
    def printName(): Unit = {
      logger.info("name is {}", name)
    }
  }

  C1("this is a test").printName()
}
