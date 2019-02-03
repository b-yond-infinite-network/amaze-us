import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar

import com.datastax.driver.core._

object Main extends App {
  //  val simucat = new Simulation()
  //  simucat.start(1000)
  //  Thread.sleep(60*1000)
  //  simucat.stop()

  //  def getCurrentdateTimeStamp: Timestamp ={
  //    val today:java.util.Date = Calendar.getInstance.getTime
  //    val timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  //    val now:String = timeFormat.format(today)
  //    val re = java.sql.Timestamp.valueOf(now)
  //    re
  //  }

  //  val collection = sc.parallelize(Seq(("simu48", 17, getCurrentdateTimeStamp, "Miaw")))
  val cluster = new Cluster.Builder()
    .addContactPoints("localhost")
    .withPort(9042)
    .build()

  val session = cluster.connect()

  session.execute("USE simucat_data")
  println(session.execute("SELECT * FROM mood_simulation"))
}
