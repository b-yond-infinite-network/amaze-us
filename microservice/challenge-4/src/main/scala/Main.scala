import scala.collection.mutable.Queue
import simucat.{Cat, Pool}

object Main extends App {
  val pool = new Pool()

  // Add 1000 cats
  pool.add(1000)

  Thread.sleep(5000)
  // Remove cats
  pool.clear()
}
