name := "simucat"

version := "0.1"

scalaVersion := "2.12.8"

// Akka actors
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.20"
// Cassandra driver
libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "3.0.0"