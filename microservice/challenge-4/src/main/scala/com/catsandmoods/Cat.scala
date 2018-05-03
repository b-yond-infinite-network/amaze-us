package com.catsandmoods

/**
  * cats-and-moods
  *
  */

import com.catsandmoods.Cat.moods
import com.catsandmoods.Mood.Mood


case class Cat (name : String) {
  def mood : Mood = {
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
  type Mood = Value
  val GROWL, HISS, PURR, THROWGLASS, ROLLONFLOOR, SCRATCHCHAIRS, LOOKDEEPINEYES, MIAW = Value
}
