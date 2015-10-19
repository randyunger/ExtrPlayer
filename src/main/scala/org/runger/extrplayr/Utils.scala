package org.runger.extrplayr

/**
 * Created by Unger on 10/18/15.
 */

object Utils {

  implicit class StrUtils(val str: String) extends AnyVal {
    def tryToInt = {
      try {
        Option(str.toInt)
      } catch {
        case e: Exception => None
      }

    }
  }

}
