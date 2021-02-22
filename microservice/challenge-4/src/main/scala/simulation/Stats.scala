package simulation

import cats.Monoid
import simulation.CatSimulation.catMoods

case class MoodStats(
                      records: Long,
                      average: Double,
                      median: Double,
                      variance: Double,
                      GROWLCount: Long,
                      HISSCount: Long,
                      PURRCount: Long,
                      THROWGLASSCount: Long,
                      ROLLONFLOORCount: Long,
                      SCRATCHCHAIRSCount: Long,
                      LOOKDEEPINEYESCount: Long,
                      MEOWCount: Long) {
  override def toString(): String = {
    s"Simulation Stats = [ records: ${records}, average mood: ${catMoods.getOrElse(average.toInt, "ERROR")}, median mood: ${catMoods.getOrElse(median.toInt, "ERROR")}, variance: ${"%.2f".format(variance)}, growls: ${GROWLCount}, hisses: ${HISSCount}, purrs: ${PURRCount}, throws: ${THROWGLASSCount}, rolls: ${ROLLONFLOORCount}, scratches: ${SCRATCHCHAIRSCount}, looks: ${LOOKDEEPINEYESCount}, meows: ${MEOWCount} ]"
  }
}

object MoodStats {
  implicit val statsMonoid: Monoid[MoodStats] = new Monoid[MoodStats] {
    override def empty: MoodStats = MoodStats(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    override def combine(x: MoodStats, y: MoodStats): MoodStats = {
      val GROWLCount = x.GROWLCount + y.GROWLCount
      val HISSCount = x.HISSCount + y.HISSCount
      val PURRCount = x.PURRCount + y.PURRCount
      val THROWGLASSCount = x.THROWGLASSCount + y.THROWGLASSCount
      val ROLLONFLOORCount = x.ROLLONFLOORCount + y.ROLLONFLOORCount
      val SCRATCHCHAIRSCount = x.SCRATCHCHAIRSCount + y.SCRATCHCHAIRSCount
      val LOOKDEEPINEYESCount = x.LOOKDEEPINEYESCount + y.LOOKDEEPINEYESCount
      val MEOWCount = x.MEOWCount + y.MEOWCount

      val totalCount = GROWLCount +
        HISSCount +
        PURRCount +
        THROWGLASSCount +
        ROLLONFLOORCount +
        SCRATCHCHAIRSCount +
        LOOKDEEPINEYESCount + MEOWCount

      val totals = GROWLCount * 1 +
        HISSCount * 2 +
        PURRCount * 3 +
        THROWGLASSCount * 4 +
        ROLLONFLOORCount * 5 +
        SCRATCHCHAIRSCount * 6 +
        LOOKDEEPINEYESCount * 7 +
        MEOWCount * 8

      val numInPos = (pos: Long) => pos match {
        case x if GROWLCount >= x => 1
        case x if GROWLCount + HISSCount >= x => 2
        case x if GROWLCount + HISSCount + PURRCount >= x => 3
        case x if GROWLCount + HISSCount + PURRCount + THROWGLASSCount >= x => 4
        case x if GROWLCount + HISSCount + PURRCount + THROWGLASSCount + ROLLONFLOORCount >= x => 5
        case x if GROWLCount + HISSCount + PURRCount + THROWGLASSCount + ROLLONFLOORCount + SCRATCHCHAIRSCount >= x => 6
        case x if GROWLCount + HISSCount + PURRCount + THROWGLASSCount + ROLLONFLOORCount + SCRATCHCHAIRSCount + LOOKDEEPINEYESCount >= x => 7
        case _ => 8
      }

      val average = totals / totalCount

      val median =
        if ((totalCount % 2) == 0) ((numInPos(Math.floor(totalCount / 2).toLong)
                                    + numInPos(Math.ceil(totalCount / 2).toLong)) / 2)
        else numInPos(Math.ceil(totalCount / 2).toLong)

      val variance =
        (Math.pow((1 - average), 2) * GROWLCount +
          Math.pow((2 - average), 2) * HISSCount +
          Math.pow((3 - average), 2) * PURRCount +
          Math.pow((4 - average), 2) * THROWGLASSCount +
          Math.pow((5 - average), 2) * ROLLONFLOORCount +
          Math.pow((6 - average), 2) * SCRATCHCHAIRSCount +
          Math.pow((7 - average), 2) * LOOKDEEPINEYESCount +
          Math.pow((8 - average), 2) * MEOWCount) / totalCount

      MoodStats(
        records = totalCount,
        average = average,
        median = median,
        variance = variance,
        GROWLCount = GROWLCount,
        HISSCount = HISSCount,
        PURRCount = PURRCount,
        THROWGLASSCount = THROWGLASSCount,
        ROLLONFLOORCount = ROLLONFLOORCount,
        SCRATCHCHAIRSCount = SCRATCHCHAIRSCount,
        LOOKDEEPINEYESCount = LOOKDEEPINEYESCount,
        MEOWCount = MEOWCount,
      )
    }
  }
}