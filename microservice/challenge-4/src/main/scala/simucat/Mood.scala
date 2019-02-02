package simucat

/** Available moods for the Cat
  * @attribute sound : phonic representation of the mood
  */
sealed trait Mood {
  val sound : String
}
case object GROWL extends Mood {val sound = "grr"}
case object HISS extends Mood {val sound = "kssss"}
case object PURR extends Mood {val sound = "rrrrr"}
case object THROWGLASS extends Mood {val sound = "cling bling"}
case object ROLLONFLOOR extends Mood {val sound = "fffff"}
case object SCRATCHCHAIRS extends Mood {val sound = "gratgrat"}
case object LOOKDEEPINEYES extends Mood {val sound = "-o-o-___--"}