import os
import logging
from pyspark.sql import SparkSession
from pyspark.sql.functions import from_json, col, to_timestamp, count, countDistinct, window
from pyspark.sql.types import StructType, StructField, StringType, BooleanType


logger = logging.getLogger('processor')
logger.setLevel(logging.DEBUG) # one selection here used for aavaa-app
logger.propagate = False  # don't propagate (in case of multiple imports)
formatter = logging.Formatter('%(asctime)s | %(message)s')

file_handler = logging.FileHandler("processor.log", "w+")
file_handler.setLevel(logging.DEBUG)
file_handler.setFormatter(formatter)
logger.addHandler(file_handler)


"""os.environ["APP_NAME"] = "evilnet"
os.environ["KAFKA_TOPIC"] = "tweets"
os.environ["KAFKA_BROKER"] = "localhost:9093"
os.environ["TIME_WIN"] = "15 minutes"
os.environ["PROC_TIME"] = "50 seconds"
os.environ["DB_URL"] = "jdbc:mysql://localhost:3306/evil_tweets?user=root&password=evil"
os.environ["DB_DRIVER"] = "com.mysql.cj.jdbc.Driver"
os.environ["PYSPARK_SUBMIT_ARGS"] = "--master local[3] pyspark-shell"
"""

app_name = os.environ.get("APP_NAME")
kafka_topic = os.environ.get("KAFKA_TOPIC")
kafka_broker = os.environ.get("KAFKA_BROKER")
time_window = os.environ.get("TIME_WIN")
processing_time = os.environ.get("PROC_TIME")
database_driver = os.environ.get("DB_DRIVER")
database_url = os.environ.get("DB_URL")


def start_spark():
    spark = SparkSession \
        .builder \
        .config("spark.sql.legacy.timeParserPolicy", "LEGACY")  \
        .config("spark.sql.streaming.forceDeleteTempCheckpointLocation", "true") \
        .config("spark.jars", "mysql-connector-java-8.0.26.jar,spark-sql-kafka-0-10_2.12-3.0.0.jar,spark-streaming-kafka-0-10_2.12-3.0.0.jar,kafka-clients-2.8.0.jar,spark-token-provider-kafka-0-10_2.12-3.0.0.jar,commons-pool2-2.8.0.jar") \
        .getOrCreate()
            
    df = spark.readStream \
        .format("kafka") \
        .option("kafka.bootstrap.servers", kafka_broker) \
        .option("subscribe", kafka_topic) \
        .load()
        
    df = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
    
    schema = StructType(
            [
                StructField("id", StringType(), True),
                StructField("author_id", StringType(), True),
                StructField("created_at", StringType(), True),
                StructField("is_retweet", BooleanType(), True),
                StructField("place", StringType(), True)
            ]
        )
    
    ds = df.withColumn("data", from_json("value", schema)).select(col('data.*'))
    ds = ds.withColumn("timestamp", to_timestamp("created_at", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).drop("created_at")
    
    return ds


def process_batch(df, batch_id):
    logger.info(f"Data batch #{batch_id} with size {df.count()}, was received!")
    
    count_df = df \
        .groupBy(["place", window("timestamp", time_window).alias("time_bin")]) \
        .agg(count("id").alias("count")) \
        .withColumn("time", col("time_bin.start")) \
        .drop("time_bin")
    
    user_df = df \
        .groupBy(["place", window("timestamp", time_window).alias("time_bin")]) \
        .agg(count("author_id").alias("count")) \
        .withColumn("time", col("time_bin.start")) \
        .drop("time_bin")
    
    retweet_df = df \
        .filter("is_retweet == True") \
        .groupBy(["place", window("timestamp", time_window).alias("time_bin")]) \
        .agg(count("id").alias("count")) \
        .withColumn("time", col("time_bin.start")) \
        .drop("time_bin")
    
    save_proc_batch(count_df, table_name="tweets")
    save_proc_batch(user_df, table_name="users")
    save_proc_batch(retweet_df, table_name="retweets")


def save_proc_batch(df, table_name):
    df.write \
        .format("jdbc") \
        .option("driver", database_driver) \
        .option("url", database_url) \
        .option("dbtable", table_name) \
      .mode("append") \
    .save()
    
    
def main():
    data_stream = start_spark()
    query = data_stream.writeStream \
        .foreachBatch(process_batch) \
        .trigger(processingTime=processing_time) \
        .start() \
        .awaitTermination()
    

if __name__ == "__main__":
    main()