package org.runger.extrplayr

import org.scalatra._
import org.slf4j.LoggerFactory
import scalate.ScalateSupport
import ch.qos.logback.classic.Logger

class ExtronServlet extends ExtrStack with Logging {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/exc") {
    ???
  }

  get("/audioBlah") {
    contentType="text/html"

    val config = Map(
      ExtrInput(1, "Randy's Player", "randy") -> List(ExtrOutput(1, "Kitchen"))
      , ExtrInput(2, "Lara's Player", "lara") -> List()
    )

    def mkOutList(in: ExtrInput, outs: List[ExtrOutput]) = {
      {outs.map(out => s"<div>${out.name}</div>")}.mkString("\n")
    }

    val page = config.map { case (in, outList) => {
      s"<div>${in.name}</div>" + mkOutList(in, outList)
    }}.mkString("\n")

    page
  }

  get("/audio") {

    info("http request received /audio")

    contentType="text/html"
//    layoutTemplate("/WEB-INF")

    val config = ExtrConfig.outputs

//    val state: List[(ExtrInput, List[ExtrOutput])] = List(
//      ExtrInput(1, "Randy's Player", "randy") -> List(ExtrConfig.outputs(1))
//      , ExtrInput(2, "Lara's Player", "lara") -> List()
//    )

//    val state = Map(
//      ExtrInput
//    )

    //Make Randy come before Lara
    val state = AudioService().simpleStateForAllInputs().toList.sortBy(_._1.name).reverse

    info(s"req state:: $state")

    scaml("audio", "config" -> config, "state" -> state)

  }

  def tryToInt(i: String) = {
    try {
      Option(i.toInt)
    } catch {
      case e: Exception => None
    }

  }

  post("/tie/:output/:input") {
    val outputO = tryToInt(params("output"))
    val inputO = tryToInt(params("input"))
    println(s"in $inputO out $outputO")
    for {
      input <- inputO
      output <- outputO
    } yield {
      AudioService().tie(input, output)
    }
  }

  delete("/tie/:output") {
    val outputO = tryToInt(params("output"))
    println(s"deleting $outputO")
    for {
      output <- outputO
    } yield AudioService().disable(output)
  }

  delete("/tie") {
    println(s"deleting all ties")
    AudioService().disableAll()
  }

  get("/audio/:output") {
    val outputO = tryToInt(params("output"))
    println(s"getting $outputO")
    val resp = for {
      output <- outputO
    } yield TelnetClient().readTie(output)
    resp
  }

  get("/audio/state") {
    val resp = AudioService().fullState()
    resp
  }

}

//    jade("now-playing")


//  }

//  get("/on/:id") {
//    val id  = params("id")
//    val loads = LuConfig().search(id)
//    loads.foreach (load =>{
//      TelnetClient().execute(load.on())
//    })
//      loads.mkString("<br/>")
//  }
//
//  get("/off/:id") {
//    val id  = params("id")
//    val loads = LuConfig().search(id)
//    loads.foreach (load =>{
//      TelnetClient().execute(load.off())
//    })
//    loads.mkString("<br/>")
//  }

//}
