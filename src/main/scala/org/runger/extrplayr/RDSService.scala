//package org.runger.extrplayr
//
//import com.typesafe.config.ConfigFactory
//import slick.driver.{H2Driver, JdbcDriver}
//
///**
//  * Created by randy on 1/29/17.
//  */
//
//object RDSConfig extends Logging {
//  val api = profile.api
//  lazy val profile: JdbcDriver = {
//    sys.env.get("DB_ENVIRONMENT") match {
//      case Some(e) => ConfigFactory.load().getString(s"$e.slickDriver") match {
//        case "scala.slick.driver.H2Driver" => {
//          info("Bootstrapping H2 driver")
//          Class.forName("org.h2.Driver")
//          H2Driver
//        }
////        case "scala.slick.driver.MySQLDriver" => PostgresDriver
//      }
//      case _ => H2Driver
//    }
//  }
//}