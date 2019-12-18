package org.joaogsma.models

/** Enumeration of all cat moods */
object Mood extends Enumeration {
  type Mood = Value
  val MIAW, GROWL, HISS, PURR, THROWGLASS, ROLLONFLOOR, SCRATCHCHAIRS, LOOKDEEPINEYES = Value
}
