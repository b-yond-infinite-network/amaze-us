import simucat.Simulation

object Main extends App {
  val simucat = new Simulation("D:\\ScalaProjects\\b-yond\\microservice\\challenge-4\\data")
  simucat.start(1000)
  Thread.sleep(60*60*1000)
  simucat.stop()
}
