package com.byond.challenge4

import com.byond.challenge4.udaf.MedianUDAF
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object Stats {

  private[this] val MOOD_COLUMN_NAME: String  = "mood"
  private[this] val COUNT_COLUMN_NAME: String = "count"

  @transient
  private[this] lazy val median = new MedianUDAF(COUNT_COLUMN_NAME)

  def generateStats(cats: DataFrame): DataFrame = {
    cats
      .groupBy(MOOD_COLUMN_NAME)
      .agg(count(MOOD_COLUMN_NAME).alias(COUNT_COLUMN_NAME))
      .agg(
        avg(MOOD_COLUMN_NAME).alias("Average"),
        mean(MOOD_COLUMN_NAME).alias("Mean"),
        median(col(MOOD_COLUMN_NAME)).alias("Median"),
        variance(MOOD_COLUMN_NAME).alias("Variance")
      )

  }

}
