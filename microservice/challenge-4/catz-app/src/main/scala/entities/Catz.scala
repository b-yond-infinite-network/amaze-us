package entities

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}
import akka.routing.{ActorRefRoutee, BroadcastRoutingLogic, Router}
import entities.Cat.ReportMood
import entities.Moods.Mood
import producer.Purrducer.Purrduce
import producer.{CatMoodRecord, Purrducer}

import scala.util.Random


object Cat {
    def props(id: Integer, producer: ActorRef): Props = Props(new Cat(id, producer))

    final case class ChangeMood()
    final case class AskMood()
    final case class ReportMood(id: Int, mood: Mood)
}


class Cat(id: Integer, producer: ActorRef) extends Actor with ActorLogging {
    import Cat._

    var mood: Mood = Moods.DEFAULT

    override def preStart(): Unit = log.info("Cat actor {} started", id)

    override def postStop(): Unit = log.info("Cat actor {} stopped", id)

    override def receive: Receive = {
        case ChangeMood =>
            mood = Moods.values(Random.nextInt(Moods.values.length))
            if (log.isInfoEnabled) {
                log.info("Cat #{} changing its mood to {}", id, mood)
            }

            // FIXME: Time recording not precise enough:
            producer ! Purrduce(new CatMoodRecord(id, System.currentTimeMillis(),
                mood.ordinal))
        case AskMood =>
            sender() ! ReportMood(id, mood)
    }
}


object CatMaster {
    final case class ChangeMoods()
}


class CatMaster(population: Integer) extends Actor with ActorLogging {
    import Cat.ChangeMood
    import CatMaster._

    val producer = context.actorOf(Purrducer.props)

    var catMasta = {
        val routees = Vector.tabulate(population)(n => {
            val r = context.actorOf(Cat.props(n, producer))
            context.watch(r)
            ActorRefRoutee(r)
        })

        Router(BroadcastRoutingLogic(), routees)
    }

    override def receive: Receive = {
        case ReportMood(id, mood) =>
            if (log.isInfoEnabled) {
                log.info(s"Cat $id switched to $mood")
            }
        case ChangeMoods =>
            if (log.isInfoEnabled) {
                log.info("Give dem catz a shake")
            }
            catMasta.route(ChangeMood, sender())
        case Terminated(a) =>
            catMasta = catMasta.removeRoutee(a)
            val r = context.actorOf(Props[Cat])
            context.watch(r)
            catMasta = catMasta.addRoutee(r)
    }
}


