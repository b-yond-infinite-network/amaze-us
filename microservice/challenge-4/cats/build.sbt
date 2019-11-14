import Dependencies._

ThisBuild / scalaVersion := "2.12.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "cats",
    libraryDependencies ++= Seq(scalaTest % Test, kafka, sparkCore, sparkStreaming, sparkStreamingKafka),
    dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7"  //override required as there is a version incompatibility between the Kafka/Spark version I am using
  )
