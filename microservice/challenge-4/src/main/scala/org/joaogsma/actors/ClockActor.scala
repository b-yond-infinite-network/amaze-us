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
    * @param catActors      actors which to send messages
    * @param changesPerCat  number of times the message should be sent to each actor
    * @return
    */
  def apply(
      catActors: Iterable[ActorRef[CatActor.Message]],
      changesPerCat: Int): Behavior[Nothing] = {
    Behaviors.setup[Nothing] { _ =>
      for (i <- 0 until changesPerCat) {
        catActors.foreach(_ ! CatActor.ChangeMood)
      }
      catActors.foreach(_ ! CatActor.Close)
      Behaviors.stopped
    }
  }
}
