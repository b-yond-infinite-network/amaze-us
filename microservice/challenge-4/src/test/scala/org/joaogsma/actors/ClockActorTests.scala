package org.joaogsma.actors

import akka.actor.testkit.typed.scaladsl.BehaviorTestKit
import akka.actor.typed.scaladsl.Behaviors
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ClockActorTests {
  val CAT_ACTORS: Seq[BehaviorTestKit[CatActor.Message]] =
      Seq.fill(2)(BehaviorTestKit(Behaviors.empty))

  @Test
  def shouldSendAllMessages(): Unit = {
    BehaviorTestKit[Nothing](ClockActor(CAT_ACTORS.map(_.ref), 10))
    val expectedMessages: Seq[CatActor.Message] =
        Seq.fill(10)(CatActor.ChangeMood).appended(CatActor.Close)
    CAT_ACTORS
        .view
        .map(_.selfInbox.receiveAll())
        .foreach(assertThat(_).isEqualTo(expectedMessages))
  }
}
