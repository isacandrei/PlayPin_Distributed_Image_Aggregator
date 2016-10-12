name := """play-pin"""

version := "1.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

val PhantomVersion = "1.29.3"

resolvers ++= Seq(
    Resolver.typesafeRepo("releases"),
    Resolver.sonatypeRepo("releases"),
    Resolver.bintrayRepo("websudos", "oss-releases")
)

libraryDependencies ++= Seq(
    jdbc,
    cache,
    ws,
    "net.liftweb" %% "lift-json" % "2.6.2",
    "com.websudos" %% "phantom-dsl" % PhantomVersion,
    "com.websudos" %% "phantom-reactivestreams" % PhantomVersion,
    "com.amazonaws" % "aws-java-sdk-osgi" % "1.11.39"
)
