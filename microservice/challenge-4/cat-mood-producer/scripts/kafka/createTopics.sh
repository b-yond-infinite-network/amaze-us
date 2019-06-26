./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 50 --topic cats

Useful commands
./kafka-topics.sh --describe --zookeeper localhost:2181

Startup:

Start ZK:
sudo bin/zookeeper-server-start.sh config/zookeeper.properties

Start kafka:

sudo bin/kafka-server-start.sh config/server.properties

Consume
sudo ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic cats