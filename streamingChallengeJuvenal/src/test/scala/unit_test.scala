import KPI_processor._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._
import org.apache.spark.sql.functions.lit
import com.holdenkarau.spark.testing._

trait SparkSessionProvider {
  val ss = SparkSession.builder
    .master("local[*]")
    .getOrCreate()
}

class unit_test extends AnyFlatSpec with SparkSessionProvider with DataFrameSuiteBase {

  it should ("instanciate a Spark Streaming Context") in {

    var env: Boolean = true
   // val ss = getSparkStreamingContext(sst, 300)

  }

}