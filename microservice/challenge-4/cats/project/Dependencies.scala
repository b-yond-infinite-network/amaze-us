import sbt._

object Dependencies {
  val sparkVersion = "2.4.0"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val kafka = "org.apache.kafka" %% "kafka" % "2.1.0"
  lazy val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion
  lazy val sparkStreaming = "org.apache.spark" %% "spark-streaming" % sparkVersion
  lazy val sparkStreamingKafka = "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion
}
