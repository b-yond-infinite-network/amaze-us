import simucat.Cat

object Main extends App {
  val cat1 = new Cat(1)
  cat1.live()

  val cat2 = new Cat(2)
  cat2.live()

  Thread.sleep(30000 * 2)

  cat1.kill()
  println("Cat 1 killed")

  Thread.sleep(30000 * 2)

  cat2.kill()
  println("Cat 2 killed")

}
