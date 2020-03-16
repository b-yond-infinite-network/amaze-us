ThisBuild / scalaVersion := "2.12.10"
ThisBuild / organization := "com.cats"

lazy val hello = (project in file("."))
  .settings(
    name := "Mysteries of the Cats",
    libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.19"
  )
