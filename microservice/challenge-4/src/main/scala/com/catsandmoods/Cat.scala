package com.catsandmoods

/**
  * cats-and-moods
  *
  */

import com.catsandmoods.Cat.moods

case class Cat (name : String) {
  def mood : String = {
    val random = new scala.util.Random
    val index = random.nextInt(moods.length )
    moods(index)
  }
}

object Cat {
  val moods = Array( Mood.GROWL, Mood.HISS, Mood.PURR, Mood.THROWGLASS, Mood.ROLLONFLOOR, Mood.SCRATCHCHAIRS, Mood.LOOKDEEPINEYES, Mood.MIAW)
  def apply(num:Int): Cat = {
    Cat(s"cat-$num")
  }
}


object Mood extends Enumeration {
  val GROWL         = "grr"
  val HISS          = "kssss"
  val PURR          = "rrrrr"
  val THROWGLASS    = "cling bling"
  val ROLLONFLOOR   = "fffff"
  val SCRATCHCHAIRS = "gratgrat"
  val LOOKDEEPINEYES = "-o-o-___--"
  val MIAW = "dd"
  type Mood = Value
}
