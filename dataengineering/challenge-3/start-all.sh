sed -i -e 's/\r$//' ./scripts/cassandra_init.sh
nohup docker-compose up >> output.log &
echo 'Staring services, and sleeping for 20 seconds'
sleep 20
# change retention period for topics
#docker exec evilnet-kafka-1-1 kafka-configs --bootstrap-server localhost:9092 --alter --entity-type topics --entity-name evilnet-tweet-info --add-config retention.ms=86400000,retention.bytes=-1 
#docker exec evilnet-kafka-2-1 kafka-configs --bootstrap-server localhost:9092 --alter --entity-type topics --entity-name evilnet-retweet-info --add-config retention.ms=86400000,retention.bytes=-1
