import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
  val dispatchVersion = "0.7.8"
  val dispatchTwitter = "net.databinder" %% "dispatch-twitter" % dispatchVersion withSources
  val dispatchHttp = "net.databinder" %% "dispatch-http" % dispatchVersion withSources
  val dispatchJson = "net.databinder" %% "dispatch-json" % dispatchVersion withSources
  val dispatchOauth = "net.databinder" %% "dispatch-oauth" % dispatchVersion withSources
  val jodaTime = "joda-time" % "joda-time" % "1.6.2" withSources

  val specs = "org.scala-tools.testing" %% "specs" % "1.6.7" % "test" withSources ()
}
