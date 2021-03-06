package no.javabin.atom2twitterpublish

import dispatch.Http
import Http._
import dispatch.json._
import JsHttp._
import dispatch.twitter.Status
import org.joda.time.DateTime
import actors.Actor
import dispatch.oauth.{Token, Consumer}
import xml.Elem

object Atom2TwitterSync {
  object Check
  object Shutdown
}
class Atom2TwitterSync(atomFeedUri: String, twitterSource: String, consumerKey: String, consumerSecret: String,
                       accessToken: String, accessSecret: String, twitterHandle: String,
                       errorLogger: (String, Option[Throwable]) => Unit,
                       infoLogger: (String) => Unit) extends Actor {
  val http = new Http()
  val consumer = Consumer(consumerKey, consumerSecret)
  val access = Token(accessToken, accessSecret)

  val created_at = 'created_at ? date
  val source = 'source ? str
  object date extends Extract[DateTime] {
    def unapply(js: JsValue) = js match {
      case JsString(v) => Some(TwitterDateParse(v))
      case _ => None
    }
  }
  implicit object dateTimeOrdering extends Ordering[DateTime] {
    def compare(dt1: DateTime, dt2: DateTime) = dt1.compareTo(dt2)
  }

  def findLastTweet: DateTime = (for {
    item <- http(Status(twitterHandle).timeline)
    source <- List(source(item)) if source.contains(twitterSource)
  } yield {created_at(item)}).sorted.lastOption.getOrElse(new DateTime(0L))

  override def act = {
    loop {
      react {
        case Atom2TwitterSync.Check =>
          try {
            val lastTweet = findLastTweet
            infoLogger("Updating... last tweet: " + lastTweet)
            http(atomFeedUri <> { elem => handleAtomDocument(elem, lastTweet) })
          } catch {
            case exception: Exception =>
              // Eat exception or the actor will die
              errorLogger("Error in atom2twitter sync", Some(exception))
          }
        case Atom2TwitterSync.Shutdown =>
          exit
      }
    }
  }

  def tweet(text: String) = {
    http(Status.update(Shortener(text, 140, "..."), consumer, access) >- { reply => infoLogger(reply.toString) })
  }

  def handleAtomDocument(doc: Elem, lastTweet: DateTime): Unit = {
    val stati = for {entry <- doc \\ "entry"
                     category <- entry \ "category"
                     text <- Seq((entry \ "title").text)
                     term <- category.attribute("term").toSeq if term.text.trim.toLowerCase == "twitter"
                     published <- (entry \ "published").map(p => AtomDateParse(p.text)).toSeq
                     if published.isAfter(lastTweet) && published.isBeforeNow
    } yield (published, text)
    stati.sorted.foreach{ case (_, text) => tweet(text) }
  }

  start
}
