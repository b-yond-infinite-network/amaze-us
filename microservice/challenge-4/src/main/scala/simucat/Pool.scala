package simucat

import scala.collection.mutable.Queue

class Pool {
  private val queue : Queue[Cat] = new Queue[Cat]
  private var idx : Int = 0

  def add(): Unit = {
    val cat = new Cat(idx)
    cat.live()
    queue.enqueue(cat)
    idx += 1
  }

  def remove(): Unit = {
    if (queue.nonEmpty) {
      val cat = queue.dequeue()
      cat.kill()
    }
  }

  def clear(): Unit = {
    while (queue.nonEmpty) {
      val cat = queue.dequeue()
      cat.kill()
    }
  }
}