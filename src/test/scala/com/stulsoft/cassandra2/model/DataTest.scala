/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra2.model

import com.stulsoft.cassandra2.model.Data._
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Yuriy Stul
  */
class DataTest extends FlatSpec with Matchers {

  behavior of "Data"

  "IntCassandraType" should "support implicit conversions" in {
    val a = CassandraAttribute[Int]("index")
    a.convert(123) shouldBe 123
    a.convert(123.0) shouldBe 123
    a.convert(123.0F) shouldBe 123
    a.convert(123L) shouldBe 123
    a.convert("123") shouldBe 123
    a.convert(Some(123)) shouldBe 123
    a.convert(None) shouldBe 0
    a.name shouldBe "index"
  }

  "StringCassandraType" should "support implicit conversions" in {
    val a = CassandraAttribute[String]("text")
    a.convert(123) shouldBe "123"
    a.convert("123") shouldBe "123"
    a.convert(Some("123")) shouldBe "123"
    a.convert(None) shouldBe null
    a.name shouldBe "text"
  }
}
