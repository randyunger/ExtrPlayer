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

}
