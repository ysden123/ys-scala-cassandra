/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.model

import org.scalatest.{FlatSpec, Matchers}

/** Unit tests for ColumnDefinition
  *
  * @author Yuriy Stul
  */
class ColumnDefinitionTest extends FlatSpec with Matchers {

  behavior of "ColumnDefinitionTest"

  "#colType" should "return colType" in {
    ColumnDefinition("the name", ColType.INT).colType shouldBe ColType.INT
  }

  "#name" should "return name" in {
    ColumnDefinition("the name", ColType.INT).name shouldBe "the name"
  }

  "#primaryKey" should " return primaryKey" in {
    val c1 = ColumnDefinition("the name", ColType.TEXT)
    c1.primaryKey shouldBe false
    val c2 = ColumnDefinition("the name", ColType.TEXT, primaryKey = true)
    c2.primaryKey shouldBe true
  }

}
