import sbt.Keys.libraryDependencies

lazy val cassandraDriverCoreVersion = "3.3.0"
lazy val typesafeConfigVersion = "1.3.1"
lazy val scalatestVersion = "3.0.4"
lazy val slf4jVersion = "1.7.25"
lazy val scalaLoggingVersion = "3.5.0"
lazy val loggingVersion="2.7"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.3",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.datastax.cassandra" % "cassandra-driver-core" % cassandraDriverCoreVersion,
    "com.typesafe" % "config" % typesafeConfigVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
    "org.scalatest" % "scalatest_2.12" % scalatestVersion % "test"
  )
)

lazy val ysScalaCassandra = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "ys-scala-cassandra"
  )

parallelExecution in Test := false