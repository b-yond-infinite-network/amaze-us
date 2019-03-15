package com.test.cats

import org.specs2.mutable.Specification
import scalaz.zio.DefaultRuntime
import scalaz.zio.duration._

class ObservationSpec extends Specification {

  import Observation._
  import data._

  private val runtime = new DefaultRuntime {}

  "Observing cats moods" >> {
    val cats: List[Cat] = runtime.unsafeRun(
      observation(Config(2, 100.millis, 1.second))
        .foldLeft(List.empty[Cat])(_.::(_))
    )
    // stream running for some time must produce elements,
    // but how many exactly is impossible to know
    cats.length must be_>(0)
  }
}
