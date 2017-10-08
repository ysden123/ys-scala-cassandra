package com.stulsoft.cassandra.model

import com.stulsoft.cassandra.util.DbUtils

/**
  * @author Yuriy Stul.
  */
case class Entity1(name: String, id: Int) extends Entity {
  override val tableName: String = "test_table"
}

case class Entity2(name:Option[String], id: Option[Int]) extends Entity {
  override val tableName: String = "test_table"
}

object Entity1Runner extends App{
  test1()
  test2()
  def test1(): Unit ={
    val e = Entity1("test name", 123)
    println(DbUtils.dataMembers(e))
  }

  def test2(): Unit ={
    val e = Entity2(Some("test name"), Some( 123))
    println(DbUtils.dataMembers(e))
  }
}