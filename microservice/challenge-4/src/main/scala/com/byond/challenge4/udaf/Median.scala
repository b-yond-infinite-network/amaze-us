package com.byond.challenge4.udaf

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataTypes, DoubleType, LongType, StructType}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class MedianUDAF(columnName: String) extends UserDefinedAggregateFunction {

  def inputSchema: StructType = new StructType()
    .add(columnName, LongType)

  def bufferSchema: StructType = new StructType()
    .add("buffer", DataTypes.createArrayType(DataTypes.LongType, false))

  def dataType = DoubleType

  def deterministic = true

  def initialize(buffer: MutableAggregationBuffer) = {
    buffer.update(0, ListBuffer.empty[Long]);
  }

  def update(buffer: MutableAggregationBuffer, input: Row) = {
    val temp = new ListBuffer[Long]()
    temp ++= buffer.getAs[List[Long]](0)
    temp += input.getAs[Long](0)
    buffer.update(0, temp)
  }

  def merge(buffer1: MutableAggregationBuffer, buffer2: Row) = {
    val temp = new ListBuffer[Long]()
    temp ++= buffer1.getAs[List[Long]](0)
    temp += buffer2.getAs[Long](0)
    buffer1.update(0, temp)
  }

  def evaluate(buffer: Row) = {
    val temp = new ListBuffer[Long]()
    temp ++= buffer
      .getAs[mutable.WrappedArray[List[Long]]](0).flatten

    val count = temp.size

    val sorted = temp
      .sortWith(_ < _)
      .zipWithIndex.map { case (v, idx) =>
      idx -> v
    }.toMap

    val median = if (count % 2 == 0 ) {
      val l = count / 2 - 1
      val r = l + 1
      (sorted(l) + sorted(r)).toDouble / 2
    }
    else {
      sorted(count / 2).toDouble
    }
    median
  }

}
