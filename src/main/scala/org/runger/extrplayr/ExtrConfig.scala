package org.runger.extrplayr

import java.sql.{Connection, DriverManager, PreparedStatement}

import slick.jdbc.meta.MTable
import slick.lifted.Tag

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, _}

/**
 * Created by Unger on 10/17/15.
 */

case class ExtrOutput(id: Int, label: String)

case class ExtrInput(id: Int, label: String, shortName: String)

object ExtrConfig {

  def inputs = hard_inputs
  def outputs = hard_outputs

  //Todo: switch b/w hard inputs and db inputs to make this more configurable
  def dbInputs = {

  }


  val hard_inputs = List(
    ExtrInput(15, "Randy's Player", "randy")
    , ExtrInput(16, "Lara's Player", "lara")
    , ExtrInput(1, "The Downstairs Speakers", "down")
    , ExtrInput(2, "The Master Speakers", "master")
    , ExtrInput(3, "The Kids Room Speakers", "kids")
    , ExtrInput(4, "The Outdoor Speakers", "out")
  )

  val hard_outputs = List(
    ExtrOutput(1, "Kitchen & Living Room")
    , ExtrOutput(2, "Play Room")
    , ExtrOutput(3, "Dining Room & Office")
    , ExtrOutput(4, "Master Bed & Bath")
    , ExtrOutput(5, "Kids Rooms")
//    , ExtrOutput(6, "Reed's Room")
    , ExtrOutput(6, "Back Patio")
  )

  val outputMap = outputs.map(o => (o.id, o)).toMap
  val inputMap = inputs.map(i => (i.id, i)).toMap

}

import RDSConfig.api._



//case class InputsRow(id: Int, label: String, shortName: String)

class InputsTable(tag: Tag) extends Table[ExtrInput](tag, "Inputs") with Logging {
  def id = column[Int]("id", O.PrimaryKey)
  def label = column[String]("label")
  def shortName = column[String]("shortName")

  val fromRow = (inRow: ExtrInput) => {
    Option(inRow.id, inRow.label, inRow.shortName)
  }

  val toRow = (tup: (Int, String, String)) => {
    val (id, label, shortName) = tup
    ExtrInput(id, label, shortName)
  }

  override def * = (id, label, shortName) <> (toRow, fromRow)
}


//case class OutputsRow(id: Int, label: String)

class OutputsTable(tag: Tag) extends Table[ExtrOutput](tag, "Outputs") with Logging {
  def id = column[Int]("id", O.PrimaryKey)
  def label = column[String]("label")

  val fromRow = (outRow: ExtrOutput) => {
    Option(outRow.id, outRow.label)
  }


  val toRow = (tup: (Int, String)) => {
    val (id, label) = tup
    ExtrOutput(id, label)
  }

  override def * = (id, label) <> (toRow, fromRow)
}


object BootstrapDb extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  val db = Database.forURL("jdbc:h2:file:./inputsOutputs.db", driver = "org.h2.Driver")
  val ins = TableQuery[InputsTable]
  val outs = TableQuery[OutputsTable]

  try {
    Await.result(db.run(DBIO.seq(
      ins.schema.create
      ,outs.schema.create

      , ins ++= ExtrConfig.hard_inputs
      , outs ++= ExtrConfig.hard_outputs

      , ins.result.map(println)
      , outs.result.map(println)

    )), Duration.Inf)
  } catch {
    case ex: Exception => ex.printStackTrace()
  }

  val tables = Await.result(db.run(MTable.getTables), 1.seconds).toList
  println(tables)
  tables.foreach(println)
}


//object DbConfig extends App {
//
//  def createDbPerson = {
//    DriverManager.registerDriver(new org.h2.Driver())
//    val c = DriverManager.getConnection("jdbc:h2:file:./inputsOutputs.db")
//    val stmt = c.prepareStatement("CREATE TABLE PERSON (ID INT PRIMARY KEY, FIRSTNAME VARCHAR(64), LASTNAME VARCHAR(64))")
//    stmt.execute()
//    stmt.close()
//    c.close()
//  }
//
//  def createDbInputOutput = {
//    DriverManager.registerDriver(new org.h2.Driver())
//    val c = DriverManager.getConnection("jdbc:h2:file:./inputsOutputs.db")
//
//    val stmtIns = c.prepareStatement("CREATE TABLE Inputs " +
//      "(ID INT PRIMARY KEY, " +
//      "Label VARCHAR(64) )")
//    stmtIns.execute()
//    stmtIns.close()
//
//    val stmtOuts = c.prepareStatement("CREATE TABLE Outputs " +
//      "(ID INT PRIMARY KEY, " +
//      "Label VARCHAR(64), " +
//      "LASTNAME VARCHAR(64))")
//    stmtOuts.execute()
//    stmtOuts.close()
//
//    c.close()
//  }
//
//  def populateDefaultData() = {
//    DriverManager.registerDriver(new org.h2.Driver())
//    val c = DriverManager.getConnection("jdbc:h2:file:./inputsOutputs.db")
//    val insIns = c.prepareStatement(
//      """
//        |Insert into Inputs
//      """.stripMargin)
//  }
//
//  createDbInputOutput
//  populateDefaultData
//
//}
