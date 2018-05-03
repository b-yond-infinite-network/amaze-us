name := "cats-and-moods"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.0"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.7"
libraryDependencies += "org.apache.spark" %% "spark-catalyst" % "2.3.0"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
