name := """play-pin"""

version := "1.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.14",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies += "net.kaliber" %% "play-s3" % "8.0.0"
libraryDependencies += "com.amazonaws" % "aws-java-sdk-osgi" % "1.11.39"