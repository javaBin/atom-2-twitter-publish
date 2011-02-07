package no.javabin.atom2twitterpublish

import dispatch._
import Http._
import oauth.{Token, Consumer, OAuth}
import org.joda.time.DateTime
import twitter.{Status, Twitter}

object RetrieveAndPublish {
  def main(args: Array[String]): Unit = {
    // old: http://wp.java.no/feed/atom/
    new Atom2TwitterSync("http://wp.java.no/?atompub=list&pt=abc&pg=1", "test3141592654", "")

    /*val consumer = Consumer(..., ...)
    val token = Token("test3141592654", "passord")
    http(Status.update("Heisann sveisann", consumer, token) ># {})*/
  }
}
