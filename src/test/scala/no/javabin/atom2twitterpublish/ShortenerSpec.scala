package no.javabin.atom2twitterpublish

import org.specs.Specification

class ShortenerSpec extends Specification {

  "string short enough should remain untouched" in {
    Shortener("hei", 3, "...") must_== "hei"
    Shortener("hei", 4, "...") must_== "hei"
  }

  "too long string should be shortened" in {
    Shortener("heisann", 4, "...") must_== "h..."
  }

  "too long string with extended chars should be shortened" in {
    Shortener("øisann", 6, "...") must_== "øi..."
  }

  "too long string with extended chars should not be shortened at extended char" in {
    Shortener("høisann", 5, "...") must_== "h..."
    //Shortener("h♥isann", 6, "...") must_== "h..."
  }

  "two byte extended chars should be taxed" in {
    val first = Array[Byte](0, -128)
    val last = Array[Byte](7, -1)
    Shortener.continuationTax(first, 0) must_== 0
    Shortener.continuationTax(first, 1) must_== 1
    Shortener.continuationTax(last, 0) must_== 0
    Shortener.continuationTax(last, 1) must_== 1
  }

  /*
  "three byte extended chars should be taxed" in {
    val first = Array[Byte](0, 8, 0)
    val last = Array[Byte](0, -1, -1)
    Shortener.continuationTax(first, 0) must_== 0
    Shortener.continuationTax(first, 1) must_== 1
    Shortener.continuationTax(first, 2) must_== 2
    Shortener.continuationTax(last, 0) must_== 0
    Shortener.continuationTax(last, 1) must_== 1
    Shortener.continuationTax(last, 2) must_== 2
  }

  "four byte extended chars should be taxed" in {
    val first = Array[Byte](0, 1, 0, 0)
    val last = Array[Byte](0, 31, -1 -1)
    Shortener.continuationTax(first, 0) must_== 0
    Shortener.continuationTax(first, 1) must_== 1
    Shortener.continuationTax(first, 2) must_== 2
    Shortener.continuationTax(first, 3) must_== 3
    Shortener.continuationTax(last, 0) must_== 0
    Shortener.continuationTax(last, 1) must_== 1
    Shortener.continuationTax(last, 2) must_== 2
    Shortener.continuationTax(last, 3) must_== 3
  }
  */
}