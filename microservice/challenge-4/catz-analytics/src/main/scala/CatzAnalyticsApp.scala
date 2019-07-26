import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

trait Config {
    lazy val config = ConfigFactory.load()
}


trait DbConfig extends Config {
    lazy val dbConfig = config.getConfig("cassandra")

    lazy val dbHost = dbConfig.getString("host")
    lazy val dbUser = dbConfig.getString("username")
    lazy val dbPassword = dbConfig.getString("password")
    lazy val keySpace = dbConfig.getString("keyspace")
    lazy val tableName = dbConfig.getString("table")
}


object CatzAnalyticsApp extends DbConfig {

    def findAverageMood(session: SparkSession): Unit = {
        val moodColumnName = "mood"

        session.read.format("org.apache.spark.sql.cassandra")
            .option("keyspace", keySpace)
            .option("table", tableName)
            .load().agg(avg(moodColumnName), variance(moodColumnName)).show()
    }

    def main(args: Array[String]): Unit = {
        val conf = new SparkConf(true).set("spark.cassandra.connection.host", dbHost)
            .set("spark.cassandra.auth.username", dbUser)
            .set("spark.cassandra.auth.password", dbPassword)
        val session = SparkSession.builder().config(conf).master("local").getOrCreate()

        findAverageMood(session)
    }

}
