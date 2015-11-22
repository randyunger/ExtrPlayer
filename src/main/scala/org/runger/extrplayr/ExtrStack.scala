package org.runger.extrplayr

import org.scalatra._
import scalate.ScalateSupport
import org.fusesource.scalate.{ TemplateEngine, Binding }
import org.fusesource.scalate.layout.DefaultLayoutStrategy
import javax.servlet.http.HttpServletRequest
import collection.mutable

trait ExtrStack extends ScalatraServlet with ScalateSupport with Logging {

  notFound {
    // remove content type in case it was set through an action
    contentType = null
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }

  error(x)

  val x: org.scalatra.ErrorHandler = {
    case t: Throwable => logger.error(s"Uncaught exception", t)
  }

}
