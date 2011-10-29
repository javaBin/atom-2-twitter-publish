import sbt._
import Keys._

object Atom2TwitterPublish extends Build {

  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "no.arktekk.atom-client",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.9.1",
    crossScalaVersions := Seq("2.9.1") // Seq("2.9.0", "2.9.1"),
  )

  val dispatchVersion = "0.7.8"
  val dispatchTwitter = "net.databinder" %% "dispatch-twitter" % dispatchVersion withSources
  val dispatchHttp = "net.databinder" %% "dispatch-http" % dispatchVersion withSources
  val dispatchJson = "net.databinder" %% "dispatch-json" % dispatchVersion withSources
  val dispatchOauth = "net.databinder" %% "dispatch-oauth" % dispatchVersion withSources
  val jodaTime = "joda-time" % "joda-time" % "1.6.2" withSources
  val specs = "org.specs2" %% "specs2" % "1.6.1" % "test" withSources ()

  lazy val root = Project(
    id = "atom-2-twitter-publish",
    base = file("."),
    settings = buildSettings ++ Seq(
      description := "Atom 2 Twitter Publish",
      libraryDependencies := Seq(
        dispatchTwitter,
        dispatchHttp,
        dispatchJson,
        dispatchOauth,
        jodaTime,
        specs
      )
    )
  )
}
