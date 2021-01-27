package com.byond

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.scalatest.wordspec.AnyWordSpecLike

//#definition
class AkkaQuickstartSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "A StatisticsSummary" must {
    "contain the total number of moods" in {
      assert(true) // Test Stats.summarize
    }
    "be combinable with another StatisticsSummary" in {
      val a = StatisticsSummary(Map(CatMiaw -> 100, CatLookDeepInEyes -> 5), 105)
      val b = StatisticsSummary(Map(CatMiaw -> 110, CatPurr -> 5), 115)
      val combined = Stats.combine(a, b)
      val expected = StatisticsSummary(Map(CatMiaw -> 210, CatPurr -> 5, CatLookDeepInEyes -> 5), 105+115)
      assert(combined == expected)
    }
  }

  "A cat mood" must {
    "be serializable" in {
      assert(true) // Check that moodFromString . moodToString = id
    }
  }

  "A cat state" must {
    "be storable" in {
      assert(true) // Store and read a CatState
    }
  }

      

}
//#full-example
