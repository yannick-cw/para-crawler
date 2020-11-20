name := "para-crawler"
version := "0.13"
scalaVersion := "2.13.4"
libraryDependencies += "org.scalatest"  %% "scalatest"   % "3.2.3" % Test
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.2.0"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.2.1"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.8"
libraryDependencies += "org.apache.commons" % "commons-email" % "1.5"
libraryDependencies += "de.heikoseeberger" %% "akka-http-circe" % "1.35.2"
libraryDependencies += "io.circe" %% "circe-generic" % "0.13.0"


enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)
dockerRepository:=Some("rincewind373")