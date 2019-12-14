import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "us.3qe"
ThisBuild / organizationName := "windymelt"

val elastic4sVersion = "7.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "dumpfav",
    resolvers += Resolver.sonatypeRepo("releases"),
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.danielasfregola" %% "twitter4s" % "6.2",
    libraryDependencies += "com.lihaoyi" %% "upickle" % "0.8.0",
    libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
    // for the default http client
    libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % elastic4sVersion,
    fork in run := true
  )


// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
