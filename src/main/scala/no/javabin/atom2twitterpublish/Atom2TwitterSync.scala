package no.javabin.atom2twitterpublish

import dispatch.Http
import Http._
import dispatch.json._
import JsHttp._
import dispatch.twitter.Status
import org.joda.time.DateTime

class Atom2TwitterSync(atomFeedUri: String, twitterHandle: String, twitterPass: String) {
  val http = new Http()

  val created_at = 'created_at ? date
  object date extends Extract[DateTime] {
    def unapply(js: JsValue) = js match {
      case JsString(v) => Some(TwitterDateParse(v))
      case _ => None
    }
  }
  implicit object dateTimeOrdering extends Ordering[DateTime] {
    def compare(dt1: DateTime, dt2: DateTime) = dt1.compareTo(dt2)
  }

  var lastTweet: DateTime = (for {
    item <- http(Status(twitterHandle).timeline)
  } yield created_at(item)).sorted.lastOption.getOrElse(new DateTime(0L))

  println("Last tweet: " + lastTweet)

  def update() {
    http(atomFeedUri <> {
      elem =>
        for {entry <- elem \\ "entry"
             category <- entry \\ "category"
             term <- category \ "@term" if term == "Twitter"} println(entry)
    })
  }

}
