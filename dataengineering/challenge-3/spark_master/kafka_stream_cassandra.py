from pyspark.sql import SparkSession
from pyspark.sql.types import StructType, StructField, StringType, BooleanType
from pyspark.sql.functions import  countDistinct,col, to_timestamp, count, from_json, window
import os

cassandra_username = str(os.environ.get('CASSANDRA_USERNAME'))
cassandra_password = str(os.environ.get('CASSANDRA_PASSWORD'))
cassandra_key_space = str(os.environ.get('CASSANDRA_KEY_SPACE'))
cassandra_host = str(os.environ.get('CASSANDRA_HOST'))
cassandra_port = str(os.environ.get('CASSANDRA_PORT'))

kafka_topic = str(os.environ.get('KAFKA_TOPIC'))
kafka_host_port = str(os.environ.get('KAFKA_HOST_PORT'))

def writeToCassandra(writeDF,table):
  writeDF.write \
    .format("org.apache.spark.sql.cassandra")\
    .mode('append')\
    .options(table=table, keyspace=cassandra_key_space)\
    .save()

spark = SparkSession \
        .builder \
        .appName("tweet_batch_stream") \
        .config("spark.driver.host", "localhost")\
        .getOrCreate()

spark = SparkSession \
        .builder \
        .appName("SparkStructuredStreaming") \
        .config("spark.cassandra.connection.host",cassandra_host)\
        .config("spark.cassandra.connection.port",cassandra_port)\
        .config("spark.cassandra.auth.username",cassandra_username)\
        .config("spark.cassandra.auth.password",cassandra_password)\
        .config("spark.driver.host", "localhost")\
        .getOrCreate()

spark.sparkContext.setLogLevel("ERROR")

raw = spark \
      .readStream \
      .format("kafka") \
      .option("kafka.bootstrap.servers", kafka_host_port) \
      .option("startingOffsets", "earliest")\
      .option("subscribe", kafka_topic) \
      .load() 

tweet_schema = StructType(
          [
              StructField("id", StringType(), True),
              StructField("author_id", StringType(), True),
              StructField("timestamp", StringType(), True),
              StructField("city", StringType(), True),
              StructField("is_retweet", BooleanType(), True)
          ]
      )

df = raw.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
df1 = df.withColumn("data", from_json("value", tweet_schema)).select(col('data.*'))
df1 = df1.withColumn("times", to_timestamp("timestamp", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).drop("timestamp")


def get_tweetcount_df(df1):
    df_tweetcount = df1 \
                    .groupBy(["city", window("times", '5 minutes').alias("win")]) \
                    .agg(count("id").alias("count")) \
                    .withColumn("date", col("win.start")) \
                    .drop("win")
    return df_tweetcount

def get_retweetcount_df(df1):
    df_retweetcount= df1 \
                    .filter("is_retweet==True") \
                    .groupBy(["city", window("times", '5 minutes').alias("win")]) \
                    .agg(count("id").alias("count")) \
                    .withColumn("date", col("win.start")) \
                    .drop("win")
    return df_retweetcount         

def get_usercount_df(df1):
    df_usercount=df1 \
                    .groupBy(["city", window("times", '5 minutes').alias("win")]) \
                    .agg(countDistinct("author_id").alias("count")) \
                    .withColumn("date", col("win.start")) \
                    .drop("win")    
    return df_usercount



def stream_batch(df1,_):
    df_tweetcount = get_tweetcount_df(df1)
    writeToCassandra(df_tweetcount,table='tweets')

    df_retweetcount = get_retweetcount_df(df1)
    writeToCassandra(df_retweetcount,table='retweets')

    df_usercount = get_usercount_df(df1)
    writeToCassandra(df_usercount,table='uniqueusers')


df1.writeStream \
    .foreachBatch(stream_batch) \
    .outputMode("update") \
    .start()\
    .awaitTermination()\
    .trigger('1 minute')
