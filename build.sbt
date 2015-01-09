name := "ceilometerapiwrapper"

version := "0.5"

organization := "it.unibo.ing.smacs"

scalacOptions += "-feature"

scalaVersion := "2.11.2"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.1" withSources()

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2"

libraryDependencies += "org.eclipse.jetty" % "jetty-client" % "9.3.0.M1"

//SOME UTILS
libraryDependencies += "it.unibo.ing" %% "utils" % "1.0" withSources() intransitive()
