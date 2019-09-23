package example

import example.Cats.Mood
import org.apache.spark.sql.{Dataset, SparkSession}

trait SparkAnalysis {
  lazy val spark = SparkSession
    .builder()
    .appName("Simple Application")
    .master( "local")
    .getOrCreate()
}

object CatAnalysis {

  def countMoods(ds: Dataset[String]): Array[(Mood.Value, Long)] =
    Mood.values
      .map(x => Tuple2(x, ds.filter(line => line.contains(x.toString)).count()))
      .toArray
      .sortBy(f => f._2)(Ordering[Long].reverse)

  def format(moods: Array[(Mood.Value, Long)]): String =
    moods.map(x => s"${x._2}: ${x._1}")
      .reduce((x, y) => x + "\n" + y)
}
