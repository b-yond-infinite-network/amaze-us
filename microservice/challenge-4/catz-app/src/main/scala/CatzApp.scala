import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import entities.CatMaster

import scala.concurrent.duration._
import scala.io.StdIn
import scala.language.postfixOps

object CatzApp {

    def main(args: Array[String]): Unit = {
        import system.dispatcher

        val system = ActorSystem("cat-system")

        val config = ConfigFactory.load()
        val population = config.getInt("cat.population")
        val moodChangeInterval = config.getInt("mood.change.interval")

        try {
            val master = system.actorOf(Props(new CatMaster(population)), "cat-master")

            system.scheduler.schedule(0 milliseconds,
                moodChangeInterval milliseconds, master,
                CatMaster.ChangeMoods)

            StdIn.readLine()
        } finally {
            system.terminate()
        }
    }

}