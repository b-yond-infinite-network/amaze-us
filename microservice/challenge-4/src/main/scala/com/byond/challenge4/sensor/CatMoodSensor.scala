package com.byond.challenge4.sensor

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.byond.challenge4.cats.Cat
import com.byond.challenge4.configuration.SensorSettings
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.log4s.{Logger, getLogger}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class CatMoodSensor(settings: SensorSettings) {

  @transient
  protected[this] lazy val logger: Logger = getLogger(getClass)

  def start(): Unit = {

    logger.info("starting sensor system...")

    val cats: Stream[Cat] = (1l to settings.numOfCatsToSense).toStream.map(_ => Cat())
    val interval = settings.intervalToSense.toSeconds seconds

    val system = ActorSystem("mood-sensor")
    val sensor = system.actorOf(MoodSensor.props(settings.producer, settings.topic, cats), "sensor")
    system.scheduler.schedule(Duration.Zero, interval, sensor, MoodSensor.SensorMsg)

  }
}

class MoodSensor(producer: KafkaProducer[String, String], topic: String, cats: Stream[Cat]) extends Actor with ActorLogging {

  override def receive: Receive = {
    case MoodSensor.SensorMsg =>
      log.info("It's time to scan all kitty ones")
      cats.foreach { cat =>
        log.debug(s"sending current mood ${cat.mood} ${cat.name}")
        producer.send(
          new ProducerRecord[String, String](topic, cat.name, cat.senseMood.id.toString)
        )
      }

    case _ => log.error("Message unknown, sensor will be discard it")
  }

}

object MoodSensor {
  def props(producer: KafkaProducer[String, String], topic: String, cats: Stream[Cat]): Props = {
    Props(new MoodSensor(producer, topic, cats))
  }
  case class SensorMsg()
}
