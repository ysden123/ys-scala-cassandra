/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.model

import com.stulsoft.cassandra.model.ColType.ColType

/** Column definition
  *
  * @author Yuriy Stul
  */
case class ColumnDefinition(name: String, colType: ColType, primaryKey: Boolean = false)

/**
  * Column types
  */
object ColType extends Enumeration {
  type ColType = Value
  val INT: Value = Value("int")
  val TEXT: Value = Value("text")
}