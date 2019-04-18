name := "kafka-workshop"

version := "0.1"

scalaVersion := "2.12.8"

val apacheKafkaV = "2.2.0"
val akkaStreamsKafkaV = "1.0.1"
val circeV = "0.11.1"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients",
  "org.apache.kafka" % "kafka-streams",
  "org.apache.kafka" %% "kafka-streams-scala",
).map(_ % apacheKafkaV)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-kafka" % akkaStreamsKafkaV
)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeV)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.typelevel" %% "cats-effect" % "1.2.0"
)