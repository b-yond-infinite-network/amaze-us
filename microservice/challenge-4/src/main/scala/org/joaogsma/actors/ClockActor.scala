package org.joaogsma.actors

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

/** Actor responsible for simulating the passage of time during a simulation. It sends
  * `CatActor.ChangeMood` messages to all cat actors at a regular interval.
  */
object ClockActor {
  /** Returns a `Behavior[Nothing]` which doesn't accept any messages, but sends all messages to the
    * cat actors and then stops.
    *
    * @param cats           actors which to send messages
    * @param changesPerCat  number of times the message should be sent to each actor
    * @return
    */
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
