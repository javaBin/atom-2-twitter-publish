package no.javabin.atom2twitterpublish

import org.specs2.mutable._

class TwitterDateTimeParseSpec extends Specification with DateTimeTuplifier {

  "parse date 1" in {
    var dateTime = TwitterDateParse("Mon Feb 07 08:36:33 +0000 2011")
    tuplify(dateTime) must be equalTo((2011, 2, 7, 8, 36, 33))
  }

  "parse date 2" in {
    var dateTime = TwitterDateParse("Wed Dec 08 14:53:52 +0000 2010")
    tuplify(dateTime) must be equalTo((2010, 12, 8, 14, 53, 52))
  }

}
