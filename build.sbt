name := "ceilometerapiwrapper"

resolvers += "Big Bee Consultants" at "http://repo.bigbeeconsultants.co.uk/repo"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.1"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

libraryDependencies += "uk.co.bigbeeconsultants" %% "bee-client" % "0.28" withSources()

scalacOptions += "-feature"

version := "0.2"

organization := "it.unibo.ing.smacs"
