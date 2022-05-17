import os
import time
time.sleep(90)
# add arg time window for stream

with open("/home/offset_config.txt") as file: 
    data = file.read() 
    conf = [int(i) for i in data if i.isdigit()][0]

os.system('echo 1 > /home/offset_config.txt')



if conf==0: # first start will be from earliest offset")
    print("starting from topic's earliest offset")
    os.system('spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.0.0,com.datastax.spark:spark-cassandra-connector_2.12:3.0.0 /home/earliest_kafka_stream_cassandra.py')
elif conf==1: # if any failure occurs the job will stream from the latest offset
    print("starting from topic's latest offset")
    os.system('spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.0.0,com.datastax.spark:spark-cassandra-connector_2.12:3.0.0 /home/latest_kafka_stream_cassandra.py')

