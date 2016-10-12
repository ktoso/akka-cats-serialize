name := "akka-cats-serialize"

organization := "akka-cats-serialize"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

val AkkaVersion = "2.4.0"

resolvers += Resolver.typesafeRepo("releases")

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % AkkaVersion

libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % AkkaVersion

libraryDependencies += "org.typelevel"       %% "cats"                   % "0.7.2"

libraryDependencies += "com.esotericsoftware" % "kryo" % "4.0.0"

libraryDependencies += "com.github.romix.akka" %% "akka-kryo-serialization" % "0.4.1"
