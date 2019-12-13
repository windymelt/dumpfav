import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "us.3qe"
ThisBuild / organizationName := "windymelt"

lazy val root = (project in file("."))
  .settings(
    name := "dumpfav",
    resolvers += Resolver.sonatypeRepo("releases"),
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.danielasfregola" %% "twitter4s" % "6.2",
    libraryDependencies += "com.lihaoyi" %% "upickle" % "0.8.0",
    fork in run := true
  )


// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
