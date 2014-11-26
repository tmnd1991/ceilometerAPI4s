name := "ceilometerapiwrapper"

resolvers += "Big Bee Consultants" at "http://repo.bigbeeconsultants.co.uk/repo"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.1"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

libraryDependencies ++= Seq(
  "uk.co.bigbeeconsultants" %% "bee-client" % "0.21.+" withSources(),
  "org.slf4j" % "slf4j-api" % "1.7.+",
  "ch.qos.logback" % "logback-core"    % "1.0.+",
  "ch.qos.logback" % "logback-classic" % "1.0.+"
)

scalacOptions += "-feature"

version := "0.2"

organization := "it.unibo.ing.smacs"
