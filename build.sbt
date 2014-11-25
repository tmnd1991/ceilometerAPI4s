import SonatypeKeys._

sonatypeSettings

name := "ceilometerapiwrapper"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.1"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

scalacOptions += "-feature"

version := "0.2"

organization := "it.unibo.ing.smacs"

profileName := "tmnd91"