import simucat.Cat

import scala.concurrent.{ExecutionContext, Future}

object Main extends App {
  implicit val ec = ExecutionContext.global
  val cat1 = new Cat(1)
  Future {cat1.live()}(ec)

  val cat2 = new Cat(2)
  Future {cat2.live()}(ec)

  Thread.sleep(30000*2)

  cat1.kill()
  println("Cat 1 killed")

  Thread.sleep(30000*2)

  cat2.kill()
  println("Cat 2 killed")
}
