package no.javabin.atom2twitterpublish

import org.specs.Specification

class AtomDateTimeParseSpec extends Specification with DateTimeTuplifier {

  "parse date 1" in {
    var dateTime = AtomDateParse("2011-02-01T10:22:37Z")
    tuplify(dateTime) must be equalTo((2011, 2, 1, 10, 22, 37))
  }

  "parse date 2" in {
    var dateTime = AtomDateParse("2010-11-17T20:53:59Z")
    tuplify(dateTime) must be equalTo((2010, 11, 17, 20, 53, 59))
  }

}