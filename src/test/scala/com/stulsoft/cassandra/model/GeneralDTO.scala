/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra.model

/**
  * @author Yuriy Stul
  */
trait GeneralDTO {
  /**
    * Entity's ID
    * @return the entity ID
    */
  def id: Option[Any]

  /**
    * Returns a list of columns, where each element is (name, value)
    * @return the list of columns, where each element is (name, value)
    */
  def columns: Seq[(String, Any)] = {
    id match {
      case Some(x) => Seq(("id", x))
      case _ => Nil
    }
  }
}
