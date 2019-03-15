
organization := "com.test"
name := "cats"

scalaVersion := "2.12.8"
scalacOptions ++= Seq("-encoding", "UTF-8", "-unchecked", "-deprecation", "-feature", "-Xlint", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.27",
  "org.scalaz" %% "scalaz-zio" % "0.9",

  "com.github.tototoshi" %% "scala-csv" % "1.3.5",

  "org.specs2" %% "specs2-core" % "4.4.1" % Test
)

fork in Test := true
parallelExecution in Test := false
