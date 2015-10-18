package org.runger.extrplayr

import org.scalatra._
import scalate.ScalateSupport

class ExtronServlet extends ExtrStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/audio") {
    contentType="text/html"

    val config = Map(
      ExtrInput(1, "Randy's Player") -> List(ExtrOutput(1, "Kitchen"))
      , ExtrInput(2, "Lara's Player") -> List()
    )

    def mkOutList(in: ExtrInput, outs: List[ExtrOutput]) = {
      {outs.map(out => s"<div>${out.name}</div>")}.mkString("\n")
    }

    val page = config.map { case (in, outList) => {
      s"<div>${in.name}</div>" + mkOutList(in, outList)
    }}.mkString("\n")

    page
  }

  get("/a") {
    contentType="text/html"
//    layoutTemplate("/WEB-INF")
    scaml("hello")

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
