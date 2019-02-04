import simucat.Simulation

object Main extends App {
  val simucat = new Simulation()
  simucat.start(1000)
  Thread.sleep(60*1000)
  simucat.stop()
}
