import os
import time
time.sleep(80)


os.system('spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.0.0,com.datastax.spark:spark-cassandra-connector_2.12:3.0.0 /home/kafka_stream_cassandra.py')
