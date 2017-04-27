package org.runger.extrplayr

import org.specs2.mutable.Specification

/**
  * Created by randy on 4/27/17.
  */
class ExtrConfig$Test extends Specification {

  "it" should {
    "do it" in {
      5 shouldEqual(2+3)
    }
  }

  "config" should {
    "load" in {
      println("Loading from disk")
      val in = ExtrConfig.dbInputs._1
      in.size shouldEqual(2)
    }

    "write" in {
      ExtrConfig.writeConfig()
      true shouldEqual(true)
    }

  }


}
