name := """clowder"""
organization := "audela"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += ws

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.23",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.23" % Test,
  "com.typesafe.akka" %% "akka-stream-kafka" % "1.0.3",
  "org.apache.spark" %% "spark-core" % "2.4.3",
  "org.apache.spark" %% "spark-sql" % "2.4.3",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.3",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.9",
  "org.webjars" % "flot" % "0.8.3-1",
  "org.webjars" % "bootstrap" % "3.3.6",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test
)


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "audela.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "audela.binders._"
