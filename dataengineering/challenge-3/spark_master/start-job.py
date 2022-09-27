import os
from cassandra_check import block_until_cassandra_ok
import time


cassandra_username = str(os.environ.get('CASSANDRA_USERNAME'))
cassandra_password = str(os.environ.get('CASSANDRA_PASSWORD'))
cassandra_key_space = str(os.environ.get('CASSANDRA_KEY_SPACE'))
cassandra_host = str(os.environ.get('CASSANDRA_HOST'))
cassandra_port = str(os.environ.get('CASSANDRA_PORT'))

block_until_cassandra_ok(cassandra_username, cassandra_password, cassandra_key_space, cassandra_host, cassandra_port)

os.system('spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.0.0,com.datastax.spark:spark-cassandra-connector_2.12:3.0.0 /home/kafka_stream_cassandra.py')



