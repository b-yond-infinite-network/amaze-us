package services
import java.util.concurrent.TimeUnit

import actors.ingress.OverlordActor
import akka.actor.{ActorRef, ActorSystem}
import javax.inject.{Inject, Named, Singleton}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

/**
  * A schedular to wake up all the cats. Sends a one time message to the overlord.
  */
@Singleton
class SystemSchedular @Inject()(system: ActorSystem, @Named("overlord-actor") overlordActor: ActorRef) {

  system.scheduler.scheduleOnce(
    Duration.create(1, TimeUnit.SECONDS),
    overlordActor,
    OverlordActor.WakeUpCats
  )
}
