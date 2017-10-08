package com.stulsoft.cassandra.model

import com.stulsoft.cassandra.util.DbUtils

/**
  * @author Yuriy Stul.
  */
case class DataMembersTest(name: String, id: Int)

object DataMembersTestRunner extends App {
  test()

  def test(): Unit = {
    val dmt = DataMembersTest("the name", 123)
    println(DbUtils.dataMembers(dmt))
  }
}