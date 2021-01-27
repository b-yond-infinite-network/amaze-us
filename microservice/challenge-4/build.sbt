name := "clowder"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.h2database" % "h2" % "1.4.200",
  "org.scalaz" %% "scalaz-core" % "7.3.2",
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)

scalacOptions ++= Seq("-deprecation", "-feature")