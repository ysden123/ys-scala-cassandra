/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cassandra2.model


object Data {

  /**
    * Cassandra data type and it's converter
    * @tparam T specifies the value type
    */
  trait CassandraDataType[T] {
    /**
      * Converts a ''value'' to ''T'' type
      *
      * @param value the value to convert
      * @return converted value
      */
    def convert(value: Any): T
  }

  /**
    * Implicit converters for Int
    */
  implicit val IntCassandraType = new CassandraDataType[Int] {
    /**
      * Converts a ''value'' to ''T'' type
      *
      * @param value the value to convert
      * @return converted value
      */
    override def convert(value: Any) = value match {
      case Some(x: Int) => x
      case Some(x: Long) => x.toInt
      case Some(x: Double) => x.toInt
      case Some(x: Float) => x.toInt
      case Some(x: Any) => x.toString.toInt
      case None => 0
      case x: Int => x
      case x: Long => x.toInt
      case x: Double => x.toInt
      case x: Float => x.toInt
      case x => x.toString.toInt
    }
  }

  /**
    * Cassandra property
    * @tparam T the property value type
    */
  trait CassandraProperty[T] {
    /**
      * The property name
      */
    val name: String

    /**
      * Converts a ''value'' to ''T'' type
      *
      * @param value the value to convert
      * @return converted value
      */
    def convert(value: Any): T
  }

  /**
    * Cassandra attribute
    * @param name the attribute name
    * @param t the attribute value type
    * @tparam T the property value type
    */
  case class CassandraAttribute[T](name: String)(implicit t: CassandraDataType[T]) extends CassandraProperty[T] {
    /**
      * Converts a ''value'' to ''T'' type
      *
      * @param value the value to convert
      * @return converted value
      */
    override def convert(value: Any): T = t.convert(value)
  }

}