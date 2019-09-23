package example

import org.specs2.mutable.Specification
import example.CatAnalysis._
import example.Cats.Mood

class AnalysisSpec extends Specification {

  "format some moods" >> {
    val moods: Array[(Cats.Mood.Value, Long)] = Array(
      (Mood.PURR, 15),
      (Mood.HISS, 10),
      (Mood.GROWL, 0)
    )

    val expected = "15: rrrrr\n10: kssss\n0: grr"
    format(moods) must beEqualTo(expected)
  }

  "format no moods" >> {
    val moods: Array[(Cats.Mood.Value, Long)] = Array()

    val expected = ""
    format(moods) must beEqualTo(expected)
  }
}