/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.util

import com.stulsoft.cassandra.model.Entity

/** Test entity
  * @author Yuriy Stul
  */
case class TestEntity(name:String,id:Int) extends Entity{
  /**
    * Table name
    */
  override val _tableName = "test_table"
}
