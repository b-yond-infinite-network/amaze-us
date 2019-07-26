name := "catz-app"
organization := "c4catz"
version := "1.0"

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.23",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.23" % Test,
    "org.scalactic" %% "scalactic" % "3.0.8",
    "org.scalatest" %% "scalatest" % "3.0.8" % "test",
    "org.apache.kafka" % "kafka-clients" % "2.3.0")

enablePlugins(DockerPlugin)

dockerAutoPackageJavaApplication()
