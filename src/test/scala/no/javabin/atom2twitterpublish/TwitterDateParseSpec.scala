package no.javabin.atom2twitterpublish

import org.specs.Specification
import org.joda.time.DateTime

class TwitterDateParseSpec extends Specification {

  def tuplify(dt: DateTime): (Int, Int, Int, Int, Int, Int) = {
    var offset = dt.getChronology.getZone.getStandardOffset(dt.getMillis)
    var hourOffset = (offset / (1000 * 60 * 60)).toInt
    (dt.year.get, dt.monthOfYear.get, dt.dayOfMonth.get, dt.hourOfDay.get - hourOffset, dt.minuteOfHour.get, dt.secondOfMinute.get)
  }

  "parse date 1" in {
    var dateTime = TwitterDateParse("Mon Feb 07 08:36:33 +0000 2011")
    tuplify(dateTime) must be equalTo((2011, 2, 7, 8, 36, 33))
  }

  "parse date 2" in {
    var dateTime = TwitterDateParse("Wed Dec 08 14:53:52 +0000 2010")
    tuplify(dateTime) must be equalTo((2010, 12, 8, 14, 53, 52))
  }

}