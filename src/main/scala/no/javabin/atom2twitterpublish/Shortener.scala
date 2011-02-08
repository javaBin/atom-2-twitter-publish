package no.javabin.atom2twitterpublish

import java.nio.charset.Charset

object Shortener {
  val UTF8 = "UTF-8"
  def apply(text: String, maxLength: Int, shortenedEnding: String): String = {
    val bs = text.getBytes(UTF8)
    println("### " + bs.length)
    if (bs.size > maxLength) {
      val endBS = shortenedEnding.getBytes(UTF8)
      val length = maxLength - endBS.size
      new String(bs, 0, length - continuationTax(bs, length - 1), "UTF-8") + shortenedEnding
    } else
      text
  }
  val bit8: Byte = -128
  val bit7: Byte = 64
  def continuationTax(bs: Array[Byte], idx: Int) =
    if ((bs(idx) & bit8) != 0)
      1
    else
      0

}