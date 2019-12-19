package org.joaogsma

import akka.actor.typed.ActorSystem
import org.joaogsma.actors.AppActor

/** Entrypoint of the application */
object AppMain extends App {
  ActorSystem(AppActor(), "App")
}
