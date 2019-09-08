import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
        name := "moodycats",
        libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.25",
        libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.25",

        libraryDependencies += scalaTest % Test,
        libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.25" % Test,
        libraryDependencies += "org.specs2" %% "specs2-core" % "4.6.0" % Test,

        scalacOptions in Test += "-Yrangepos"
  )


// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.

