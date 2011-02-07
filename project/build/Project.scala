import sbt._

class Project(info: ProjectInfo) extends PluginProject(info) with IdeaProject {
  val dispatchVersion = "0.7.8"
  val dispatchTwitter = "net.databinder" %% "dispatch-twitter" % dispatchVersion withSources
  val dispatchHttp = "net.databinder" %% "dispatch-http" % dispatchVersion withSources
  val dispatchJson = "net.databinder" %% "dispatch-json" % dispatchVersion withSources
  val dispatchOauth = "net.databinder" %% "dispatch-oauth" % dispatchVersion withSources
  val jodaTime = "joda-time" % "joda-time" % "1.6.2" withSources
}
