package no.javabin.atom2twitterpublish

import org.joda.time.{DateTimeFieldType, DateTime}
import org.joda.time.format.{DateTimeFormat, ISODateTimeFormat, DateTimeFormatterBuilder}
import java.util.Locale

object TwitterDateParse {
  val ddd = ISODateTimeFormat.dateParser
  val dateTimeFormatter = DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss Z yyyy").withLocale(Locale.US)
  def apply(dateString: String): DateTime = dateTimeFormatter.parseDateTime(dateString)
}