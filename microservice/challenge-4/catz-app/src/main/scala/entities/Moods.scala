package entities


object Moods {

    sealed trait Mood {
        def ordinal: Int

        def sound: String

        override def toString = s"Mood[$ordinal, $sound]"
    }

    case object GROWL extends Mood {
        val ordinal = 1
        val sound = "grr"
    }

    case object HISS extends Mood {
        val ordinal = 2
        val sound = "kssss"
    }

    case object PURR extends Mood {
        val ordinal = 3
        val sound = "rrrrr"
    }

    case object THROWGLASS extends Mood {
        val ordinal = 4
        val sound = "cling bling"
    }

    case object ROLLONFLOOR extends Mood {
        val ordinal = 5
        val sound = "fffff"
    }

    case object SCRATCHCHAIRS extends Mood {
        val ordinal = 6
        val sound = "gratgrat"
    }

    case object LOOKDEEPINEYES extends Mood {
        val ordinal = 7
        val sound = "-o-o-___--"
    }

    case object DEFAULT extends Mood {
        val ordinal = 8
        val sound = "Miaw"
    }

    // welp, "Scala + Enums" is not exactly a love affair
    val values: Seq[Mood] = Seq(GROWL, HISS, PURR, THROWGLASS, ROLLONFLOOR,
        SCRATCHCHAIRS, LOOKDEEPINEYES, DEFAULT)

}
