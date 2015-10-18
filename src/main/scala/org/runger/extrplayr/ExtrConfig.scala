package org.runger.extrplayr

/**
 * Created by Unger on 10/17/15.
 */

case class ExtrOutput(id: Int, name: String)

case class ExtrInput(id: Int, name: String)

object ExtrConfig {

  val inputs = List(
    ExtrInput(1, "Randy's Player")
    , ExtrInput(2, "Lara's Player")
  )

  val outputs = List(
    ExtrOutput(1, "Kitchen & Living Room")
    , ExtrOutput(2, "Dining Room & Office")
    , ExtrOutput(3, "Play Room")
    , ExtrOutput(4, "Master Bed & Bath")
    , ExtrOutput(5, "Reed's Room")
    , ExtrOutput(6, "Paige's Room")
  )

  def getTies() = {
  }


}
