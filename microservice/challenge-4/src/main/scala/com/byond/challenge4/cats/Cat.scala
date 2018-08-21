package com.byond.challenge4.cats

import java.util.UUID

import scala.util.Random

case class Cat(name: String = UUID.randomUUID().toString, mood: Int = Random.nextInt(Mood.values.size) + 1) {

  def senseMood: Mood.MoodType = {
    Mood(Random.nextInt(Mood.values.size) + 1)
  }
}