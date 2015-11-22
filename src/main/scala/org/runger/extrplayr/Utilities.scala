package org.runger.extrplayr

import org.slf4j.LoggerFactory

/**
 * Created by Unger on 11/14/15.
 */

trait Logging {

  val logger = LoggerFactory.getLogger(this.getClass)

  def info(msg: String) = {
    logger.info(msg)
  }

  def warn(msg: String) = {
    logger.warn(msg)
  }

  def error(msg: String) = {
    logger.error(msg)
  }

}