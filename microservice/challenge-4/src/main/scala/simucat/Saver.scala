package simucat

import akka.actor.Actor
import java.io.{BufferedWriter, FileWriter}
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import au.com.bytecode.opencsv.CSVWriter

/** Companion object of Saver class
  * Define the event receivable by Saver actor
  */
object Saver {
  case class SaveMood(catID : String, datetime : String, mood : String, prevMood : String)
}

/** The writer into CSV file
  *
  * @attribute dataFile : the CSV file where the Saver will write the Cat moods
  * @attribute csvWriter : the CSV writer
  */
class Saver(dataFolder : String) extends Actor {
  private val dataFile = new BufferedWriter(new FileWriter(s"$dataFolder\\moods_${System.currentTimeMillis()}.csv"))
  private val csvWriter = new CSVWriter(dataFile)

  /** Write the row (catID, datetime, mood, prevMood) into dataFile
    *
    * @param catID
    * @param datetime
    * @param mood
    */
  def saveMoodCSV(catID : String, datetime : String, mood: String, prevMood : String): Unit = {
    val csvData = new ListBuffer[Array[String]]()
    csvData += Array(catID, datetime, mood, prevMood)
    csvWriter.writeAll(csvData.toList)
  }

  import Saver._
  def receive = {
    case SaveMood(catID, datetime, mood, prevMood) => {
      saveMoodCSV(catID, datetime, mood, prevMood)
    }
  }

  /* Add head of CSV file when creating the actor */
  override def preStart(): Unit = {
    val csvData = new ListBuffer[Array[String]]()
    csvData += Array("catID", "datetime", "mood", "prev")
    csvWriter.writeAll(csvData.toList)
  }

  /* Close the file when terminating the actor */
  override def postStop(): Unit = {
    dataFile.close()
  }
}