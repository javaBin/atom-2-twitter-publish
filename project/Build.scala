import sbt._
import sbt.Keys._
import sbtrelease.Release._
import sbtrelease.ReleasePart
import sbtrelease.ReleaseKeys._

object Atom2TwitterPublish extends Build {

  lazy val buildSettings = Defaults.defaultSettings ++ releaseSettings ++ Seq(
    organization := "no.javabin",
    scalaVersion := "2.9.1",
    releaseProcess <<= thisProjectRef apply { ref =>
      import sbtrelease.ReleaseStateTransformations._
      Seq[ReleasePart](
        initialGitChecks,
        checkSnapshotDependencies,
        inquireVersions,
        runTest,
        setReleaseVersion,
        commitReleaseVersion,
        tagRelease,
      // Enable when we're deploying to Sonatype
//        releaseTask(publish in Global in ref),
        setNextVersion,
        commitNextVersion
      )
    }
  )

  val dispatchVersion = "0.7.8"
  val dispatchTwitter = "net.databinder" %% "dispatch-twitter" % dispatchVersion withSources
  val dispatchHttp = "net.databinder" %% "dispatch-http" % dispatchVersion withSources
  val dispatchJson = "net.databinder" %% "dispatch-json" % dispatchVersion withSources
  val dispatchOauth = "net.databinder" %% "dispatch-oauth" % dispatchVersion withSources
  val jodaTime = "joda-time" % "joda-time" % "1.6.2" withSources
  val specs = "org.specs2" %% "specs2" % "1.6.1" % "test" withSources ()

  lazy val root = Project(
    id = "atom2twitterpublisher",
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
