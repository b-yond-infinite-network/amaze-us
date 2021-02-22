package simulation

import cats.Monoid
import simulation.CatSimulation.{GROWL, HISS, LOOKDEEPINEYES, MEOW, PURR, ROLLONFLOOR, SCRATCHCHAIRS, THROWGLASS}

case class CatRecord(catId: Long, mood: String, time: Long)

object CatRecord {
  def recordToStat(record: CatRecord): MoodStats = record.mood match {
    case GROWL => Monoid[MoodStats].empty.copy(GROWLCount = 1)
    case HISS => Monoid[MoodStats].empty.copy(HISSCount = 1)
    case PURR => Monoid[MoodStats].empty.copy(PURRCount = 1)
    case THROWGLASS => Monoid[MoodStats].empty.copy(THROWGLASSCount = 1)
    case ROLLONFLOOR => Monoid[MoodStats].empty.copy(ROLLONFLOORCount = 1)
    case SCRATCHCHAIRS => Monoid[MoodStats].empty.copy(SCRATCHCHAIRSCount = 1)
    case LOOKDEEPINEYES => Monoid[MoodStats].empty.copy(LOOKDEEPINEYESCount = 1)
    case MEOW => Monoid[MoodStats].empty.copy(MEOWCount = 1)
  }
}
