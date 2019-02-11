name := "ch4"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions ++= Seq("-Ypartial-unification", "-Ypatmat-exhaust-depth", "30")

libraryDependencies += "org.tpolecat" %% "doobie-hikari" % "0.6.0"
libraryDependencies += "org.tpolecat" %% "doobie-core" % "0.6.0"
libraryDependencies += "org.tpolecat" %% "doobie-postgres" % "0.6.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
