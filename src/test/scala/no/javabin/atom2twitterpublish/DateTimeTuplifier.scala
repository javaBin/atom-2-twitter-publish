package no.javabin.atom2twitterpublish

import org.joda.time.DateTime

trait DateTimeTuplifier {

  def tuplify(dt: DateTime): (Int, Int, Int, Int, Int, Int) = {
    var offset = dt.getChronology.getZone.getStandardOffset(dt.getMillis)
    var hourOffset = (offset / (1000 * 60 * 60)).toInt
    (dt.year.get, dt.monthOfYear.get, dt.dayOfMonth.get, dt.hourOfDay.get - hourOffset, dt.minuteOfHour.get, dt.secondOfMinute.get)
  }

}