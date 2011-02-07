package no.javabin.atom2twitterpublish

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

object AtomDateParse {
  def apply(dateString: String): DateTime = ISODateTimeFormat.dateTimeParser.parseDateTime(dateString)
}