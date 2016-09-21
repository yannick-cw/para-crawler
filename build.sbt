name := "para-crawler"
version := "0.12-SNAPSHOT"
scalaVersion := "2.11.8"
libraryDependencies += "org.scalatest"  %% "scalatest"   % "2.2.4" % Test
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "1.0.0"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.9"
libraryDependencies += "org.apache.commons" % "commons-email" % "1.4"
libraryDependencies += "com.typesafe.akka" % "akka-http-spray-json-experimental_2.11" % "2.4.10"
libraryDependencies += "com.typesafe.akka" % "akka-http-testkit_2.11" % "2.4.10"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.0"

enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)
dockerRepository:=Some("rincewind373")

