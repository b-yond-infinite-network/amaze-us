import scala.collection.mutable.Queue
import simucat.{Cat, Pool}

object Main extends App {
  val pool = new Pool()

  // Cat 1
  pool.add()
  Thread.sleep(30000*2)

  // Cat 2
  pool.add()
  Thread.sleep(30000*1)

  // Cat 3
  pool.add()
  Thread.sleep(30000*3)

  // 1
  pool.remove()
  Thread.sleep(30000*1)

  // 2
  pool.remove()
  Thread.sleep(30000*1)

  // 3
  pool.remove()
}
