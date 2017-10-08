package com.stulsoft.cassandra.model

import scala.reflect.runtime.universe._

/**
  * @author Yuriy Stul.
  */
trait Entity {
  val tableName: String
}
