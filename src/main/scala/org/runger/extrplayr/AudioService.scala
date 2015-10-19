package org.runger.extrplayr

/**
 * Created by Unger on 10/18/15.
 */

import Utils._

object AudioService {

  val as = new AudioService

  def apply() = {
    as
  }

}

class AudioService {
  //Could be more robust way of dealing with telnet

  def tie(input: Int, output: Int) = {
    val cmd = s"$input*$output$$"
    println(s"Executing: $cmd")
    TelnetClient().execute(cmd)
  }

  def disable(output: Int) = {
    val cmd = s"0*$output$$"
    println(s"Executing: $cmd")
    TelnetClient().execute(cmd)
  }

  def disableAll() = {
    val cmd = ExtrConfig.outputs.map(output => s"0*${output.id}$$").mkString
//    val cmd = s"0*$output$$"
    println(s"Executing: $cmd")
    TelnetClient().execute(cmd)
  }

  def fullState() = {
    
    val fullConfig = ExtrConfig.outputs.map(o =>{
      val tieStr = TelnetClient().readTie(o.id)
      val input = for {
        inQ <- tieStr.tryToInt
        in <- ExtrConfig.inputMap.get(inQ)
      } yield in
      (o, input)
    })
    
    println(fullConfig)

    fullConfig
  }

  def simpleState() = {
    fullState.toMap.foldLeft(Map.empty[ExtrInput, List[ExtrOutput]])((acc, inp) => {
      inp._2 match {
        case None => acc
        case Some(i) => {
          val list = acc.getOrElse(i, List.empty)
            acc + (i -> (inp._1 :: list))
        }
      }

    })
  }

  def simpleStateForAllInputs() = {
    val simp = simpleState()
    ExtrConfig.inputs.map(inp => {
      (inp, simp.get(inp).getOrElse(List.empty))
    })
  }

}