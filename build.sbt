name := "smacsSAB"

resolvers ++= Seq("clojars" at "http://clojars.org/repo/", "clojure-releases" at "http://build.clojure.org/releases")

resolvers += "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/"

libraryDependencies += "org.apache.storm" % "storm-core" % "0.9.2-incubating" % "provided" exclude("junit", "junit")

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.0"

libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

scalacOptions += "-feature"