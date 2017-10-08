package com.stulsoft.cassandra.model

import com.stulsoft.cassandra.util.DbUtils

/**
  * @author Yuriy Stul.
  */
case class Entity1(name: String, id: Int) extends Entity {
  override val tableName: String = "test_table"
}

object Entity1Runner extends App{
  test()
  def test(): Unit ={
    val e1 = Entity1("test name", 123)
    println(DbUtils.dataMembers(e1))
  }
}