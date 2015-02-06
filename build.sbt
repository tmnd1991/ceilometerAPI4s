name := "ceilometerapiwrapper"

version := "0.5"

organization := "it.unibo.ing.smacs"

scalacOptions += "-feature"

scalaVersion := "2.11.2"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.1" withSources()

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2"

//libraryDependencies += "org.eclipse.jetty" % "jetty-client" % "9.3.0.M1"
libraryDependencies += "org.eclipse.jetty" % "jetty-client" % "8.1.16.v20140903" withJavadoc()

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.5"

//SOME UTILS
libraryDependencies += "it.unibo.ing" %% "utils" % "1.0" withSources() intransitive()
