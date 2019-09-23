package example

import org.apache.log4j._

import example.CatAnalysis._

object AnalyticsApp extends SparkAnalysis {

  def main(args: Array[String]): Unit = {

    // This is sketchy, but I don't want to pollute the console output (the stats)
    // with all the spewed logs from Spark.
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    Logger.getLogger("spark").setLevel(Level.OFF)

    val config = args.toList match {
      case filePath :: Nil =>
        if (scala.reflect.io.File(filePath).exists) Right(filePath)
        else Left(s"File does not exist at path $filePath")
      case _ =>
        Left("Expecting a file path containing all the moodswings")
    }

    config
      .fold(println,
        filepath => {

          val logData = spark.read.textFile(filepath).cache()
          val total = logData.count()
          val moodsStats = countMoods(logData)
          val formattedMoods = format(moodsStats)

          println(s"Total moods: $total")
          println(formattedMoods)
        })
  }
}
