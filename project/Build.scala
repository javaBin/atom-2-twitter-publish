import sbt._
import sbt.Keys._
import sbtrelease.Release._
import sbtrelease.ReleasePart
import sbtrelease.ReleaseKeys._

object Atom2TwitterPublish extends Build {

  lazy val buildSettings = Defaults.defaultSettings ++ releaseSettings ++ Seq(
    organization := "no.java",
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
    },
   credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
   publishSetting
  ) ++ mavenCentralFrouFrou

  lazy val publishSetting = publishTo <<= (version) { version: String =>
    if (version.trim.endsWith("SNAPSHOT"))
      Some(Resolvers.sonatypeNexusSnapshots)
    else
      Some(Resolvers.sonatypeNexusStaging)
  }

  // Things we care about primarily because Maven Central demands them
  lazy val mavenCentralFrouFrou = Seq(
    homepage := Some(new URL("http://wiki.java.no")),
    startYear := Some(2010),
    licenses := Seq(("Apache 2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))),
    pomExtra <<= (pomExtra, name, description) {(pom, name, desc) => pom ++ xml.Group(
      <scm>
        <url>https://github.com/javaBin/atom-2-twitter-publish</url>
        <connection>scm:git:git://github.com/javaBin/atom-2-twitter-publish.git</connection>
        <developerConnection>scm:git:git@github.com:javaBin/atom-2-twitter-publish.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <id>trygvis</id>
          <name>Trygve Laugstøl</name>
          <url>http://twitter.com/trygvis</url>
        </developer>
      </developers>
    )}
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

object Resolvers {
  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val sonatypeNexusStaging = "Sonatype Nexus Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
}
