package simucat

import akka.actor.{ActorSystem, Props}

/** Manage Cat Actors for the simulation
  *
  * @attribute system : actor system according to Akka Actors (https://doc.akka.io)
  */
class Simulation {
  private val system = ActorSystem("simucat")

  def start(n : Int): Unit = {
    /* Create the saver */
    val saver = system.actorOf(Props(new Saver()), s"saver")

    for (i <- 1 to n) {
      val cat = system.actorOf(Props(new Cat(i, saver)), s"cat$i")
    }
  }

  def stop(): Unit = {
    system.terminate()
  }
}