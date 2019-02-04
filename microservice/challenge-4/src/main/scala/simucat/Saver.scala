package simucat

import akka.actor.Actor
import java.io.{BufferedWriter, FileWriter}
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import au.com.bytecode.opencsv.CSVWriter

object Saver {
  case class SaveMood(catID : String, datetime : String, mood : String)
}

class Saver extends Actor {
  private val dataFile = new BufferedWriter(new FileWriter(s"D:\\ScalaProjects\\b-yond\\microservice\\challenge-4\\data\\moods_${System.currentTimeMillis()}.csv"))
  private val csvWriter = new CSVWriter(dataFile)

  def saveMoodCSV(catID : String, datetime : String, mood: String): Unit = {
    val csvData = new ListBuffer[Array[String]]()
    csvData += Array(catID, datetime, mood)
    csvWriter.writeAll(csvData.toList)
  }

  import Saver._
  def receive = {
    // When receiving ChangeMood, randomly select a new mood
    case SaveMood(catID, datetime, mood) => {
      saveMoodCSV(catID, datetime, mood)
    }
  }

  override def preStart(): Unit = {
    val csvData = new ListBuffer[Array[String]]()
    csvData += Array("catID", "datetime", "mood")
    csvWriter.writeAll(csvData.toList)
  }

  override def postStop(): Unit = {
    dataFile.close()
  }
}