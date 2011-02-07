package no.javabin.atom2twitterpublish

object RetrieveAndPublish {
  def main(args: Array[String]): Unit = {
    var it = new Atom2TwitterSync("http://wp.java.no/?atompub=list&pt=abc&pg=1", "test3141592654", "")
    it ! Atom2TwitterSync.Check
    Thread.sleep(3000)
    it ! Atom2TwitterSync.Shutdown
  }
}
