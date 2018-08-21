name := "mysteries-of-the-cats"

scalaVersion := "2.11.11"

val sparkVersion = "2.2.0"

libraryDependencies ++= Seq(
  "org.apache.spark"  %% "spark-core"                 % sparkVersion % "provided",
  "org.apache.spark"  %% "spark-sql"                  % sparkVersion % "provided",
  "org.apache.spark"  %% "spark-streaming"            % sparkVersion % "provided",
  "org.apache.spark"  %% "spark-streaming-kafka-0-10" % sparkVersion excludeAll(
    ExclusionRule(organization = "org.spark-project.spark", name = "unused"),
    ExclusionRule(organization = "org.apache.spark", name = "spark-streaming"),
    ExclusionRule(organization = "org.apache.hadoop")
  ),
  "com.typesafe"      %  "config"                     % "1.3.0",
  "com.iheart"        %% "ficus"                      % "1.4.3",
  "org.log4s"         %% "log4s"                      % "1.3.0",
  "com.typesafe.akka" %% "akka-actor"                 % "2.5.9",
  "com.typesafe.akka" %% "akka-testkit"               % "2.5.9"      % "test"

)

target in assembly := file("build")

assemblyJarName in assembly := s"${name.value}.jar"
