/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.model

import org.scalatest.{FlatSpec, Matchers}

/** Unit tests for GeneralDTO
  * @author Yuriy Stul
  */
class GeneralDTOTest extends FlatSpec with Matchers {

  behavior of "GeneralDTOTest"

  case class TestDTO(name: String, id: Option[Int]) extends GeneralDTO {
    override def columns: Seq[(String, Any)] = {
      super.columns ++ Seq(("name", name))
    }
  }

  "#columns" should "return columns" in {
    val t1 = TestDTO("test", None)
    val columns1 = t1.columns
    columns1.length shouldBe 1
    columns1 shouldBe List(("name", "test"))

    val t2 = TestDTO("test", Some(0))
    val columns2 = t2.columns
    columns2.length shouldBe 2
    columns2 shouldBe List(("id",0),("name", "test"))
  }

  "#id" should "return id" in {
    val t = TestDTO("test", Some(123))
    t.id.get shouldBe 123
  }

}
