package example

/* Ideally this would be driven by config/cmdLine, but for simplicity I am using constants. */
object KafkaConstants {
  final val BOOTSTRAP_SERVERS = "localhost:9092" // Default host:port for Kafka
  final val GROUP_ID = "cats_group"
  final val TOPIC = "cats"
}
