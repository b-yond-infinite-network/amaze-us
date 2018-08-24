package com.byond.challenge4.cats

object Mood extends Enumeration {

  type MoodType = Value

  val GROWL          = Value(1, "grr")
  val HISS           = Value(2, "kssss")
  val PURR           = Value(3, "rrrrr")
  val THROWGLASS     = Value(4, "cling bling")
  val ROLLONFLOOR    = Value(5, "fffff")
  val SCRATCHCHAIRS  = Value(6, "gratgrat")
  val LOOKDEEPINEYES = Value(7, "-o-o-___--")

}
