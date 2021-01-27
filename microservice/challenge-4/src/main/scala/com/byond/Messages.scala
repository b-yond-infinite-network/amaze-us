package com.byond


sealed trait Command
case object Act extends Command


sealed trait Response
case class ResponseAddCatState(catState: CatState) extends Response
case class RefreshStatistics() extends Response
case class KillCats() extends Response


case class CatId(id: Int)

sealed trait CatMood
final case object CatGrowl extends CatMood
final case object CatHiss extends CatMood
final case object CatPurr extends CatMood
final case object CatThrowGlass extends CatMood
final case object CatRollOnFloor extends CatMood
final case object CatScratchChairs extends CatMood
final case object CatLookDeepInEyes extends CatMood
final case object CatMiaw extends CatMood

case class CatState(catId: CatId, catMood: CatMood, timestamp: Long)