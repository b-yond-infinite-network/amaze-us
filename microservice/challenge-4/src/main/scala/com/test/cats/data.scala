package com.test.cats

import scalaz.NonEmptyList
import scalaz.syntax.foldable._

object data {

  type Timestamp = Long

  case class Cat(id: Int, mood: Mood, ts: Timestamp)

  sealed trait Mood { val desc: String }

  object moods {
    object DEFAULT        extends Mood { val desc = "Miaw" }
    object GROWL          extends Mood { val desc = "grr" }
    object HISS           extends Mood { val desc = "kssss" }
    object PURR           extends Mood { val desc = "rrrrr" }
    object THROWGLASS     extends Mood { val desc = "cling bling" }
    object ROLLONFLOOR    extends Mood { val desc = "fffff" }
    object SCRATCHCHAIRS  extends Mood { val desc = "gratgrat" }
    object LOOKDEEPINEYES extends Mood { val desc = "-o-o-___--" }

    val all: NonEmptyList[Mood] =
      NonEmptyList(DEFAULT, GROWL, HISS, PURR, THROWGLASS, ROLLONFLOOR, SCRATCHCHAIRS, LOOKDEEPINEYES)

    val byDesc: Map[String, Mood] =
      all.map(_.desc).zip(all).toList.toMap

    val byIndex: Map[Int, Mood] =
      all.zipWithIndex.map(_.swap).toList.toMap
  }
}
