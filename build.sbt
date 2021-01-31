ThisBuild / scalaVersion := "2.12.12"
ThisBuild / organization := "ping"

lazy val app = (project in file ("."))
  .settings(
    name := "App",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
  )
