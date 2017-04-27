package org.runger.extrplayr

import java.io.{PrintWriter, InputStreamReader, BufferedReader}
import java.net.Socket
import java.util.concurrent.TimeUnit

import akka.actor.{Props, ActorSystem, Actor}

import scala.concurrent.duration.Duration

/**
 * Created by Unger on 9/30/15.
 */

object TelnetClient {

  val instance = new TelnetClient("AudioSwitch", "blah", "integration")

  def apply() = {
    instance
  }

}

class CommPackage(addr: String) {

  val sock = new Socket(addr, 23)
  val socketIsIntact = true
  val i = new BufferedReader(new InputStreamReader(sock.getInputStream))
  val o = new PrintWriter(sock.getOutputStream, true)

  def send(message: String) = {
    o.println(message + '\r')
  }

  def reconnect() = {
    val prev = sock.getLocalSocketAddress
    sock.connect(prev)
  }

  def close() = {
    sock.close()
  }

}

class TelnetClient(ip: String, user: String, pwd: String) extends Logging {

  var comm = new CommPackage(ip)

  class TelnetActor extends Actor {
    def receive = {
      case "login" => {
        info("Login prompt received")
//        o.println(user + '\r')
        comm.send(user)
      }
      case "pwd" => {
        info("Pwd prompt received")
//        o.println(pwd + '\r')
        comm.send(pwd)
      }
      case cmd: String => {
        info(s"Sending telnet cmd: $cmd")
//        if(!socketIsIntact) {
//          sock = mkSock
//          Thread.sleep(150)
//        }
//        o.println(cmd + '\r')
        comm.send(cmd)
      }
      case _ => warn("Unknown cmd to Actor")
    }
  }

  val system = ActorSystem("TelnetSystem")
  val telnetActor = system.actorOf(Props(new TelnetActor))

  var lastResp = ""

  val readT = new Thread() {
    override def run(): Unit = {
      //      var ch = i.read().toChar

      //      //Read Login prompt
      //      while (ch != ':'){
      //        ch = i.read().toChar
      //      }
      //      telnetActor ! "login"
      //
      //      //Read password prompt
      //      ch = i.read().toChar
      //      while (ch != ':'){
      //        ch = i.read().toChar
      //      }
      //      lutronActor ! "pwd"

      //Send subsequent output to Actor
      while (true) {
        try {
          var line = comm.i.readLine()
          while (true) {
            info(s"rcvd: $line")
            line = comm.i.readLine()
            lastResp = line
            if(line.contains("Copyright 2007")) comm.send("\0330*1000tc")
//            if(line.contains("Out06 In02 Aud")) ???
          }
        } catch {
          case e: Exception => {
            warn("Warning: Socket was disconnected!")
            warn(e.getStackTrace.mkString("\n"))
            e.printStackTrace()
          }
        }
        finally {
          comm.close()
          comm = new CommPackage(ip)
        }
      }
    }
  }

  readT.start()


  def execute(cmd: String) = {
    telnetActor ! cmd
  }

//  def getTie(id: Int) = {
//    telnetActor ! s"id$$"
//  }

  //This is bad for concurrency
  def readTie(id: Int) = {
//    readT.stop()
//    o.println(s"$id$$" + '\r')
    telnetActor ! s"$id$$"
//    val resp = i.readLine()
//    println("got resp!")
    Thread.sleep(150)
    val resp = lastResp
    resp
  }
}
//  val cancels = ExtrConfig.outputs.map(o =>
//    system.scheduler.schedule(
//      Duration.Zero
//      , Duration.create(1000, TimeUnit.MILLISECONDS)
//      , telnetActor
//      , s"${o.id}$$"
//    )
//  )

//}

