from pyspark.sql import SparkSession
from pyspark.sql.functions import col, count, countDistinct, from_json, to_timestamp, window
from pyspark.sql.types import BooleanType, LongType, StringType, StructType, TimestampType

from pyspark.sql.window import Window
import pyspark.sql.functions as func
import os

# Should be externalized
os.environ["JAVA_HOME"] = "/usr/lib/jvm/java-1.8-openjdk"
DB_DRIVER =  os.getenv('DB_DRIVER')
DB_URL = os.getenv('DB_URL')
KAFKA_BROKERS = os.getenv('KAFKA_BROKERS')
KAFKA_TOPIC = os.getenv('KAFKA_TOPIC')

spark = SparkSession \
    .builder \
    .appName("MetricsGenerator") \
    .config("spark.sql.legacy.timeParserPolicy", "LEGACY")  \
    .config("spark.sql.streaming.forceDeleteTempCheckpointLocation", "true") \
    .config("spark.jars", "mysql-connector-java-8.0.26.jar,spark-sql-kafka-0-10_2.12-3.0.0.jar,spark-streaming-kafka-0-10_2.12-3.0.0.jar,kafka-clients-2.8.0.jar,spark-token-provider-kafka-0-10_2.12-3.0.0.jar,commons-pool2-2.8.0.jar") \
    .getOrCreate()

def writeToDB(batchDF, epochId):
    batchDF.persist()

    #batchDF.show()
    resultDF = batchDF \
      .groupBy(["location", window("created_at", "5 minutes").alias("start_date")]) \
      .agg(count('id').alias('total')) \
      .withColumn("start_date", col("start_date.start"))

    resultDF.write \
        .format("jdbc") \
        .option("driver", DB_DRIVER) \
        .option("url", DB_URL) \
        .option("dbtable", "tweets") \
      .mode("append") \
    .save()

    userDF = batchDF \
      .groupBy(["location", window("created_at", "5 minutes").alias("start_date")]) \
      .agg(countDistinct('user').alias('total')) \
      .withColumn("start_date", col("start_date.start"))

    userDF.write \
        .format("jdbc") \
        .option("driver", DB_DRIVER) \
        .option("url", DB_URL) \
        .option("dbtable", "users") \
      .mode("append") \
    .save()

    retweets = batchDF \
      .filter("is_retweet == 'True'") \
      .groupBy(["location", window("created_at", "5 minutes").alias("start_date")]) \
      .agg(count('id').alias('total')) \
      .withColumn("start_date", col("start_date.start"))

    retweets.write \
        .format("jdbc") \
        .option("driver", DB_DRIVER) \
        .option("url", DB_URL) \
        .option("dbtable", "retweets") \
      .mode("append") \
    .save()

    batchDF.unpersist()

def subscribeKafkaTopic():
  df = spark \
  .readStream \
  .format("kafka") \
  .option("kafka.bootstrap.servers", KAFKA_BROKERS) \
  .option("subscribe", KAFKA_TOPIC) \
  .load()

  df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
  schema = StructType() \
    .add("id", LongType()) \
    .add("user", LongType()) \
    .add("time", StringType()) \
    .add("location", StringType()) \
    .add("is_retweet", BooleanType())

  resultDF = df.select( \
    col("key").cast("string"),
    from_json(col("value").cast("string"), schema).alias("payload"))

  resultDF = resultDF.select(col("payload.*"))
  resultDF = resultDF.withColumn('created_at', to_timestamp(col("time"), "EEE MMM dd HH:mm:ss ZZZZ yyyy"))

  return resultDF

def main():
    print("Metrics generator started")
    resultDF = subscribeKafkaTopic()

    resultDF \
      .writeStream \
      .trigger(processingTime='5 minutes') \
      .foreachBatch(writeToDB) \
      .outputMode("update") \
      .start() \
      .awaitTermination()

if __name__ == "__main__":
    main()