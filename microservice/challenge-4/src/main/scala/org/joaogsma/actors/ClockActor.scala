package org.joaogsma.actors

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object ClockActor {
  def apply(cats: Iterable[ActorRef[CatActor.Message]], changesPerCat: Int): Behavior[Nothing] = {
    Behaviors.setup[Nothing] { _ =>
      for (i <- 0 until changesPerCat) {
        cats.foreach(_ ! CatActor.ChangeMood())
      }
      cats.foreach(_ ! CatActor.Close())
      Behaviors.stopped
    }
  }
}
