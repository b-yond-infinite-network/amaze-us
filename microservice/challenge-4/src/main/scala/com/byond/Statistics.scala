package com.byond

import scala.collection.mutable.HashMap
import scalaz.Scalaz._
import scalaz.Semigroup

// TODO: Extend to hourly statistics to discover hour-dependent behavior
case class StatisticsSummary(
  counts: Map[CatMood, Int],
  total: Int
)

object Stats {
  private def counts[T](xs: IterableOnce[T]): Map[T, Int] = {
    xs.iterator.foldLeft(HashMap.empty[T, Int].withDefaultValue(0))((acc, x) => { acc(x) += 1; acc}).toMap
  }

  def summarize(catStates: Seq[CatState]): StatisticsSummary = {
    StatisticsSummary(counts(catStates.map(_.catMood)), catStates.size)
  }

  // Enables scaling.
  // The |+| operator is the semigroup operator. It combines two hashmaps by summing their values when possible
  def combine(a: StatisticsSummary, b: StatisticsSummary): StatisticsSummary = {
    StatisticsSummary(a.counts |+| b.counts, a.total + b.total)
  }
}