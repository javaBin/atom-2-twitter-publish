package no.javabin.atom2twitterpublish

object RetrieveAndPublish {
  def main(args: Array[String]): Unit = {
    args match {
      case Array(atomFeedUri, twitterSource, consumerKey, consumerSecret, accessToken, accessSecret, twitterHandle) =>
        var it = new Atom2TwitterSync(
          atomFeedUri, twitterSource, consumerKey, consumerSecret, accessToken, accessSecret, twitterHandle)
        it ! Atom2TwitterSync.Check
        it ! Atom2TwitterSync.Shutdown
      case _ =>
        println("Usage: RetrieveAndPublish <atom feed URI> <twitter source> <consumer key> <consumer secret>" +
                " <access token> <access secret> <twitter handle>")
    }
  }
}
