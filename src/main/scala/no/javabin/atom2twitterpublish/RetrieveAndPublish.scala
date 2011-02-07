package no.javabin.atom2twitterpublish

import dispatch._
import Http._
import json._
import JsHttp._
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

class Atom2TwitterSync(atomFeedUri: String, twitterHandle: String, twitterPass: String) {
  val http = new Http()

  var lastTweet: DateTime = null

  val created_at = 'created_at ? str

  for {
    item <- http(Status(twitterHandle).timeline)
  } println("### text: " + Status.text(item) + " created: " + created_at(item))


  def update() {
    http(atomFeedUri <> {
      elem =>
        for {entry <- elem \\ "entry"
             category <- entry \\ "category"
             term <- category \ "@term" if term == "Twitter"} println(entry)
    })
  }

}
