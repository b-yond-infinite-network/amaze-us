name := "catz-broker"
organization := "c4catz"
version := "1.0"

scalaVersion := "2.11.0"

val sparkVersion = "2.4.3"

libraryDependencies ++= Seq(
    "com.typesafe" % "config" % "1.3.4",
    "org.apache.kafka" % "kafka-clients" % "2.3.0",
    "org.apache.cassandra" % "cassandra-all" % "3.11.4",
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-sql" % sparkVersion,
    "org.apache.spark" %% "spark-streaming" % sparkVersion,
    "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
    "com.datastax.spark" %% "spark-cassandra-connector" % "2.4.1"
).map(_.exclude("org.slf4j", "slf4j-log4j12"))


enablePlugins(DockerPlugin)

dockerAutoPackageJavaApplication()
