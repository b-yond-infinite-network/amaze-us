import Dependencies._

// Spark requires scalaVersion < 2.13
ThisBuild / scalaVersion     := "2.12.9"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val sparkVersion = "2.4.4"

lazy val root = (project in file("."))
  .settings(
      // Generator
      name := "moodycats",

      libraryDependencies ++= Seq (
          "com.typesafe.akka" %% "akka-actor" % "2.5.25",
          "com.typesafe.akka" %% "akka-stream" % "2.5.25",
          "com.typesafe.akka" %% "akka-testkit" % "2.5.25" % Test,
          "org.apache.spark" %% "spark-core" % sparkVersion,
          "org.apache.spark" %% "spark-sql" % sparkVersion,
          "org.apache.spark" %% "spark-streaming" % sparkVersion,
          scalaTest % Test,
          "org.specs2" %% "specs2-core" % "4.6.0" % Test
      ),

      scalacOptions in Test += "-Yrangepos",
  )
