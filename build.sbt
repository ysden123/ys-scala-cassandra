import sbt.Keys.libraryDependencies

lazy val cassandraDriverCoreVersion = "3.3.0"
lazy val typesafeConfigVersion = "1.3.1"
lazy val scalatestVersion ="3.0.4"

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
    "org.scalatest" % "scalatest_2.12" % scalatestVersion % "test"
  )
)

lazy val ysScalaCassandra = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "ys-scala-cassandra"
  )