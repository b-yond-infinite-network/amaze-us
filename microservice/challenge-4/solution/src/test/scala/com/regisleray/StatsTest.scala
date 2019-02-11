package com.regisleray

import com.regisleray.Cat.Mood
import org.scalatest.{Matchers, WordSpec}

class StatsTest extends WordSpec with Matchers {

  "Stats" should {
    "average with empty values" in {
      Stats(Nil).average shouldBe Nil
    }

    "average with values" in {
      Stats(List(Mood.Growl -> 10, Mood.Hiss -> 10)).average shouldBe List(Mood.Growl -> 0.50d, Mood.Hiss -> 0.50d)
    }

    "median with empty values" in {
      Stats(Nil).median shouldBe None
    }

    "variance with empty values" in {
      Stats(Nil).variance shouldBe None
    }

    "median with values" in {
      Stats(List(Mood.Growl -> 10, Mood.Hiss -> 10)).median shouldBe Some(10d)
    }

    "variance with  values" in {
      Stats(List(Mood.Growl -> 10, Mood.Hiss -> 10)).variance shouldBe Some(0d)
    }
  }
}
