package example

import org.slf4j.LoggerFactory

import scala.collection._

trait DataSource {
  def moodMap: Map[String, Int]
}

trait DataStore {
  def store(moodMap: Map[String, Int])
}

class MapSource(from: Map[String, Int]) extends DataSource {
  override def moodMap: Map[String, Int] = from
}

class InMemoryStore extends DataStore {
  private val log = LoggerFactory.getLogger(this.getClass)
  private[example] var storedMoodMap: Map[String, Int] = Map.empty

  override def store(moodMap: Map[String, Int]): Unit = {
    storedMoodMap = (moodMap.toSeq ++ storedMoodMap.toSeq).groupBy(_._1)
      .flatMap { case (mood, grouped) => Map(mood -> grouped.map(_._2).sum) }
    logToConsole() // log here otherwise it will only be called once at termination
  }

  def logToConsole(): Unit = log.info(s"${storedMoodMap.toSeq.sortBy(_._2)}")
}
