/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.util

import java.util.concurrent.TimeUnit

import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/** Unit tests for DbUtils
  *
  * @author Yuriy Stul
  */
class DbUtilsTest extends FlatSpec with Matchers {
  val UNIT_TEST_KEY_SPACE = "ys_unit_test_2"
  behavior of "DbUtilsTest"

  it should "createKeySpace" in {
    val f = DbUtils.createKeySpace(UNIT_TEST_KEY_SPACE)
    f.onComplete{
      case Success(_)=>
        succeed
      case Failure(err)=>
        fail(err.getMessage)
    }
    Await.ready(f, Duration(10, TimeUnit.SECONDS))
  }

  it should "deleteKeySpace" in {
    val f = DbUtils.deleteKeySpace(UNIT_TEST_KEY_SPACE)
    f.onComplete{
      case Success(_)=>
        succeed
      case Failure(err)=>
        fail(err.getMessage)
    }
    Await.ready(f, Duration(20, TimeUnit.SECONDS))
  }
}
